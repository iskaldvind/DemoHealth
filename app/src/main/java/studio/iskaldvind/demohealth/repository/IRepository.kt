package studio.iskaldvind.demohealth.repository

import kotlinx.coroutines.flow.SharedFlow
import studio.iskaldvind.demohealth.model.Record

interface IRepository {
    fun getFlow(): SharedFlow<List<Record>>
    suspend fun getRecords()
    suspend fun sendRecord(record: Record)
}