package studio.iskaldvind.demohealth.mock

import android.util.Log
import studio.iskaldvind.demohealth.model.Record
import java.util.*
import kotlin.random.Random

class Mock(private var lastTime: Long) {

    private fun randomModifier(amplitude: Int): Int =
        Random.nextInt(-amplitude, amplitude)

    fun getData(): Record {
        val cal = Calendar.getInstance()
        cal.timeZone = TimeZone.getDefault()
        cal.timeInMillis = lastTime
        val hours = cal.get(Calendar.HOUR_OF_DAY)
        val newHours = (if (hours < 14) 21 else 6) + randomModifier(2)
        val newMinutes = 30 + randomModifier(29)
        if (hours > 14) cal.add(Calendar.DATE, 1)
        cal.set(Calendar.HOUR_OF_DAY, newHours)
        cal.set(Calendar.MINUTE, newMinutes)
        lastTime = cal.timeInMillis
        return Record(
            date = lastTime,
            high = 130 + randomModifier(15),
            low = 70 + randomModifier(10),
            rate = 55 + randomModifier(5)
        )
    }
}