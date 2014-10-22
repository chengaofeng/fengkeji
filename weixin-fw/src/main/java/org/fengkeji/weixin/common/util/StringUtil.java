/*
 ######################################################################
 # プログラムID          ：［StringUtil］
 # タイトル              ：［フレームワーク機能］
 # 説明                  ：［文字列に関するユーティリティクラス］
 ######################################################################
 # 作成日                ：［2013/4/10］
 # 作成者                ：［戚　志偉/NTTCom］
 #
 # Copyright (C) 2013 NTTCommunications
 ######################################################################
 */
package org.fengkeji.weixin.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 文字列に関するユーティリティクラス
 * 
 * @version 1.0
 * @author NTTCommunications
 */
public abstract class StringUtil {

    /**
     * SHIFT_JIS
     */
    private static final String ENCODING_SHIFTJIS = "Shift-Jis";
    
    /** 文字:全角スペース. */
    public static final char WIDE_SPACE = '　';

    /** 文字:-. */
    public static final char HYPHEN = '-';

    /** 文字:. */
    public static final char PERIOD = '.';

    /** 文字：半角スペース. */
    public static final char HALF_SPACE = ' ';

    /** 文字：半角ゼロ. */
    public static final char HALF_ZERO = '0';

    /** 文字：半角コンマ. */
    public static final char COMMA = ',';

    /** 文字：TAB. */
    public static final char TAB = '\t';

    /** 文字：クウォウト. */
    public static final char QUOTE = '"';

    /** 文字：\n. */
    public static final char NEW_LINE = '\n';

    /**
     * 対象文字列の桁数は指定長さより未満の場合、先頭に指定文字を埋め込み
     * 
     * @param resultStr 対象文字列
     * @param len 長さ
     * @param str 文字
     * @return 結果文字列
     */
    public static String padLeft(String resultStr, int len, char str) {

        String result = nvl(resultStr);
        StringBuilder buf = new StringBuilder(len);

        int padCount = len - result.length();
        for (int i = 0; i < padCount; i++) {
            buf.append(str);
        }

        buf.append(result);
        return buf.toString();
    }

    /**
     * 対象文字列の桁数は指定長さより未満の場合、後ろに指定文字を埋め込み
     * 
     * @param resultStr 対象文字列
     * @param len 長さ
     * @param str 文字
     * @return 結果文字列
     */
    public static String padRight(String resultStr, int len, char str) {

        StringBuilder buf = new StringBuilder(nvl(resultStr));

        while (buf.length() < len) {
            buf.append(str);
        }
        return buf.toString();
    }

    /**
     * 指定の桁数は指定長さ文字を埋め込み
     * 
     * @param len 長さ
     * @param str 文字
     * @return 結果文字列
     */
    public static String reap(int len, char str) {

        StringBuilder buf = new StringBuilder();

        while (buf.length() < len) {
            buf.append(str);
        }
        return buf.toString();
    }

    /**
     * 空文字列かどうかチェック
     * 
     * @param text 対象文字列
     * @return boolean チェック結果
     */
    public static boolean isEmpty(String text) {

        return isEmpty(text, false);
    }

    /**
     * 空文字列かどうかチェック
     * 
     * @param text 対象文字列
     * @param trimFlg true:トリム|false:トリムなし
     * @return boolean チェック結果
     */
    public static boolean isEmpty(String text, boolean trimFlg) {

        if (text == null) {
            return true;
        }

        String value = text;
        if (trimFlg) {
            value = text.trim();
        }

        return value.length() == 0;
    }

    /**
     * 入力した文字列のバイト長さを取得する
     * 
     * @param str 入力した文字列
     * @param encoding 文字エンコーディング
     * @return バイト長さ
     */
    public static int getByteLength(String str, String encoding) {

        try {
            return nvl(str).getBytes(encoding).length;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 文字列はNULLまたは空文字の場合、空文字をリターン
     * 
     * @param str 文字列
     * @return String 文字列または空文字
     */
    public static String nvl(String str) {

        return isEmpty(str) ? "" : str;
    }

    /**
     * 文字列の前後空白（半角スペースと全角スペース）を除く
     * 
     * @param s 文字列
     * @return String 変更された文字列
     */
    public static String trim(String s) {

        if (s == null) {
            return null;
        }

        int begin = 0;
        int end = 0;
        int length = s.length();
        for (begin = 0; begin < length; begin++) {
            char c = s.charAt(begin);
            if (!(c == HALF_SPACE || c == WIDE_SPACE)) {
                break;
            }
        }

        for (end = length - 1; end >= 0; end--) {
            char c = s.charAt(end);
            if (!(c == HALF_SPACE || c == WIDE_SPACE)) {
                break;
            }
        }

        if (end < begin) {
            return "";

        }
        return s.substring(begin, end + 1);
    }

    /**
     * 文字列の後空白（半角スペースと全角スペース）を除く
     * 
     * @param s 文字列
     * @return String 変更された文字列
     */
    public static String rtrim(String s) {

        if (s == null) {
            return null;
        }

        int begin = 0;
        int end = 0;
        int length = s.length();

        for (end = length - 1; end >= 0; end--) {
            char c = s.charAt(end);
            if (!(c == HALF_SPACE || c == WIDE_SPACE)) {
                break;
            }
        }

        if (end < begin) {
            return "";
        }
        return s.substring(begin, end + 1);
    }

    /**
     * BigDecimalからStringに変換する
     * 
     * @param dec BigDecimal
     * @return 変換したString
     */
    public static String convertBigDecimalToString(BigDecimal dec) {

        if (null == dec) {
            return null;
        } else {
            return dec.toPlainString();
        }
    }

    /**
     * StringからBigDecimalに変換する
     * 
     * @param dec String
     * @return 変換したBigDecimal
     */
    public static BigDecimal convertStringToBigDecimal(String dec) {

        if (isEmpty(dec)) {
            return null;
        } else {
            return new BigDecimal(dec);
        }
    }

    /**
     * IntegerからStringに変換する
     * 
     * @param dec Integer
     * @return 変換したString
     */
    public static String convertIntegerToString(Integer dec) {

        if (dec == null) {
            return null;
        } else {
            return String.valueOf(dec);
        }
    }

    /**
     * LongからStringに変換する
     * 
     * @param dec Integer
     * @return 変換したString
     */
    public static String convertLongToString(Long dec) {

        if (dec == null) {
            return null;
        } else {
            return String.valueOf(dec);
        }
    }

    /**
     * 含め文字列を計算
     * 
     * @param baseStr 本文字列
     * @param containStr 含め文字列
     * @return 含めカウント
     */
    public static int getContainStr(String baseStr, String containStr) {

        if (isEmpty(baseStr) || isEmpty(containStr)) {
            return 0;
        }

        int baseLen = baseStr.length();
        int conLen = containStr.length();
        int repLen = baseStr.replaceAll(containStr, "").length();

        return (baseLen - repLen) / conLen;

    }

    /**
     * 値比較
     * 
     * @param a 比較パラメータ１
     * @param b 比較パラメータ２
     * @return 比較結果
     */
    public static boolean assertEqual(String a, String b) {

        boolean ret = false;
        if (isEmpty(a) && isEmpty(b)) {
            ret = true;
        } else if (!isEmpty(a) && !isEmpty(b) && a.equals(b)) {
            ret = true;
        }
        return ret;
    }

    /**
     * 金額のカンマ編集（￥-Z,ZZZ,ZZZ,ZZ9）を行う
     * 
     * @param amount 文字列で表す金額
     * @param markFlg TRUE：￥マーク付き FALSE：￥マークなし
     * @param maxSize 受取バッファの最大サイズ
     * @return 出力金額
     */
    public static String formatCurrency(String amount, boolean markFlg,
            int maxSize) {

        boolean minus = false;
        int newMaxSize = maxSize;
        String result;
        String forAmount = amount;
        NumberFormat formater = null;
        formater = new DecimalFormat("###,###");
        if (!isEmpty(amount)) {
            if ("-".equals(amount.substring(0, 1))) {
                forAmount = amount.substring(1);
                minus = true;
            }
            if (newMaxSize < 0) {
                newMaxSize = 255;
            }
            if (markFlg) {
                newMaxSize--;
            }
            double num = Double.parseDouble(forAmount);
            result = formater.format(num);
            if (result.length() > newMaxSize) {
                result =
                        result.substring(result.length() - newMaxSize,
                                result.length());
            }
            if (minus) {
                result = "-" + result;
            }
            if (markFlg) {
                result = "￥" + result;
            }
            return result;
        } else {
            return "";
        }
    }

    /**
     * 金額のカンマ編集（￥-Z,ZZZ,ZZZ,ZZ9）を行う
     * 
     * @param amount long金額
     * @param markFlg TRUE：￥マーク付き FALSE：￥マークなし
     * @param maxSize 受取バッファの最大サイズ
     * @return 出力金額
     */
    public static String formatCurrency(long amount, boolean markFlg,
            int maxSize) {

        return formatCurrency(String.valueOf(amount), markFlg, maxSize);
    }

   

    /**
     * ユニコードのテキスト変換
     * 
     * @param theString 変換前のユニコード
     * @return 変換後の テキスト
     */
    public static String decodeUnicode(String theString) {

        if (isEmpty(theString)) {
            return "";
        }

        char aChar;
        int len = theString.length();
        StringBuilder outBuffer = new StringBuilder(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed      encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * 数値と文字列 の形式変換
     * 
     * @param num 数値
     * @return 文字列
     */
    public static String integerToString(Integer num) {

        if (null == num) {
            return null;
        }
        return String.valueOf(num);
    }

    /**
     * 数値と文字列 の形式変換
     * 
     * @param num 数値
     * @return 文字列
     */
    public static String longToString(Long num) {

        if (null == num) {
            return null;
        }
        return String.valueOf(num);
    }

    /**
     * 数値と文字列 の形式変換
     * 
     * @param s 文字列
     * @return 数値
     */
    public static Integer stringToInteger(String s) {

        if (StringUtil.isEmpty(s)) {
            return null;
        }
        return Integer.valueOf(s);
    }

    /**
     * 数値と文字列 の形式変換
     * 
     * @param s 文字列
     * @return 数値
     */
    public static Long stringToLong(String s) {

        if (StringUtil.isEmpty(s)) {
            return null;
        }
        return Long.valueOf(s);
    }

    /**
     * 数値と文字列 の形式変換
     * 
     * @param s 文字列
     * @return 数値
     */
    public static long stringTolong(String s) {

        if (StringUtil.isEmpty(s)) {
            return 0;
        }
        return Long.parseLong(s);
    }

    /**
     * 数値と文字列 の形式変換
     * 
     * @param l Long
     * @return 数値
     */
    public static long convertLongTolong(Long l) {

        if (l == null) {
            return 0;
        }
        return l;
    }

    /**
     * 数値と文字列 の形式変換
     * 
     * @param s 文字列
     * @return 数値
     */
    public static int stringToInt(String s) {

        if (StringUtil.isEmpty(s)) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    /**
     * 数値(Integerとint) の形式変換
     * 
     * @param i Integer形数値
     * @return int形数値
     */
    public static int integerToInt(Integer i) {

        if (i == null) {
            return 0;
        }
        return i;
    }

    /**
     * 数値(Longとlong) の形式変換
     * 
     * @param i Integer形数値
     * @return int形数値
     */
    public static long nullToZero(Long i) {

        if (i == null) {
            return 0L;
        }
        return i;
    }

    /**
     * 値1と値2を比較する
     * 
     * @param src 値1
     * @param dest 値2
     * @return +:値＞値2|0:値1==値2|-:値1＜値2
     */
    public static int compareTo(String src, String dest) {

        if (StringUtil.isEmpty(src) && StringUtil.isEmpty(dest)) {
            return 0;
        }

        if (StringUtil.isEmpty(src) && !StringUtil.isEmpty(dest)) {
            return -1;
        }

        if (!StringUtil.isEmpty(src) && StringUtil.isEmpty(dest)) {
            return 1;
        }

        return src.compareTo(dest);
    }

    /**
     * 値1と値2を比較する
     * 
     * @param src 値1
     * @param dest 値2
     * @return +:値＞値2|0:値1==値2|-:値1＜値2
     */
    public static int compareTo(Integer src, Integer dest) {

        if (src == null) {
            if (dest == null) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (dest == null) {
                return 1;
            } else {
                return src.compareTo(dest);
            }
        }
    }

    /**
     * 値1と値2を比較する
     * 
     * @param src 値1
     * @param dest 値2
     * @return +:値＞値2|0:値1==値2|-:値1＜値2
     */
    public static int compareTo(Long src, Long dest) {

        if (src == null) {
            if (dest == null) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (dest == null) {
                return 1;
            } else {
                return src.compareTo(dest);
            }
        }
    }

    /**
     * 値1と値2を比較する
     * 
     * @param src 値1
     * @param dest 値2
     * @return +:値＞値2|0:値1==値2|-:値1＜値2
     */
    public static int compareTo(Long src, Integer dest) {

        if (src == null) {
            if (dest == null) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (dest == null) {
                return 1;
            } else {
                return src.compareTo(Long.valueOf(dest));
            }
        }
    }

    /**
     * 値1と値2を比較する
     * 
     * @param src 値1
     * @param dest 値2
     * @return +:値＞値2|0:値1==値2|-:値1＜値2
     */
    public static int compareTo(Integer src, Long dest) {

        return -1 * compareTo(dest, src);
    }

    /**
     * 文字列を連接する
     * 
     * @param strs 文字列
     * @return 連接結果
     */
    public static String concat(String... strs) {

        StringBuilder builder = new StringBuilder();
        for (String item : strs) {
            item = StringUtil.isEmpty(item) ? "" : item;
            builder.append(item);
        }
        return builder.toString();
    }

    /**
     * 文字列を連接する
     * 
     * @param objects 文字列
     * @return 連接結果
     */
    public static String concatObjects(Object... objects) {

        StringBuilder builder = new StringBuilder();
        for (Object item : objects) {
            item = item == null ? "" : item;
            if (item instanceof String) {
                builder.append(item);
            } else {
                builder.append(String.valueOf(item));
            }
        }
        return builder.toString();
    }

    /**
     * 文字列から数字配列に変換する
     * 
     * @param str 文字列
     * @param separate デリミタ
     * @return 数字配列
     */
    public static int[] convertStringToIntArray(String str, String separate) {

        String[] strArray = str.split(nvl(separate));
        int[] intArray = convertStringArrayToIntArray(strArray);

        return intArray;
    }

    /**
     * 数字配列から文字列に変換する
     * 
     * @param intArray 数字配列
     * @param separate デリミタ
     * @return 文字列
     */
    public static String
            convertIntArrayToString(int[] intArray, String separate) {

        String separateStr = nvl(separate);

        if (intArray == null || intArray.length == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i : intArray) {
                sb.append(i);
                sb.append(separateStr);
            }
            String str = sb.toString();

            return str.substring(0, str.length() - separateStr.length());
        }

    }

    /**
     * 文字配列から数字配列に変換する
     * 
     * @param strArray 文字配列
     * @return 数字配列
     */
    public static int[] convertStringArrayToIntArray(String[] strArray) {

        int[] intArray = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }

        return intArray;
    }

    /**
     * 文字配列から文字列に変換する
     * 
     * @param strArray 文字配列
     * @param separate デリミタ
     * @return 文字列
     */
    public static String convertStringArrayToString(String[] strArray,
            String separate) {

        String separateStr = nvl(separate);

        if (strArray == null || strArray.length == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (String i : strArray) {
                sb.append(i);
                sb.append(separateStr);
            }
            String str = sb.toString();

            return str.substring(0, str.length() - separateStr.length());
        }
    }

    /**
     * 文字列の切り取る
     * 
     * @param str 文字列
     * @param startIndex 開始位置
     * @param endIndex 終了位置
     * @return 文字列
     */
    public static String substring(String str, int startIndex, int endIndex) {

        if (!StringUtil.isEmpty(str)) {
            if (endIndex > startIndex) {
                if (str.length() >= endIndex) {
                    return str.substring(startIndex, endIndex);
                } else if (str.length() < endIndex && str.length() > startIndex) {
                    return str.substring(startIndex, str.length());
                }
            }
        }

        return "";
    }

    /**
     * バイト取り込む
     * 
     * @param byteFrom 取得元バイト
     * @param startIndex 開始インデックス
     * @param length 取り込むバイト数
     * @return 取り込んだバイト
     */
    public static byte[] getMiddleBytes(byte[] byteFrom, int startIndex,
            int length) {

        if (byteFrom == null || byteFrom.length == 0) {
            return byteFrom;
        }

        if (startIndex < 0 || startIndex >= byteFrom.length || length < 0
                || length > byteFrom.length) {
            return byteFrom;
        }

        byte[] byteTo = new byte[length];
        for (int i = 0; i < length; i++) {
            byteTo[i] = byteFrom[i + startIndex];
        }

        return byteTo;
    }

    /**
     * バイト取り込む
     * 
     * @param inputStr 入力文字列
     * @param startIndex 開始インデックス
     * @param length 取り込むバイト数
     * @return 取り込んだ文字列
     */
    public static String getMiddleStringByByte(String inputStr, int startIndex,
            int length) {

        String retStr = "";

        if (StringUtil.isEmpty(inputStr)) {
            return "";
        }

        try {
            byte[] inBytes = inputStr.getBytes(ENCODING_SHIFTJIS);
            int bLength = inBytes.length;
            if (startIndex < bLength) {
                int cLength = 0;
                if (startIndex + length >= bLength) {
                    cLength = bLength - startIndex;
                } else {
                    cLength = length;
                }
                byte[] tempBytes = getMiddleBytes(inBytes, startIndex, cLength);
                retStr = new String(tempBytes, ENCODING_SHIFTJIS);
            }

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return retStr;
    }

    /**
     * 文字列分割
     * 
     * @param str 対象文字列
     * @return 分割した文字配列
     */
    public static String[] splitPreserveAllTokens(String str) {

        if (str == null) {
            return null;
        }

        String convertStr = str;

        List<String> strList = new ArrayList<String>();
        int index = convertStr.indexOf(',');

        while (index != -1) {
            String t = convertStr.substring(0, index);
            strList.add(t);

            convertStr = convertStr.substring(index + 1);
            index = convertStr.indexOf(",");
        }
        strList.add(convertStr);

        return strList.toArray(new String[] {});
    }

    /**
     * バイト数列からShift-Jis文字列に変換
     * 
     * @param fromByte バイト数列
     * @return Shift-Jis文字列
     */
    public static String convertBytesToJisString(byte[] fromByte) {

        if (fromByte == null || fromByte.length == 0) {
            return "";
        }

        String retStr = "";
        try {
            retStr = new String(fromByte, ENCODING_SHIFTJIS);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return retStr;
    }

    /**
     * Shift-Jis文字列からバイト数列に変換
     * 
     * @param fromStr Shift-Jis文字列
     * @return らバイト数列
     */
    public static byte[] convertJisStringToBytes(String fromStr) {

        byte[] retBytes = new byte[] {};

        if (StringUtil.isEmpty(fromStr)) {
            return retBytes;
        }

        try {
            retBytes = fromStr.getBytes(ENCODING_SHIFTJIS);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return retBytes;
    }

    /**
     * バイトからShiftJis文字列に変換する
     * 
     * @param newBytes バイト
     * @return ShiftJis文字列
     */
    public static String getJisString(byte[] newBytes) {

        String tempStr = "";
        try {
            tempStr = new String(newBytes, ENCODING_SHIFTJIS);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return tempStr;
    }

    /**
     * 文字列のサイズを戻る
     * 
     * @param str 文字列
     * @return サイズ（null:0 以外は文字のlength）
     */
    public static int length(String str) {

        if (str == null) {
            return 0;
        } else {
            return str.length();
        }
    }

    /**
     * 対象文字列のバイト数は指定長さより未満の場合、先頭に指定文字を埋め込み
     * 
     * @param resultStr 対象文字列
     * @param len 長さ
     * @param str 文字
     * @return 結果文字列
     */
    public static String padLeftByByte(String resultStr, int len, char str) {

        StringBuilder buf = new StringBuilder(nvl(resultStr));

        try {
            while (buf.toString().getBytes(ENCODING_SHIFTJIS).length < len) {
                buf.insert(0, str);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return buf.toString();
    }

    /**
     * 対象文字列のバイト数は指定長さより未満の場合、後ろに指定文字を埋め込み
     * 
     * @param resultStr 対象文字列
     * @param len 長さ
     * @param str 文字
     * @return 結果文字列
     */
    public static String padRightByByte(String resultStr, int len, char str) {

        StringBuilder buf = new StringBuilder(nvl(resultStr));

        try {
            while (buf.toString().getBytes(ENCODING_SHIFTJIS).length < len) {
                buf.append(str);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return buf.toString();
    }

  

    /**
     * 正規表現文字列の取得
     * 
     * @param str 対象文字列
     * @param flg 固定位置フラグ
     * @return 正規表現文字列
     */
    public static String getRegularExpression(String str, boolean flg) {

        // 正規表現文字列
        String regEx = str;
        // "."を"\."に変換する
        regEx = regEx.replaceAll("\\.", "\\\\.");
        // "*"を".*"に変換する
        regEx = regEx.replaceAll("\\*", ".*");
        // "?"を".?"に変換する
        regEx = regEx.replaceAll("\\?", ".?");

        if (flg) {
            regEx = StringUtil.concat("^", regEx, "$");
        }

        return regEx;
    }

}
