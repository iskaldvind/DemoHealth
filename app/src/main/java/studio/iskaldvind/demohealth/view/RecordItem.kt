package studio.iskaldvind.demohealth.view

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.viewbinding.BindableItem
import studio.iskaldvind.demohealth.R
import studio.iskaldvind.demohealth.databinding.RecordItemBinding
import studio.iskaldvind.demohealth.model.Record
import java.util.*

class RecordItem(
    context: Context,
    resources: Resources,
    val record: Record,
    val dateStr: String,
    val isSeparator: Boolean
) : BindableItem<RecordItemBinding>() {

    val date = record.date

    private val backgroundGood =
        ContextCompat.getDrawable(context, R.drawable.item_good_background)
    private val backgroundNormal =
        ContextCompat.getDrawable(context, R.drawable.item_normal_background)
    private val backgroundWarning =
        ContextCompat.getDrawable(context, R.drawable.item_warning_background)
    private val headerDimen =
        resources.getDimensionPixelSize(R.dimen.header_height)
    private val recordDimen =
        resources.getDimensionPixelSize(R.dimen.record_height)

    override fun getLayout(): Int = R.layout.record_item

    override fun initializeViewBinding(view: View): RecordItemBinding =
        RecordItemBinding.bind(view)

    override fun bind(binding: RecordItemBinding, position: Int) {
        with(binding) {
            val cal = Calendar.getInstance()
            cal.timeInMillis = record.date
            val rootParams = root.layoutParams as RecyclerView.LayoutParams
            rootParams.height = if (isSeparator) headerDimen else recordDimen
            root.layoutParams = rootParams
            if (isSeparator) {
                recordContainer.visibility = View.GONE
                val month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)
                val date = cal.get(Calendar.DATE)
                val headerText = "$date $month"
                header.text = headerText
            } else {
                header.visibility = View.GONE
                val background = when {
                    record.high < 130 -> backgroundGood
                    record.high > 140 -> backgroundWarning
                    else -> backgroundNormal
                }
                root.background = background
                val hours = cal.get(Calendar.HOUR_OF_DAY)
                val minutes = cal.get(Calendar.MINUTE)
                val minutesString = if (minutes < 10) "0$minutes" else "$minutes"
                val timeString = "$hours:$minutesString"
                time.text = timeString
                high.text = record.high.toString()
                low.text = record.low.toString()
                rate.text = record.rate.toString()
            }
        }
    }

    override fun toString(): String =
        "{record:${record}, dateStr:${dateStr}, isSeparator:${isSeparator}, date:${date}}"
}