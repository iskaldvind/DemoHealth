package studio.iskaldvind.demohealth.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import studio.iskaldvind.demohealth.R.layout.main_activity
import studio.iskaldvind.demohealth.databinding.MainActivityBinding
import studio.iskaldvind.demohealth.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import studio.iskaldvind.demohealth.model.Record
import studio.iskaldvind.demohealth.utils.getDate

class MainActivity: AppCompatActivity(main_activity) {

    private val viewModel: MainViewModel by viewModel()
    private val binding: MainActivityBinding by viewBinding()
    private val adapter by lazy { RecordsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.recycler.adapter = adapter
        binding.fab.setOnClickListener { viewModel.sendData() }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collect { values ->
                    for (value in values.sortedBy { it.date }) {
                        addToAdapter(record = value)
                    }
                }
            }
        }
        viewModel.getData()
    }

    @Synchronized
    private fun addToAdapter(record: Record) {
        val recordItem = RecordItem(
            context = this,
            record = record,
            resources = resources,
            dateStr = getDate(record.date),
            isSeparator = false
        )
        val previousItem = adapter.getPrevious(recordItem)
        if (previousItem == null || (
                    !previousItem.isSeparator && previousItem.dateStr < recordItem.dateStr)) {
            val separator = RecordItem(
                context = this,
                record = Record(recordItem.date - 1),
                resources = resources,
                dateStr = getDate(record.date),
                isSeparator = true
            )
            adapter.addItem(separator)
        }
        adapter.addItem(recordItem)
        binding.recycler.scrollToPosition(adapter.itemCount - 1)
    }
}