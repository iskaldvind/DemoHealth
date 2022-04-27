package studio.iskaldvind.demohealth.model

class Record {
    val date: Long
    val high: Int
    val low: Int
    val rate: Int

    @Suppress("unused")
    constructor() {
        this.date = 0L
        this.high = 0
        this.low = 0
        this.rate = 0
    }

    constructor(date: Long) {
        this.date = date
        this.high = 0
        this.low = 0
        this.rate = 0
    }

    constructor(date: Long, high: Int, low: Int, rate: Int) {
        this.date = date
        this.high = high
        this.low = low
        this.rate = rate
    }

    override fun toString(): String =
        "{date:${date}, high:${high}, low:${low}, rate:${rate}}"
}