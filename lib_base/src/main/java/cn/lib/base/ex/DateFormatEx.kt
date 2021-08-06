package cn.lib.base.ex

import java.text.SimpleDateFormat
import java.util.*

//1秒等于1000毫秒
const val SECOND = 1000

//1分钟等于 60 * 1000毫秒
const val MINUTE = 60 * SECOND

//一小时等于60 * 60 * 1000毫秒
const val HOUR = 60 * MINUTE

//一天等于 24 * 60 * 60 * 1000毫秒
const val DAY = 24 * HOUR

//一周等于 7 * 24 * 60 * 60 * 1000毫秒
const val WEEK_DAY = 7 * DAY

//一年等于 365 * 24 * 60 * 60 * 1000毫秒
const val YEAR = 365 * DAY

const val FORMAT_YMD = "yyyy/MM/dd"
const val FORMAT_YMD2 = "yyyy-MM-dd"

const val FORMAT_YMDHM = "yyyy/MM/dd HH:mm"
const val FORMAT_YMDHM2 = "yyyy-MM-dd HH:mm"
const val FORMAT_YMDHM3 = "yyyy年MM月dd日 HH:mm"
const val FORMAT_YMDHM4 = "yyyy.MM.dd HH:mm"

const val FORMAT_YMDHMS = "yyyy/MM/dd HH:mm:ss"
const val FORMAT_YMDHMS2 = "yyyy-MM-dd HH:mm:ss"

const val FORMAT_MDHM = "MM/dd HH:mm"
const val FORMAT_MDHM2 = "MM-dd HH:mm"
const val FORMAT_MDHM3 = "MM月dd日 HH:mm"
const val FORMAT_MDHM4 = "MM.dd HH:mm"

const val FORMAT_MD = "MM/dd"
const val FORMAT_MD2 = "MM-dd"

const val FORMAT_HH_MM = "HH:mm"
const val FORMAT_YESTORY_HH_MM = "昨天 HH:mm"
const val FORMAT_HH_MM_SS = "HH:mm:ss"

fun str2date(time: String, pattern: String): Date {
    val format = SimpleDateFormat(pattern)
    return format.parse(time)
}

fun millis2Str(millis: Long, pattern: String): String {
    return date2str(Date(millis), pattern)
}

fun date2str(date: Date, pattern: String): String {
    val format = SimpleDateFormat(pattern)
    return format.format(date)
}

fun convert(time: String, pattern: String): Long {
    val format = SimpleDateFormat(pattern)
    return format.parse(time).time
}

fun compare(date1: Date, date2: Date): Long {
    return date1.time - date2.time
}

fun isToDay(date: Date): Boolean {
    return isToDay(date.time)
}

fun isToDay(date: Long): Boolean {
    val calendar: Calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    val startTime: Long = convert("$year ${month + 1} $day", "yyyy MM dd")
    val endTime: Long = startTime + DAY
    return date in startTime until endTime
}

fun isYesterday(date: Date): Boolean {
    return isYesterday(date.time)
}

fun isYesterday(date: Long): Boolean {
    val calendar: Calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    val endTime: Long = convert("$year ${month + 1} $day", "yyyy MM dd")
    val startTime: Long = endTime - DAY
    return date in startTime until endTime
}

fun isToyear(date: Date): Boolean {
    return isToyear(date.time)
}

fun isToyear(date: Long): Boolean {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    calendar.timeInMillis = date
    return year == calendar.get(Calendar.YEAR)
}

fun todayYesterdayToyear(date: Date): String {
    return when {
        isToDay(date) -> date2str(date, FORMAT_HH_MM)//HH:mm
        isYesterday(date) -> date2str(date, FORMAT_YESTORY_HH_MM)//昨天 HH:mm
        isToyear(date) -> date2str(date, FORMAT_MDHM4)//MM.dd HH:mm
        else -> date2str(date, FORMAT_YMDHM4)//yyyy.MM.dd HH:mm
    }
}