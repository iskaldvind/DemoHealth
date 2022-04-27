package studio.iskaldvind.demohealth.view

import com.xwray.groupie.GroupieAdapter

class RecordsAdapter : GroupieAdapter() {

    private val items: MutableList<RecordItem> = mutableListOf()
    private val noIndex = -1

    @Synchronized
    fun addItem(item: RecordItem) {
        if (getExistingIndex(item = item) != noIndex) return
        val index = getIndex(item)
        items.add(index = index, element = item)
        add(index, item)
    }

    override fun getItemCount(): Int =
        items.size

    private fun getIndex(item: RecordItem): Int {
        if (items.isEmpty()) return 0
        for((index, current) in items.withIndex()) {
            if (current.date > item.date) {
                return index
            }
        }
        return items.size
    }

    private fun getExistingIndex(item: RecordItem): Int {
        if (items.isEmpty()) return noIndex
        for ((index, current) in items.withIndex()) {
            if (current.date == item.date) return index
        }
        return noIndex
    }

    fun getPrevious(item: RecordItem): RecordItem? {
        if (items.isEmpty() || items[0].date > item.date) return null
        val mItems = items.toList()
        for ((index, current) in mItems.withIndex()) {
            if (index > 0 && current.date > item.date) return mItems[index - 1]
        }
        return mItems.last()
    }
}