/*
 ######################################################################
 # プログラムID          ：［DateUtil］
 # タイトル              ：［フレームワーク機能］
 # 説明                  ：［日付関連ユーティリティクラス］
 ######################################################################
 # 作成日                ：［2014/10/10］
 # 作成者                ：［陈高峰/fengkj］
 #
 # Copyright (C) 2013 NTTCommunications
 ######################################################################
 */
package org.fengkeji.weixin.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日付関連ユーティリティクラス
 * 
 * @version 1.0
 * @author chengf
 */
public abstract class DateUtil {

    /** パターン:yyyyMMdd. */
    public static final String DATE_PATTERN_0 = "yyyyMMdd";

    /** パターン:yyyyMMddHHmmssSSS. */
    public static final String DATE_PATTERN_1 = "yyyyMMddHHmmssSSS";

    /** パターン:yyyy/MM/dd. */
    public static final String DATE_PATTERN_2 = "yyyy/MM/dd";

    /** パターン:yyyy/MM/dd HH:mm:ss. */
    public static final String DATE_PATTERN_3 = "yyyy/MM/dd HH:mm:ss";

    /** パターン:yy/MM/dd. */
    public static final String DATE_PATTERN_4 = "yy/MM/dd";

    /** パターン:yyyy/MM/dd HH:mm:ss.SSS. */
    public static final String DATE_PATTERN_5 = "yyyy/MM/dd HH:mm:ss.SSS";

    /** パターン:yyyyMM. */
    public static final String DATE_PATTERN_6 = "yyyyMM";

    /** パターン:yyMMdd. */
    public static final String DATE_PATTERN_7 = "yyMMdd";

    /** パターン:yyyy-MM-dd HH:mm:ss.SSS. */
    public static final String DATE_PATTERN_8 = "yyyy-MM-dd HH:mm:ss";

    /** パターン:yyyy/MM/dd HH:mm. */
    public static final String DATE_PATTERN_9 = "yyyy/MM/dd HH:mm";

    /** パターン:yyyy/MM. */
    public static final String DATE_PATTERN_10 = "yyyy/MM";

    /** パターン:yyyyMMddHHmmss. */
    public static final String DATE_PATTERN_11 = "yyyyMMddHHmmss";

    /** パターン:yyyy年MM月dd日. */
    public static final String DATE_PATTERN_12 = "yyyy年MM月dd日";

    /** パターン:yy/MM. */
    public static final String DATE_PATTERN_13 = "yy/MM";

    /** パターン:yy/MM/dd HH:mm:ss. */
    public static final String DATE_PATTERN_14 = "yy/MM/dd HH:mm:ss";

    /** パターン:yy年MM月dd日. */
    public static final String DATE_PATTERN_15 = "yy年MM月dd日";

    /** パターン:yy/MM/dd HH:mm. */
    public static final String DATE_PATTERN_16 = "yy/MM/dd HH:mm";

    /** パターン:yyyy-MM-dd. */
    public static final String DATE_PATTERN_17 = "yyyy-MM-dd";

    /** パターン:MMM. dd, yyyy. */
    public static final String DATE_PATTERN_18 = "MMM.  dd, yyyy";

    /** パターン:yyyy.MM.dd. */
    public static final String DATE_PATTERN_19 = "yyyy.MM.dd";

    /** パターン:yy.MM.dd. */
    public static final String DATE_PATTERN_20 = "yy.MM.dd";

    /** パターン:MMMMM dd, yyyy */
    public static final String DATE_PATTERN_21 = "MMMMM dd, yyyy";

    /** パターン:yyyy-MM-dd.HH.mm.ss.SSS */
    public static final String DATE_PATTERN_22 = "yyyy-MM-dd.HH.mm.ss.SSS";

    /** パターン:MM/dd. */
    public static final String DATE_PATTERN_23 = "MM/dd";

    /** パターン:yyMM. */
    public static final String DATE_PATTERN_24 = "yyMM";

    /** パターン:hh:mm. */
    public static final String DATE_PATTERN_25 = "hh:mm";

    /** パターン:yyMMddHHmmss. */
    public static final String DATE_PATTERN_26 = "yyMMddHHmmss";

    /** パターン:yyMMddHHmm**. */
    public static final String DATE_PATTERN_27 = "yyMMddHHmm**";

    /** パターン:HHmmss. */
    public static final String DATE_PATTERN_28 = "HHmmss";

    /** パターン:yyyyMMdd HH:mm:ss. */
    public static final String DATE_PATTERN_29 = "yyyyMMdd HH:mm:ss";

    /** パターン:yyyyMMdd.HHmmss. */
    public static final String DATE_PATTERN_30 = "yyyyMMdd.HHmmss";

    /** パターン:HH:mm. */
    public static final String DATE_PATTERN_45 = "HH:mm";

    /** 月初日 */
    public static final String MONTH_FIRST_DAY = "01";

    /**
     * システム日付を取得する
     * 
     * @return システム日付
     */
    public static Date getSystemDate() {

        return new Date();
    }


    /**
     * 指定フォーマットでシステム日付を取得する
     * 
     * @param dateFormatStr
     * 指定日付フォーマット
     * @return システム日付
     */
    public static String getSystemDate(String dateFormatStr) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        Date systemDate = getSystemDate();
        return dateFormat.format(systemDate);
    }

    /**
     * 指定された日付によって、システム日付演算機能(yyyyMMdd)
     * 
     * @param date
     * 指定日付
     * @param days
     * 追加される日付
     * @return String 日付
     */
    public static String addDays(String date, int days) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.toDate(date, DATE_PATTERN_0));
        calendar.add(Calendar.DATE, days);
        return toString(calendar.getTime(), DATE_PATTERN_0);
    }

    /**
     * 指定されたフォーマットによって、システム日付演算機能
     * 
     * @param date
     * 指定日付
     * @param days
     * 追加される日付
     * @param pattern
     * パターン
     * @return String 日付
     */
    public static String addDays(String date, int days, String pattern) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.toDate(date, pattern));
        calendar.add(Calendar.DATE, days);
        return toString(calendar.getTime(), pattern);
    }

    /**
     * 指定された日付によって、システム日付演算機能
     * 
     * @param date
     * 指定日付
     * @param days
     * 追加される日付
     * @return 日付
     */
    public static Timestamp addDays(Timestamp date, int days) {

        if (null != date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, days);
            return new Timestamp(calendar.getTime().getTime());
        } else {
            return null;
        }
    }

    /**
     * 指定された日付によって、システム日付演算機能
     * 
     * @param date
     * 指定日付
     * @param days
     * 追加される日付
     * @return Date 日付
     */
    public static Date addDays(Date date, int days) {

        if (null != date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, days);
            return calendar.getTime();
        } else {
            return null;
        }
    }

    /**
     * 指定年月と指定月数を加算して、加算された日付の月の最後の日を返す
     * 
     * @param dt
     * 指定日付
     * @param iMonth
     * 指定月数
     * @return 計算した日付
     */
    public static Date getAddMonthEnday(Date dt, int iMonth) {

        if (null != dt) {
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.MONTH, iMonth + 1);
            c.set(Calendar.DATE, 1);
            c.add(Calendar.DAY_OF_MONTH, -1);
            return new Date(c.getTime().getTime());
        } else {
            return null;
        }
    }

    /**
     * 指定時間の時を取得する
     * 
     * @param date
     * 入力時間
     * @return int 指定時間の時
     */
    public static int getHourOFDay(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 指定時間の分を取得する
     * 
     * @param date
     * 入力時間
     * @return 指定時間の分
     */
    public static int getMinuteOFDay(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 指定時間の秒を取得する
     * 
     * @param date
     * 入力時間
     * @return 指定時間の秒
     */
    public static int getSecondOFDay(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 今週の月曜日を取得する
     * 
     * @param format
     * 入力したフォーマット
     * @return 今週の月曜日
     */
    public static String getMondayOFWeek(String format) {

        Calendar c = Calendar.getInstance();
        int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
        c.add(Calendar.DATE, -weekday);
        return toString(c.getTime(), format);
    }

    /**
     * 今週の日曜日を取得する
     * 
     * @param format
     * 入力したフォーマット
     * @return 今週の日曜日
     */
    public static String getSundayOFWeek(String format) {

        Calendar c = Calendar.getInstance();
        int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
        c.add(Calendar.DATE, -weekday);
        c.add(Calendar.DATE, 6);
        return toString(c.getTime(), format);
    }

    /**
     * 指定日付の指定月数後の日付を取得する
     * 
     * @param date
     * 指定日付
     * @param months
     * 指定月
     * @return 指定日付の指定月数後の日付
     */
    public static Date addMonth(final Date date, final int months) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        Date newDate = calendar.getTime();

        return newDate;
    }

    /**
     * 指定日付の指定月数後の年月の第1日を取得する
     * 
     * @param date
     * 指定日付
     * @param months
     * 指定月
     * @return 指定日付の指定月数後の年月の第1日
     */
    public static Date addMonthFirstDay(final Date date, final int months) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        calendar.set(Calendar.DATE, 1);
        Date newDate = calendar.getTime();

        return newDate;
    }

    /**
     * 指定日付の指定月数後の年月の最終日を取得する
     * 
     * @param date
     * 指定日付
     * @param months
     * 指定月
     * @return 指定年月の指定月数後の年月の最終日
     */
    public static Date addMonthLastDay(final Date date, final int months) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date newDate = calendar.getTime();

        return newDate;
    }

    /**
     * 指定日付の指定年数後の年月の最終日を取得する
     * 
     * @param date
     * 指定日付
     * @param years
     * 指定年
     * @return 指定年月の指定年数後の年月の最終日
     */
    public static Date addYearMonthLastDay(final Date date, final int years) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date newDate = calendar.getTime();

        return newDate;
    }

    /**
     * 指定日付の指定年数後の日付を取得する
     * 
     * @param date
     * 指定日付
     * @param years
     * 指定年
     * @return 指定日付の指定年数後の日付
     */
    public static Date addYear(final Date date, final int years) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        Date newDate = calendar.getTime();

        return newDate;
    }

    /**
     * パタン別の日付変換
     * 
     * @param dateStr
     * 日付文字列
     * @param datePatternFrom
     * 変換元パタン
     * @param datePatternTo
     * 変換先パタン
     * @return 変換した日付
     */
    public static String covertDateToOtherPattern(String dateStr,
            String datePatternFrom, String datePatternTo) {

        SimpleDateFormat sdfFrom =
                new SimpleDateFormat(datePatternFrom, Locale.getDefault());
        SimpleDateFormat sdfTo =
                new SimpleDateFormat(datePatternTo, Locale.getDefault());

        try {
            Date date = sdfFrom.parse(dateStr);
            String dateStrTo = sdfTo.format(date);

            return dateStrTo;
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 文字列日付(yyyy/MM/dd)をDate型日付に変わる
     * 
     * @param sDate
     * 文字列日付(yyyy/MM/dd)
     * @return 日付
     */
    public static Date toDate(final String sDate) {

        return toDate(sDate, DATE_PATTERN_2);
    }

    /**
     * 文字列日付をDate型日付に変わる
     * 
     * @param sDate
     * 文字列日付
     * @param sFmt
     * フォーマット
     * @return 日付
     */
    public static Date toDate(final String sDate, final String sFmt) {

        SimpleDateFormat sm = new SimpleDateFormat(sFmt, Locale.getDefault());

        try {
            return sm.parse(sDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日付時刻形式チェック(YYMMDDHHMMSS)
     * 
     * @param formatStr
     * チェック対象文字列
     * @param datePattern
     * 日付タイプ
     * @return チェック結果
     */
    public static boolean isYYMMDDHHMMSS(String formatStr, String datePattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        try {
            sdf.parse(formatStr);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    /**
     * うるう年チェック
     * 
     * @param yearStr
     * チェック対象文字列
     * @return チェック結果
     */
    public static boolean isLeapYear(String yearStr) {

        int year = Integer.parseInt(yearStr);
        if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 > 0))) {
            return true;
        }

        return false;
    }

    /**
     * Date型日付を文字列日付(yyyy/MM/dd)に変わる
     * 
     * @param date
     * Date型日付
     * @return 文字列日付(yyyy/MM/dd)(入力日付はNULLの場合、""を返す)
     */
    public static String toString(final Date date) {

        return toString(date, DATE_PATTERN_2);
    }

    /**
     * Date型日付を指定フォーマットの文字列日付に変わる
     * 
     * @param date
     * Date型日付
     * @param sFmt
     * フォーマット
     * @return 文字列日付(入力日付はNULLの場合、""を返す)
     */
    public static String toString(final Date date, final String sFmt) {

        if (date == null) {
            return "";
        }
        SimpleDateFormat sm = new SimpleDateFormat(sFmt, Locale.getDefault());
        return sm.format(date);
    }

    /**
     * 指定日付の月の月末日を取得する
     * 
     * @param date
     * 指定日付
     * @return 指定日付の月の月末日
     */
    public static int getLastDayOfMonth(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

        return calendar.getActualMaximum(Calendar.DATE);
    }



    /**
     * 日付比較
     * 
     * @param day1
     * 指定日付1
     * @param day2
     * 指定日付2
     * @return 比較結果(負数の場合、day1<day2; 0の場合、day1=day2; 正数の場合、day1>day2)
     */
    public static int compareDate(Date day1, Date day2) {

        long l1 = day1.getTime();
        long l2 = day2.getTime();
        if (l1 - l2 > 0) {
            return 1;
        } else if (l1 - l2 == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 指定日付の指定分数後の日付を取得する
     * 
     * @param date
     * 指定日付
     * @param minutes
     * 指定分数後
     * @return 計算結果
     */
    public static Date addMinutes(Date date, int minutes) {

        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MINUTE, minutes);
        return cale.getTime();
    }



    /**
     * Date型日付を文字列時刻に変わる.（HH:mm:ss）
     * 
     * @param date
     * Date型日付
     * @return 文字列日付(入力時刻はNULLの場合、""を返す)
     */
    public static String getDateTime(final Date date) {

        if (date == null) {
            return "";
        }
        SimpleDateFormat sm =
                new SimpleDateFormat(DATE_PATTERN_3, Locale.getDefault());
        String sysdatetime = sm.format(date);
        String systime = "";

        if (sysdatetime != null
                && sysdatetime.indexOf(' ') > 0) {
            systime = sysdatetime.split(" ")[1];
        }

        return systime;
    }

    /**
     * 指定日付の曜日を取得する
     * 
     * @param dt
     * Date型日付
     * @return 数字曜日（日：1、月：2、火：3、水：4、木：5、金：6、土：7）
     */
    public static int getWeekOfDate(Date dt) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 年取得 yyyy
     * 
     * @param date
     * 日付(yyyymmdd)
     * @return 年
     */
    public static String getYear(String date) {

        if (StringUtil.isEmpty(date)) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.toDate(date, DATE_PATTERN_0));

        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * 年取得 YY (01,02,03......10,11,12)
     * 
     * @param date
     * 日付(yyyymmdd)
     * @return 年
     */
    public static String getYearYY(String date) {

        if (StringUtil.isEmpty(date)) {
            return "";
        }
        String year = getYear(date);
        int iYear = Integer.parseInt(year) % 100;
        return iYear > 9 ? String.valueOf(iYear) : String
                .valueOf("0" + iYear);
    }

    /**
     * 月取得 MM (01,02,03......10,11,12)
     * 
     * @param date
     * 日付
     * @param pattern
     * パターン
     * @return 月
     */
    public static String getMonth(String date, String pattern) {

        if (StringUtil.isEmpty(date)) {
            return "";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.toDate(date, pattern));
        int month = calendar.get(Calendar.MONTH) + 1;

        return month > 9 ? String.valueOf(month) : String
                .valueOf("0" + month);
    }

    /**
     * 月取得 MM (01,02,03......10,11,12)
     * 
     * @param date
     * 日付(yyyymmdd)
     * @return 月
     */
    public static String getMonth(String date) {

        return getMonth(date, DATE_PATTERN_0);
    }

    /**
     * 年月取得 YYYYMM
     * 
     * @param date
     * 日付(yyyymmdd)
     * @return 月
     */
    public static String getYearMonth(String date) {

        if (StringUtil.isEmpty(date)) {
            return "";
        }
        String fromatDate = "";
        if (date.length() == 6) {
            fromatDate = date + "0";
        } else {
            fromatDate = date;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.toDate(fromatDate, DATE_PATTERN_0));
        int month = calendar.get(Calendar.MONTH) + 1;

        // 年月取得 YYYYMM
        String year = getYear(fromatDate);
        String yearMonth =
                year
                        + (month > 9 ? String.valueOf(month) : String
                                .valueOf("0" + month));
        return yearMonth;
    }

    /**
     * 年月取得 YYYYMM
     * 
     * @param date
     * 日付
     * @return 月
     */
    public static String getYearMonth(Date date) {

        if (date == null) {
            return "";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;

        // 年月取得 YYYYMM
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String yearMonth =
                year
                        + (month > 9 ? String.valueOf(month) : String
                                .valueOf("0" + month));
        return yearMonth;
    }

    /**
     * 指定日付の指定月数後の日付を取得する
     * 
     * @param date
     * 指定日付(YYYYMMDD/ YYYYMM)
     * @param months
     * 指定月
     * @return 指定日付の指定月数後の日付
     */
    public static String addMonth(final String date, final int months) {

        if (StringUtil.isEmpty(date)) {
            return null;
        }
        // フォーマットデータ取得
        String fromatDate = "";
        if (date.length() == 6) {
            fromatDate = date + "0";
        } else {
            fromatDate = date;
        }
        // 指定日付の指定月数後の日付を取得する
        Date retDate =
                addMonth(DateUtil.toDate(fromatDate, DATE_PATTERN_0), months);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(retDate);
        int month = calendar.get(Calendar.MONTH) + 1;

        String year = String.valueOf(calendar.get(Calendar.YEAR));
        int day = calendar.get(Calendar.DATE);

        // 戻り値作成
        String yearMonth =
                year
                        + (month > 9 ? String.valueOf(month) : String
                                .valueOf("0" + month));
        if (date.length() == 6) {
            return yearMonth;
        } else {
            return yearMonth
                    + (day > 9 ? String.valueOf(day) : String
                            .valueOf("0" + day));
        }
    }

    /**
     * 開始月から終了月まで月数を算出する
     * 
     * @param startMonth
     * 開始月(YYYYMM)
     * @param endMonth
     * 終了月(YYYYMM)
     * @return 月数
     */
    public static int calcMonthDistance(String startMonth, String endMonth) {

        int distance =
                (Integer.parseInt(endMonth.substring(0, 4)) - Integer
                        .parseInt(startMonth.substring(0, 4)))
                        * 12
                        + (Integer.parseInt(endMonth.substring(4, 6)) - Integer
                                .parseInt(startMonth.substring(4, 6)));

        return Math.abs(distance);
    }

    /**
     * 開始月から終了月まで日数を算出する
     * 
     * @param startYmd
     * 開始日(YYYYMMDD)
     * @param endYmd
     * 終了日(YYYYMMDD)
     * @return 日数
     */
    public static int calcDayDistance(String startYmd, String endYmd) {

        return (int) Math.abs((DateUtil.toDate(endYmd, DATE_PATTERN_0)
                .getTime() - DateUtil.toDate(startYmd, DATE_PATTERN_0)
                .getTime())
                / (1000 * 60 * 60 * 24));
    }

    /**
     * 日付時刻形式チェック(YYMMDDHHMMSS)
     * 
     * @param dateStr
     * 日付時刻文字列
     * @return フォーマット可否
     */
    public static boolean checkYYMMDDHHMMSS(String dateStr) {


        String yyMMdd = dateStr.substring(0, 6);
        String hhmmss = dateStr.substring(6, 12);

        String yyyyMMdd =
                DateUtil.covertDateToOtherPattern(yyMMdd,
                        DATE_PATTERN_7,
                        DATE_PATTERN_0);
        if (!isDate(yyyyMMdd)) {
            return false;
        }

        if (!isTime(hhmmss)) {
            return false;
        }

        return true;
    }

    /**
     * 日付形式チェック(YYYYMMDD/YYYYMM)
     * 
     * @param dateStr
     * 日付文字列
     * @return フォーマット可否
     */
    public static boolean isDate(String dateStr) {

        if (StringUtil.isEmpty(dateStr)) {
            return false;
        }

        if (dateStr.matches("[0-9]{8}")) {
            String year = dateStr.substring(0, 4);
            int month = Integer.parseInt(dateStr.substring(4, 6));
            int day = Integer.parseInt(dateStr.substring(6, 8));

            if ((month) < 1 || (month) > 12) {
                return false;
            }

            int[][] days =
                    {
                            { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
                            { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };

            int isLeap = isLeapYear(year) ? 1 : 0;

            if (day < 1 || day > days[isLeap][month - 1]) {
                return false;
            }

            return true;
        } else if (dateStr.matches("[0-9]{6}")) {
            int month = Integer.parseInt(dateStr.substring(4, 6));

            if ((month) < 1 || (month) > 12) {
                return false;
            }
            return true;
        } else {
            return false;
        }

    }

    /**
     * 時刻形式チェック(HHMMSS)
     * 
     * @param timeStr
     * 時刻文字列
     * @return フォーマット可否
     */
    public static boolean isTime(String timeStr) {

        if (StringUtil.isEmpty(timeStr) || timeStr.length() != 6) {
            return false;
        }

        int hour = Integer.parseInt(timeStr.substring(0, 2));
        int minute = Integer.parseInt(timeStr.substring(2, 4));
        int second = Integer.parseInt(timeStr.substring(4, 6));

        if (hour < 0 || hour > 23) {
            return false;
        }

        if (minute < 0 || minute > 59) {
            return false;
        }

        if (second < 0 || second > 59) {
            return false;
        }

        return true;
    }

   
    /**
     * 時刻形式フォーマット(HH:MM)
     * 
     * @param timeStr
     * 時刻文字列
     * @return フォーマットの結果
     */
    public static String timeConvert(String timeStr) {

        // 非空のチェック
        if (StringUtil.isEmpty(timeStr)) {
            return null;
        }

        // 4桁が未満の場合、桁数の補足
        String hhmm = timeStr;
        if (hhmm.length() != 4) {
            hhmm = StringUtil.padLeft(hhmm, 4, '0');
        }

        // 時刻形式フォーマット(HH:MM)
        StringBuilder sb = new StringBuilder();
        sb.append(hhmm.substring(0, 2));
        sb.append(":");
        sb.append(hhmm.substring(2, 4));

        return sb.toString();
    }

    /**
     * 日付差を計算する
     * 
     * @param beginDayStr
     * 開始日付
     * @param endDayStr
     * 終了日付
     * @param dateFormat
     * フォーマット
     * @return 日付差
     */
    public static String getDiffDay(String beginDayStr, String endDayStr,
            String dateFormat) {

        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        long diff = 0;
        try {
            Date beginDay = format.parse(beginDayStr);
            Date endDay = format.parse(endDayStr);
            diff =
                    (endDay.getTime() - beginDay.getTime())
                            / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return String.valueOf(diff);
    }

}
