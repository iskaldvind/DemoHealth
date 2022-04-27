package studio.iskaldvind.demohealth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import studio.iskaldvind.demohealth.mock.Mock
import studio.iskaldvind.demohealth.model.Record
import studio.iskaldvind.demohealth.repository.IRepository
import java.util.*

class MainViewModel(
    private val repository: IRepository
): ViewModel() {

    var lastDate = 0L
    var mock: Mock? = null

    private val _data: MutableSharedFlow<List<Record>> = MutableSharedFlow(replay = 100)
    val data: SharedFlow<List<Record>> = _data
    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Default + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    fun getData() {
        viewModelCoroutineScope.launch {
            repository.getFlow().collect { records -> _data.emit(records) }
        }
        viewModelCoroutineScope.launch {
            repository.getRecords()
        }
    }

    fun sendData() {
        if (lastDate == 0L) lastDate = Calendar.getInstance().timeInMillis
        if (mock == null) mock = Mock(lastDate)
        viewModelCoroutineScope.launch {
            mock?.getData()?.let { record ->
                repository.sendRecord(record = record)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    private fun cancelJob() =
        viewModelCoroutineScope.coroutineContext.cancelChildren()

    private fun handleError(error: Throwable) =
        Log.e("Error", error.stackTraceToString())
}