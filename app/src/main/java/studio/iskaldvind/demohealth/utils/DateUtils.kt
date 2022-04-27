package studio.iskaldvind.demohealth.utils

import java.util.*

fun getDate(time: Long): String {
    val cal = Calendar.getInstance()
    cal.timeZone = TimeZone.getDefault()
    cal.timeInMillis = time
    val month = cal.get(Calendar.MONTH)
    val monthStr = if (month < 10) "0$month" else "$month"
    val date = cal.get(Calendar.DATE)
    val dateStr = if (date < 10) "0$date" else "$date"
    return "${cal.get(Calendar.YEAR)}$monthStr$dateStr"
}