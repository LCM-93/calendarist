package org.hothub.calendarist.utils;

import org.hothub.calendarist.constants.CalendaristConstants;
import org.hothub.calendarist.pojo.SolarDate;

import java.util.Calendar;
import java.util.Date;

public class CalendaristUtils {



    /**
     * 干支日期中的年月日转化为干支
     *
     * @param value 年 OR 月 OR 日
     * @return String
     */
    public static String ganZhi(int value) {
        return CalendaristConstants.TIANGAN_INFO[value % 10] + CalendaristConstants.DIZHI_INFO[value % 12];
    }


    /**
     * 获取农历年份对应的生肖
     *
     * @param lunarYear 农历年份
     * @return String
     */
    public static String getZodiac(int lunarYear) {
        return CalendaristConstants.ZODIAC_INFO[(lunarYear - 4) % 12];
    }



    /**
     * 干支日期中的小时转为干支
     *
     * @param cycleDay 干支日期的天
     * @param cycleHour 干支日期的小时
     * @return String
     */
    public static String hourGanZhi(int cycleDay, int cycleHour) {
        int index = (cycleDay % 10) + 1;

        // 五个为一周期
        index = index % 5;

        //小时对应的索引
        int hourIndex = hourZhi(cycleHour);

        if (hourIndex > 10) {
            return CalendaristConstants.TIANGAN_INFO[hourIndex - 10 + (index - 1) * 2] + CalendaristConstants.DIZHI_INFO[hourZhi(cycleHour)];
        } else {
            hourIndex = hourIndex + (index - 1) * 2;

            return CalendaristConstants.TIANGAN_INFO[hourIndex >= 10 ? hourIndex - 10 : hourIndex] + CalendaristConstants.DIZHI_INFO[hourZhi(cycleHour)];
        }
    }



    /**
     * 返回小时对应的支的索引
     *
     * @param hour 干支日期的小时数
     * @return int
     */
    public static int hourZhi(int hour) {
        if (hour >= 23 || hour < 1) {
            return 0;
        } else if (hour < 3) {
            return 1;
        } else if (hour < 5) {
            return 2;
        } else if (hour < 7) {
            return 3;
        } else if (hour < 9) {
            return 4;
        } else if (hour < 11) {
            return 5;
        } else if (hour < 13) {
            return 6;
        } else if (hour < 15) {
            return 7;
        } else if (hour < 17) {
            return 8;
        } else if (hour < 19) {
            return 9;
        } else if (hour < 21) {
            return 10;
        } else {
            return 11;
        }
    }



    /**
     * 获取某年的春节当天的阳历日期
     *
     * @param year 年份
     * @return {@link SolarDate}
     */
    public static int chineseNewYear(Integer year) {
        if (year > 2100 || year < 1900) {
            throw new RuntimeException("the year should between 1900 and 2100!");
        }

        return CalendaristConstants.CHINESE_NEW_YEAR[year - CalendaristConstants.MIN_YEAR];
    }



    /**
     * 获取某年的春节当天的阳历日期
     *
     * @param year 年份
     * @return {@link SolarDate}
     */
    public static long chineseNewYearTimestamp(Integer year) {
        if (year > 2100 || year < 1900) {
            throw new RuntimeException("the year should between 1900 and 2100!");
        }

        return CalendaristConstants.CHINESE_NEW_YEAR_TIMESTAMP[year - CalendaristConstants.MIN_YEAR];
    }





    // WARNING: Dates before Oct. 1582 are inaccurate
    public static long solarToInt(int y, int m, int d) {
        m = (m + 9) % 12;
        y = y - m / 10;
        return 365 * y + y / 4 - y / 100 + y / 400 + (m * 306 + 5) / 10 + (d - 1);
    }

    public static SolarDate solarFromInt(long g) {
        long y = (10000 * g + 14780) / 3652425;
        long ddd = g - (365 * y + y / 4 - y / 100 + y / 400);
        if (ddd < 0) {
            y--;
            ddd = g - (365 * y + y / 4 - y / 100 + y / 400);
        }
        long mi = (100 * ddd + 52) / 3060;
        long mm = (mi + 2) % 12 + 1;
        y = y + (mi + 2) / 12;
        long dd = ddd - (mi * 306 + 5) / 10 + 1;

        return new SolarDate((int) y, (int) mm, (int) dd);
    }




    public static int getBitInt(int data, int length, int shift) {
        return (data & (((1 << length) - 1) << shift)) >> shift;
    }



    /**
     * 正确的立春时间应该是以小时来进行计算的
     *
     * @param solarYear 阳历年份
     * @param solarMonth 阳历月份
     * @return
     */
    public static int getFirstTerm(int solarYear, int solarMonth) {
        long times = 31556925974L * (solarYear - 1900) + CalendaristConstants.SOLAR_TERM_INFO[(solarMonth - 1) * 2] * 60000L + ((long) 0.7 * (solarYear - 1900));
        Date offDate = new Date(times - 2208549300000L);
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        cal.setTime(offDate);
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        // 之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
        return (cal.get(Calendar.DATE));
    }



    /**
     * 农历某年的总天数
     *
     * @param lunarYear 农历年份
     * @return int
     */
    public static int daysOfYear(int lunarYear) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((CalendaristConstants.LUNAR_CODE[lunarYear - CalendaristConstants.MIN_YEAR] & i) != 0) sum += 1;
        }

        return (sum + daysOfLeapMonth(lunarYear));
    }

    /**
     * 农历某年某月的总天数
     *
     * @param lunarYear 农历年份
     * @param lunarMonth 农历月份
     * @return int
     */
    public static int daysOfMonth(int lunarYear, int lunarMonth) {
        if ((CalendaristConstants.LUNAR_CODE[lunarYear - CalendaristConstants.MIN_YEAR] & (0x10000 >> lunarMonth)) == 0) {
            return 29;
        } else {
            return 30;
        }
    }

    /**
     * 农历某年闰月的天数
     *
     * @param lunarYear 农历年份
     * @return int
     */
    public static int daysOfLeapMonth(int lunarYear) {
        if (leapMonth(lunarYear) != 0) {
            if ((CalendaristConstants.LUNAR_CODE[lunarYear - CalendaristConstants.MIN_YEAR] & 0x10000) != 0) {
                return 30;
            } else {
                return 29;
            }
        } else {
            return 0;
        }
    }

    /**
     * 农历某年闰哪个月
     * 1-12 , 没闰返回0
     *
     * @param lunarYear 农历年份
     * @return int
     */
    public static int leapMonth(int lunarYear) {
        return CalendaristConstants.LUNAR_CODE[lunarYear - CalendaristConstants.MIN_YEAR] & 0xf;
    }

}