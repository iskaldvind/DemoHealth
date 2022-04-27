package studio.iskaldvind.demohealth.repository

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import studio.iskaldvind.demohealth.model.Record

class Repository: IRepository {

    private val db = Firebase.firestore
    private val collection = "records"
    private val scope = CoroutineScope(
        Dispatchers.Default
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(e = throwable)
        }
    )

    private val data: MutableSharedFlow<List<Record>> = MutableSharedFlow(replay = 100)

    override fun getFlow(): SharedFlow<List<Record>> =
        data

    override suspend fun getRecords() {
        db.collection(collection).get().addOnSuccessListener { results ->
            scope.launch {
                data.emit(results.mapNotNull { result ->
                    result.toObject(Record::class.java)
                })
            }
        }
    }

    override suspend fun sendRecord(record: Record) {
        db.collection(collection).add(record).addOnSuccessListener {
            scope.launch {
                data.emit(listOf(record))
            }
        }
    }

    private fun handleError(e: Throwable) {
        Log.e("Error", e.stackTraceToString())
    }
}