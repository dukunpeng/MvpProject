package com.example.newtest.tyjk;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
public class StringUtils {
	// time format
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_1 = "yyyy/MM/dd";
	public static final String DATE_FORMAT_2 = "dd/MM/yyyy";
	public static final String TIME_FORMAT = "HH:mm";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String LONG_DATE_TIME_FORMAT = "yyMMddhhmmssSSS";

	public static final String GBK_charset="utf-8";
	public static final String ISO_8859_1 = "ISO-8859-1";

	// boolean format
	public static final int BOOLEAN_01 = 0;
	public static final int BOOLEAN_YESNO = 1;
	public static final int BOOLEAN_TRUEFLASE = 2;

	// pad format
	public static final int STR_PAD_LEFT = 1;
	public static final int STR_PAD_RIGHT = 2;
	public static final int STR_PAD_BOTH = 3;

	// email address pattern
	public static final String EMAIL_ADDRESS_PATTERN = "^(\\w+([-+.]\\w+)*@\\w+([-]\\w+)*.\\w+)$|^(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+).\\w+$";

	private static Hashtable htmlEntityTable = null;
	private static final String[] HTML_ENTITIES = { ">", "&gt;", "<", "&lt;",
			"&", "&amp;", "\"", "&quot;", "'", "&#039;", "//", "&#092;",
			"\u00a9", "&copy;", "\u00ae", "&reg;" };

	// BSC00015P
	private static final String beginFormula = "{#";
	private static final String endFormula = "}";
	// system root path
	public static String sysRootPath = "";

	/**
	 * return null input string as emptry string
	 * 
	 * @param s
	 *            input String
	 * @return String
	 */
	public static String null2Str(String s) {
		return null2Str(s, "");
	}

	/**
	 * return null input string as emptry string
	 * 
	 * @param s
	 *            input String
	 * @param def
	 *            default String if input s is null
	 * @return String
	 */
	public static String null2Str(String s, String def) {
		if (isNull(s))
			return def;
		else
			return s.trim();
	}

	/**
	 * check if the input string is null
	 * 
	 * @param s
	 *            String
	 * @return boolean
	 */
	public static boolean isNull(String s) {
		if (s == null || s.trim().equals(""))
			return true;
		else
			return false;
	}

	/**
	 * return 0 if input string is null or integer of input string
	 * 
	 * @param s
	 *            input String
	 * @return int
	 */
	public static int str2Int(String s) {
		int i = 0;
		try {
			// if the number format is "2,000" , clean the ","
			i = Integer.parseInt(strReplace(",", "", null2Str(s, "0")));
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}

	/**
	 * return 0 if input string is null or integer of input string
	 * 
	 * @param s
	 *            input String
	 * @return long
	 */
	public static long str2Long(String s) {
		long i = 0;
		try {
			// if the number format is "2,000" , clean the ","
			i = Long.parseLong(strReplace(",", "", null2Str(s, "0")));
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}

	/**
	 * return String of input integer
	 * 
	 * @param i
	 *            integer
	 * @return String
	 */
	public static String int2Str(int i) {
		return int2Str(i, false);
	}

	/**
	 * return String of input integer
	 * 
	 * @param i
	 *            integer
	 * @param bShowZero
	 *            boolean
	 * @return String
	 */
	public static String int2Str(int i, boolean bShowZero) {
		return int2Str(i, bShowZero, false);
	}

	/**
	 * return String of input integer
	 * 
	 * @param i
	 *            integer
	 * @param bShowZero
	 *            boolean
	 * @param bShowGroup
	 *            boolean
	 * @return String
	 */
	public static String int2Str(int i, boolean bShowZero, boolean bShowGroup) {
		if (!bShowZero && i == 0) {
			return "";
		} else {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(bShowGroup);
			nf.setMinimumIntegerDigits(1);
			nf.setMaximumFractionDigits(0);
			nf.setMinimumFractionDigits(0);
			return nf.format(i);
		}

	}

	/**
	 * return String of input double
	 * 
	 * @param d
	 * @return
	 */
	public static String double2Str(double d) {
		return double2Str(d, 2);
	}

	/**
	 * return String of input double with decimal points
	 * 
	 * @param d
	 * @param decimalPoints
	 * @return
	 */
	public static String double2Str(double d, int decimalPoints) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(true);
		nf.setMinimumIntegerDigits(1);
		nf.setMaximumFractionDigits(decimalPoints);
		nf.setMinimumFractionDigits(decimalPoints);
		return nf.format(d);
	}

	/**
	 * 鎸夊皬鏁颁綅鍥涜垗浜斿叆
	 * 
	 * @param d
	 * @return
	 */
	public static Double doubleRound(double d, int decimalPoints) {
		double p = Math.pow(10, decimalPoints);
		return Math.round(d * p) / p;
	}

	/**
	 * return double of input string value (can input format is "2,000" )
	 * 鐩存帴鎴彇鍥哄畾灏忔暟浣�,鏈洓鑸嶄簲鍏�
	 * 
	 * @param s
	 * @return
	 */
	public static double str2Double(String s) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(true);
		try {
			// if the number format is "2,000" , clean the ","
			return nf.parse(strReplace(",", "", null2Str(s, "0")))
					.doubleValue();
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * return Date of input string
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static java.util.Date str2Date(String s) {
		return str2Date(s, DATE_FORMAT);
	}

	/**
	 * return Date of input string
	 *
	 * @param s
	 *            String
	 * @param format
	 *            String
	 * @return String
	 */
	public static java.util.Date str2Date(String s, String format) {
		java.util.Date dRet = null;
		SimpleDateFormat sdf = null;
		try {
			sdf = new SimpleDateFormat(format);
			dRet = sdf.parse(s);
			return dRet;
		} catch (ParseException pe) {
		}
		try {
			sdf = new SimpleDateFormat(DATE_FORMAT);
			dRet = sdf.parse(s);
			return dRet;
		} catch (ParseException pe) {
		}
		try {
			sdf = new SimpleDateFormat(DATE_FORMAT_1);
			dRet = sdf.parse(s);
			return dRet;
		} catch (ParseException pe) {
		}
		try {
			sdf = new SimpleDateFormat(DATE_FORMAT_2);
			dRet = sdf.parse(s);
			return dRet;
		} catch (ParseException pe) {
		}

		return dRet;
	}

	/**
	 * return String of input date
	 *
	 * @param d
	 *            Date
	 * @return String
	 */
	public static String date2Str(java.util.Date d) {
		return date2Str(d, DATE_FORMAT);
	}

	/**
	 * return String of input date
	 *
	 * @param d
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public static String date2Str(java.util.Date d, String format) {
		if (d == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(d);
	}

	/**
	 * return String of input date
	 *
	 * @param d
	 *            Date
	 * @return String
	 */
	public static String time2Str(java.util.Date d) {
		return date2Str(d, TIME_FORMAT);
	}

	/**
	 * return String of input date
	 *
	 * @param d
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public static String datetime2Str(java.util.Date d) {
		if (d == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		return sdf.format(d);
	}

	/**
	 * 灏哠tring绫诲瀷鏃ユ湡杞寲鎴恓ava.sql.Date绫诲瀷"2003-01-01"鏍煎紡
	 *
	 * @param str
	 *            String
	 * @param format
	 *            String
	 * @return Date
	 */
	public static java.sql.Date str2SqlDate(String str, String format) {
		if (str == null || format == null) {
			return null;
		}
		return new java.sql.Date(str2Date(str, format).getTime());
	}

	/**
	 * 灏哠tring绫诲瀷鏃ユ湡杞寲鎴恓ava.sql.Date绫诲瀷"2003-01-01"鏍煎紡
	 *
	 * @param str
	 *            String
	 * @return Date
	 */
	public static java.sql.Date str2SqlDate(String str) {

		if (str == null) {
			return null;
		}
		return new java.sql.Date(str2Date(str).getTime());
	}

	/**
	 * 灏哠tring 绫诲瀷杞寲涓篵oolean绫诲瀷,鎺ュ彈"true" ,"false" , "0" ,
	 * "1","Y","N","YES","NO"绛夌被鍨�,鍏朵粬褰㈠紡涓篺alse杩斿洖
	 *
	 * @param str
	 * @return
	 */
	public static boolean str2boolean(String str) {
		if (str == null) {
			return false;
		} else if (str.equalsIgnoreCase("true") || str.equals("1")
				|| str.equalsIgnoreCase("Y") || str.equalsIgnoreCase("YES")) {
			return true;
		} else if (str.equalsIgnoreCase("false") || str.equals("0")
				|| str.equalsIgnoreCase("N") || str.equalsIgnoreCase("NO")) {
			return false;
		} else {
			return false;
		}
	}

	/**
	 * 灏哹oolean绫诲瀷杞寲涓篠tring
	 * 绫诲瀷杈撳嚭,鏍煎紡鏈�"0","1","Y","N","T","F"绛夌被鍨�,榛樿浣跨敤"0","1"
	 *
	 * @param str
	 * @return
	 */
	public static String boolean2Str(boolean bl, int format) {
		switch (format) {
		case BOOLEAN_01: {
			return bl ? "1" : "0";
		}
		case BOOLEAN_YESNO: {
			return bl ? "Y" : "N";
		}
		case BOOLEAN_TRUEFLASE: {
			return bl ? "T" : "F";
		}
		default:
			return "0";
		}
	}

	/**
	 * 灏哹oolean绫诲瀷杞寲涓篠tring 绫诲瀷杈撳嚭,榛樿浣跨敤"0","1"
	 *
	 * @param str
	 * @return
	 */
	public static String boolean2Str(boolean bl) {
		return boolean2Str(bl, BOOLEAN_01);
	}

	/**
	 * 灏唈ava.util.Date鏃ユ湡杞寲鎴恓ava.sql.Date绫诲瀷
	 *
	 * @param Date
	 * @return 鏍煎紡鍖栧悗鐨刯ava.sql.Date
	 */
	public static java.sql.Date toSqlDate(java.util.Date date) {

		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 灏唈ava.util.Date鏃ユ湡杞寲鎴恓ava.sql.Timestamp绫诲瀷
	 *
	 * @param Date
	 * @return 鏍煎紡鍖栧悗鐨刯ava.sql.Timestamp
	 */
	public static java.sql.Timestamp toSqlTimestamp(java.util.Date date) {

		if (date == null) {
			return null;
		}
		return new java.sql.Timestamp(date.getTime());
	}

	/**
	 * 灏嗘棩鍘嗚浆鍖栦负鏃ユ湡
	 *
	 * @param calendar
	 *            Calendar
	 * @return Date
	 */
	public static java.util.Date converToDate(java.util.Calendar calendar) {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 灏嗘棩鏈熻浆鍖栦负鏃ュ巻
	 *
	 * @param date
	 *            Date
	 * @return Calendar
	 */
	public static java.util.Calendar converToCalendar(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 姹傚緱浠庢煇澶╁紑濮嬶紝杩囦簡鍑犲勾鍑犳湀鍑犳棩鍑犳椂鍑犲垎鍑犵鍚庯紝鏃ユ湡鏄灏� 鍑犲勾鍑犳湀鍑犳棩鍑犳椂鍑犲垎鍑犵鍙互涓鸿礋鏁�
	 *
	 * @param date
	 *            Date
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @param day
	 *            int
	 * @param hour
	 *            int
	 * @param min
	 *            int
	 * @param sec
	 *            int
	 * @return Date
	 */
	public static java.util.Date modifyDate(java.util.Date date, int year,
			int month, int day, int hour, int min, int sec) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DATE, day);
		cal.add(Calendar.HOUR, hour);
		cal.add(Calendar.MINUTE, min);
		cal.add(Calendar.SECOND, sec);

		return cal.getTime();

	}

	/**
	 * 鍙栧緱褰撳墠鏃ユ湡鏃堕棿 1:year 2:month 3:date 4:day
	 */
	public static int getCurTime(int i) {
		if (i == 1) {
			return java.util.Calendar.getInstance().get(Calendar.YEAR);
		} else if (i == 2) {
			return java.util.Calendar.getInstance().get(Calendar.MONTH) + 1;
		} else if (i == 3) {
			return java.util.Calendar.getInstance().get(Calendar.DATE);
		} else if (i == 4) {
			int temp = java.util.Calendar.getInstance().get(
					Calendar.DAY_OF_WEEK);
			if (temp == 1) {
				temp = 7;
			} else {
				temp--;
			}
			return temp;
		}

		return 0;

	}

	/**
	 * Repeat string with multiplier times.
	 *
	 * @param input
	 * @param multiplier
	 * @return
	 */
	public static String strRepeat(String input, int multiplier) {
		StringBuffer sb = new StringBuffer("");
		if (isNull(input)) {
			input = " ";
		}

		for (int i = 0; i < multiplier; i++) {
			sb.append(input);
		}
		return sb.toString();
	}

	/**
	 * return string with padString and length . defualt padstring in left .
	 *
	 * @param input
	 * @param length
	 * @param padString
	 * @return
	 */
	public static String strPad(String input, int length, String padString) {

		return strPad(input, length, padString, STR_PAD_LEFT);
	}

	/**
	 * return string with padString using length .
	 *
	 * @param input
	 * @param length
	 * @param padString
	 * @param padType
	 * @return
	 */
	public static String strPad(String input, int length, String padString,
                                int padType) {
		int multiplier = 0;
		String tmpStr = "";
		String outStr = "";
		int pos = 0;

		input = null2Str(input, "");
		padString = null2Str(padString, " ");

		if (input.length() >= length)
			return input;

		multiplier = (int) Math.ceil((double) length
				/ (double) padString.length());
		tmpStr = strRepeat(padString, multiplier);

		if (padType == STR_PAD_RIGHT) {
			pos = length - input.length();
		} else if (padType == STR_PAD_BOTH) {
			pos = ((length - input.length()) / 2);
		} else {
			pos = 0;
		}

		if (pos > 0) {
			outStr = tmpStr.substring(0, pos);
		}
		outStr += input;
		outStr += tmpStr.substring(pos + input.length(), length);

		return outStr;
	}

	/**
	 * If Java 1.4 is unavailable, the following technique may be used.
	 *
	 * @param aInput
	 *            is the original String which may contain substring aOldPattern
	 * @param aOldPattern
	 *            is the non-empty substring which is to be replaced
	 * @param aNewPattern
	 *            is the replacement for aOldPattern
	 */
	public static String strReplace(final String aOldPattern,
                                    final String aNewPattern, final String aInput) {
		if (aOldPattern == null || aOldPattern.equals("")) {
			throw new IllegalArgumentException("Old pattern must have content.");
		}

		if (aInput == null || aInput.equals("")) {
			return aInput;
		}

		if (aNewPattern == null) {
			throw new IllegalArgumentException("New pattern must not null.");
		}

		if (aNewPattern.equals(aOldPattern))
			return aInput;

		final StringBuffer result = new StringBuffer();
		// startIdx and idxOld delimit various chunks of aInput; these
		// chunks always end where aOldPattern begins
		int startIdx = 0;
		int idxOld = 0;
		while ((idxOld = aInput.indexOf(aOldPattern, startIdx)) >= 0) {
			// grab a part of aInput which does not include aOldPattern
			result.append(aInput.substring(startIdx, idxOld));
			// add aNewPattern to take place of aOldPattern
			result.append(aNewPattern);
			// reset the startIdx to just after the current match, to see
			// if there are any further matches
			startIdx = idxOld + aOldPattern.length();
		}
		// the final chunk will go to the end of aInput
		result.append(aInput.substring(startIdx));
		return result.toString();
	}

	/**
	 * return a string arraylist separator by Separator of input.
	 *
	 * @param aSeparator
	 * @param aInput
	 * @return
	 */
	public static ArrayList strSplit(final String aSeparator,
                                     final String aInput) {
		ArrayList token = new ArrayList();

		if (aSeparator == null || aSeparator.equals("")) {
			token.add(aInput);
			return token;
		}
		if (aInput == null || aInput.equals("")) {
			return token;
		}

		// final StringBuffer result = new StringBuffer();
		// startIdx and idxOld delimit various chunks of aInput; these
		// chunks always end where aOldPattern begins
		int startIdx = 0;
		int idxOld = 0;
		while ((idxOld = aInput.indexOf(aSeparator, startIdx)) >= 0) {
			// grab a part of aInput which does not include aOldPattern

			token.add(aInput.substring(startIdx, idxOld));
			// reset the startIdx to just after the current match, to see
			// if there are any further matches
			startIdx = idxOld + aSeparator.length();
		}
		// the final chunk will go to the end of aInput
		token.add(aInput.substring(startIdx));

		return token;
	}

	/**
	 * return string show file size .
	 *
	 * @param size
	 * @return
	 */
	public static String formatFileSize(long size) {
		if (size > 0 && size < 1024) {
			return Long.toString(size) + " B";
		} else if (size >= 1024 && size < 1024 * 1024) {
			return StringUtils.double2Str((double) size / 1024) + " KB";
		} else if (size >= 1024 * 1024) {
			return StringUtils.double2Str((double) size / 1024 / 1024) + " MB";
		} else {
			return "0 B";
		}
	}

	/***
	 * Converts entity array to hashtable
	 */
	private static synchronized void buildEntityTable() {
		htmlEntityTable = new Hashtable(HTML_ENTITIES.length);

		for (int i = 0; i < HTML_ENTITIES.length; i += 2) {
			if (!htmlEntityTable.containsKey(HTML_ENTITIES[i])) {
				htmlEntityTable.put(HTML_ENTITIES[i], HTML_ENTITIES[i + 1]);
			}
		}
	}

	/***
	 * Converts a single character to HTML
	 */
	private static String encodeSingleChar(String ch) {
		return (String) htmlEntityTable.get(ch);
	}

	/***
	 * Converts a String to HTML by converting all special characters to
	 * HTML-entities.
	 */
	public final static String encodeHTML(String s) {
		final String CR = "<BR>";

		if (htmlEntityTable == null) {
			buildEntityTable();
		}
		if (s == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer(s.length() * 2);

		char ch;
		for (int i = 0; i < s.length(); ++i) {
			ch = s.charAt(i);
			if ((ch >= 63 && ch <= 90) || (ch >= 97 && ch <= 122)
					|| (ch == ' ')) {
				sb.append(ch);
			} else if (ch == '\n') {
				sb.append(CR);
			} else {
				String chEnc = encodeSingleChar(String.valueOf(ch));
				if (chEnc != null) {
					sb.append(chEnc);
				} else {
					sb.append(ch);
					/*
					 * // Not 7 Bit use the unicode system sb.append("&#");
					 * sb.append(new Integer(ch).toString()); sb.append(';');
					 */
				}
			}
		}
		return sb.toString();
	}

	/**
	 * source like: command1=value1;command2=value2 ... get the value n from
	 * command n string.
	 *
	 * @param source
	 * @param command
	 * @param delimi
	 * @return valuen
	 */
	public static String getCommand(String source, String command, String delimi) {
		String result = "";
		StringTokenizer st = new StringTokenizer(source, delimi, false);
		while (st.hasMoreTokens()) {
			StringTokenizer stcomm = new StringTokenizer(
					null2Str(st.nextToken()), "=", false);
			while (stcomm.hasMoreTokens()) {
				if (null2Str(stcomm.nextToken()).equalsIgnoreCase(command)) {
					result = null2Str(stcomm.nextToken());
					return result;
				}
			}
		}
		return result;
	}

	/**
	 * change the path string use the os native string.
	 *
	 * @param path
	 * @return right path string of system.
	 */
	public static String pathName(String path) {
		String pathName = null;
		String OSName = System.getProperty("os.name");
		if (OSName.toLowerCase().indexOf("window") > 0) {
			pathName = strReplace("\\", "/", path);
		} else {
			pathName = strReplace("/", "\\", path);
		}
		return pathName;
	}

	/**
	 * change the sql string in statement using prepared SQL .
	 *
	 * @param fieldName
	 * @param values
	 * @return the string is fit for prepared sqlstatement.
	 */
	public static String concatForPreparedSQL(String fieldName, ArrayList values) {
		String result = "";
		if ((values == null) || (values.size() == 0)) {
			return " (1=1) ";
		}

		/*
		 * can't use the statment to optimize if (values.size() == 1){ return
		 * " " + fieldName + " = '" + null2Str((String)values.get(0)) + "'"; }
		 */
		for (int i = 0; i < values.size(); i++) {
			result += "?,";
		}
		result = result.substring(0, result.length() - 1);
		result = " " + fieldName + " in ( " + result + " ) ";
		return result;
	}

	/**
	 * return string of year , using 'YY' format
	 *
	 * @param year
	 * @return
	 */
	public static String strYear(int year) {
		String str = String.valueOf(year);
		if (str.length() == 4)
			str = str.substring(2);
		return str;
	}

	/**
	 * return String of month , using 'MM' format .
	 *
	 * @param month
	 * @return
	 */
	public static String strMon(int month) {
		return strPad(Integer.toString(month), 2, "0", STR_PAD_RIGHT);
	}

	/**
	 * return String Serial NO using left padding with '0'.
	 *
	 * @param serialno
	 * @param length
	 * @return
	 */
	public static String strSerial(int serialno, int length) {
		return strPad(Integer.toString(serialno), length, "0", STR_PAD_RIGHT);
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	public static String strRight(String str) {
		str = "000" + str;
		int len = str.length();
		return str.substring(len - 3);
	}

	/**
	 * Validate the input string which is digit or char.
	 *
	 * @param s
	 *            , input string
	 * @return true for digit, false for char
	 * @version 1.0 shine new
	 */
	public static boolean isDigit(String s) {
		if (s == null)
			return false;
		return Pattern.matches("^\\d+$", s);
	}

	/**
	 * Count the date2 from date1 with second depart.
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getQuotSecond(Date date1, Date date2) {
		long quot = 0;
		quot = date1.getTime() - date2.getTime();
		quot = quot / 1000;
		return quot;
	}

	/**
	 * Count the date2 from date1 with minute depart.
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getQuotMinute(Date date1, Date date2) {
		long quot = 0;
		quot = date1.getTime() - date2.getTime();
		quot = quot / 1000 / 60;
		return quot;
	}

	/**
	 * Count the date2 from date1 with hour depart.
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getQuotHour(Date date1, Date date2) {
		long quot = 0;
		quot = date1.getTime() - date2.getTime();
		quot = quot / 1000 / 60 / 60;
		return quot;
	}

	/**
	 * Count the date2 from date1 with days depart.
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getQuot(Date date1, Date date2) {
		long quot = 0;
		quot = date1.getTime() - date2.getTime();
		quot = quot / 1000 / 60 / 60 / 24;
		return quot;
	}

	/**
	 * Count the datetime2 from datetime1 with days depart.
	 *
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long getQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}

	/**
	 * Count the datetime1 from now with days depart.
	 *
	 * @param time1
	 * @return
	 */
	public static long getQuot(String time1) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = ft.parse(time1);
			quot = new Date().getTime() - date.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}

	/**
	 * return the SQLCode from the SQL error messag. When input null, it will
	 * return "". e.g: input "[SQL0104] Token SELEC was not valid.", returns
	 * "SQL0104".
	 *
	 * @param errMsg
	 *            , e.g: SQLException.getMessage().
	 * @return String, e.g: "SQL0104"
	 */
	public static String getSQLErrCode(String errMsg) {
		if (errMsg == null)
			return "";

		int startIndex = errMsg.indexOf("[");
		if (startIndex == -1)
			return "";

		int endIndex = errMsg.indexOf("]");
		if (endIndex == -1)
			return "";

		if (++startIndex > endIndex)
			return "";
		return errMsg.substring(startIndex, endIndex).trim();
	}

	/**
	 * return the SQL Message from the SQL error messag. When input null, it
	 * will return "". e.g: input "[SQL0104] Token SELEC was not valid.",
	 * returns "Token SELEC was not valid.".
	 *
	 * @param errMsg
	 *            , e.g: SQLException.getMessage().
	 * @return String, e.g: "Token SELEC was not valid."
	 */
	public static String getSQLErrMessage(String errMsg) {
		if (errMsg == null)
			return "";

		Pattern pattern = Pattern.compile("\\[.*\\]");
		Matcher matcher = pattern.matcher(errMsg);
		return matcher.replaceFirst("").trim();
	}

	/**
	 * SQL Injection Charator checking .
	 *
	 * @param str
	 * @return
	 */
	public static boolean sql_inj(String str) {
		String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
		ArrayList inj_stra = strSplit(inj_str, "|");
		for (int i = 0; i < inj_stra.size(); i++) {
			if (str.indexOf((String) inj_stra.get(i)) >= 0) {
				return true;
			}
		}
		return false;
	}

	public static boolean getValidIdentityNo(String xidentityno) {
		if (xidentityno.length() != 18) {
			throw new IllegalArgumentException("韬唤璇侀暱搴︿笉绗�");
		}
		char A[] = ((xidentityno.toUpperCase()).trim()).toCharArray();
		int W[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
		int s = 0;
		int m = 0;
		int b = 0;
		for (int i = 1; i <= 17; i++) {
			b = new Integer(A[i - 1]).intValue();
			if (b < 48 || b > 57) {
				throw new IllegalArgumentException("韬唤璇佸彿鐮佸繀闇�涓烘暟瀛�");
			}
			m = str2Int(String.valueOf(A[i - 1]));
			s = s + m * W[i - 1];
		}
		int y = s % 11;
		String v = "";
		if (y == 0)
			v = "1";
		if (y == 1)
			v = "0";
		if (y == 2)
			v = "X";
		if (y >= 3 && y <= 10)
			v = new Integer(12 - y).toString();
		String idno = xidentityno.substring(0, 17) + v;
		if (!idno.equals(xidentityno)) {
			throw new IllegalArgumentException("韬唤璇佸彿鐮佷笉瀵�!");
		}
		return true;
	}

	/**
	 * 楠岃瘉韬唤璇侊紙鎶刧etValidIdentityNo 鏂规硶锛夛紝鍖哄埆锛氭牴鎹敊璇被鍨嬭繑鍥炰笉鍚岀殑鍊硷紝鑰屼笉鏄洿鎺ユ姏鍑洪敊璇�
	 *
	 * @param xidentityno
	 * @return
	 * @version 1.1 habe
	 */
	public static int getValidIdentityNoReturnType(String xidentityno) {
		if (xidentityno.length() != 18) {
			return 1;// ("韬唤璇侀暱搴︿笉绗�");
		}
		char A[] = ((xidentityno.toUpperCase()).trim()).toCharArray();
		int W[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
		int s = 0;
		int m = 0;
		int b = 0;
		for (int i = 1; i <= 17; i++) {
			b = new Integer(A[i - 1]).intValue();
			if (b < 48 || b > 57) {
				return 2;// ("韬唤璇佸彿鐮佸繀闇�涓烘暟瀛�");
			}
			m = str2Int(String.valueOf(A[i - 1]));
			s = s + m * W[i - 1];
		}
		int y = s % 11;
		String v = "";
		if (y == 0)
			v = "1";
		if (y == 1)
			v = "0";
		if (y == 2)
			v = "X";
		if (y >= 3 && y <= 10)
			v = new Integer(12 - y).toString();
		String idno = xidentityno.substring(0, 17) + v;
		if (!idno.equals(xidentityno)) {
			return 3;// ("韬唤璇佸彿鐮佷笉瀵�!");
		}
		return 0;
	}

	/**
	 * 楠岃瘉email鏍煎紡
	 *
	 * @param email
	 * @return 鏄惁绗﹀悎鏍煎紡
	 */
	public static boolean verifyEmail(String email) {
		if (email == null || "".equals(email)) {
			return false;
		}
		if (email.indexOf('@') < 1) {
			return false;
		}
		return Pattern.matches(EMAIL_ADDRESS_PATTERN, email);
	}

	/**
	 * 灏唈ava.sql.Date鏃ユ湡杞寲鎴恓ava.util.Date绫诲瀷
	 *
	 * @param Date
	 * @return 鏍煎紡鍖栧悗鐨刯ava.sql.Date
	 */
	public static java.util.Date toUtilDate(java.sql.Date date) {

		if (date == null) {
			return null;
		}
		return new java.util.Date(date.getTime());
	}

	/**
	 * 杞崲瀛楃缂栫爜
	 *
	 * @param str
	 * @param beginCharset
	 * @param endCharset
	 * @return
	 */
	public static String changeCharset(String str, String beginCharset,
                                       String endCharset) {
		try {
			return new String(str.getBytes(beginCharset), endCharset);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	/**
	 * 鍒ゆ柇sourceStr 鏄惁绗﹀悎 patern瑙勫垯. 渚嬪: 鍒ゆ柇鏄惁杈撳叆鐨勬槸鍚﹀彧鏈夋暟瀛楀拰瀛楁瘝 pattern =
	 * /[A-Za-z0-9]/
	 *
	 * @param pattern
	 * @param sourceStr
	 * @return
	 */
	public static boolean testStr(String pattern, String sourceStr) {
		return Pattern.matches(pattern, sourceStr);
	}

	/**
	 * 澶氶�夌殑鍙傛暟锛岃浆鎹负SP杈撳叆鐨刾arameter
	 *
	 * @param para
	 * @return
	 */
	public static String strMulti2Parameter(String para) {

		ArrayList array = StringUtils.strSplit(";", para);
		StringBuffer retString = new StringBuffer();
		int size = array.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				if (i != 0)
					retString.append(",");
				retString.append("'''" + (String) array.get(i) + "'''");
			}
		}
		return retString.toString();
	}

	/**
	 * 澶氶�夌殑鍙傛暟锛岃浆鎹负SP杈撳叆鐨刾arameter
	 *
	 * @param para
	 * @return
	 */
	public static String strMulti2ParameterForSQL(String para) {

		ArrayList array = StringUtils.strSplit(";", para);
		StringBuffer retString = new StringBuffer();
		int size = array.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				if (i != 0)
					retString.append(",");
				retString.append("'" + (String) array.get(i) + "'");
			}
		}
		return retString.toString();
	}

	/**
	 * 瀛楃涓叉浛鎹�
	 *
	 * @param regex
	 * @param replacement
	 * @param inputChars
	 * @return
	 */
	public static String replaceAll(String regex, String replacement,
                                    String inputString) {
		if (inputString == null)
			throw new RuntimeException(" 'inputString' can not be null! ");
		return inputString.replaceAll(regex, replacement);
	}

	/**
	 * 鑾峰彇鐗瑰畾鐨勬棩鏈�, 濡傛棤瀹氫箟涓殑鍐呭锛屽垯杩斿洖浠婂ぉ LAST_MONTHN_FIRST_DATE = 涓婁竴涓湀棣栨棩
	 * LAST_MONTHN_LAST_DATE = 涓婁竴涓湀鏈�鍚庝竴鏃�
	 *
	 * @param defined
	 * @return
	 */
	public static java.util.Date getDateByDefine(String define) {
		// defined : LAST_MONTHN_FIRST_DATE
		// defined : LAST_MONTHN_LAST_DATE
		java.util.Date date = new java.util.Date();
		Calendar c = new GregorianCalendar(TimeZone.getDefault(),
				Locale.getDefault());
		c.setTime(date);

		if (define.equalsIgnoreCase("LAST_MONTHN_FIRST_DATE")) {
			c.set(Calendar.DATE, 1); // 璁剧疆杩欎釜鏈堢殑绗竴澶�.
			date = modifyDate(c.getTime(), 0, -1, 0, 0, 0, 0); // 涓婁竴涓湀棣栨棩
		} else if (define.equalsIgnoreCase("LAST_MONTHN_LAST_DATE")) {
			c.set(Calendar.DATE, 1); // 璁剧疆杩欎釜鏈堢殑绗竴澶�.
			date = modifyDate(c.getTime(), 0, 0, -1, 0, 0, 0); // 涓婁竴涓湀鏈�鍚庝竴鏃�
		} else if (define.equalsIgnoreCase("LAST_LAST_WEEK_SATURDAY_DATE")) {
			int dayofweek = c.get(Calendar.DAY_OF_WEEK);
			date = modifyDate(c.getTime(), 0, 0, -(dayofweek + 7), 0, 0, 0);// 涓婁笂涓槦鏈熷叚
		} else if (define.equalsIgnoreCase("LAST_WEEK_FRIDAY_DATE")) {
			int dayofweek = c.get(Calendar.DAY_OF_WEEK);
			date = modifyDate(c.getTime(), 0, 0, -(dayofweek + 1), 0, 0, 0);// 涓婁釜鏄熸湡浜�
		} else if (define.equalsIgnoreCase("LAST_DATE")) {
			date = modifyDate(c.getTime(), 0, 0, -1, 0, 0, 0); // 鏄ㄥぉ
		}

		if (date == null) {
			return null;
		}
		return date;
	};

	public static java.sql.Date getSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * change String value to any type of sql input parameter suport :
	 * date,timestamp ,string ,bigdecimal,int,double ...
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public static Object str2Object(String type, String value) {
		Object rtn = null;

		String nullvalue = "null";
		type = null2Str(type);

		// date,timestamp,string,bigdecimal,int,double
		if (type.equals(""))
			return null;
		if (value == null)
			return null;
		value = null2Str(value);

		if (type.equalsIgnoreCase("string")) {
			return value;
		} else if (type.equalsIgnoreCase("date")) {
			return str2SqlDate(value);
		}
		return rtn;
	}

	/**
	 * 鍒ゆ柇鏂囦欢瀛樹笉瀛樺湪 鏂囦欢瑕佹寚瀹氬ソ鐩稿璺緞
	 * 
	 * @version 1.1
	 */
	public static boolean isFileExists(String fileName) {
		if ("".equals(fileName))
			return false;
		File currFile = new File(fileName);
		return currFile.exists();
	}

	/**
	 * 鑾峰彇褰撳墠椤圭洰鐨勬牴鐩綍
	 * 
	 * @return 绯荤粺鐩綍鍚�
	 * @version 1.1
	 */
	public static String getPath() {
		if ("".equals(sysRootPath)) {
			StringUtils o = new StringUtils();
			String projectPath = o.getClass().getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			String path = projectPath.substring(0,
					projectPath.lastIndexOf("/") - 1);
			sysRootPath = path.substring(0, path.lastIndexOf("/"));
		}
		return sysRootPath;

	}

	/**
	 * 鍒ゆ柇鏄惁琛ㄨ揪寮忓弬鏁�(鎷疯礉SystemSettingDAO)
	 * 
	 * @param value
	 * @return
	 * @version 1.1
	 */
	public static boolean isFormulaString(String value) {
		if (value == null)
			return false;
		int firstIndex = value.indexOf(beginFormula);
		int lastIndex = value.lastIndexOf(endFormula);
		if (lastIndex > firstIndex) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 鍒ゆ柇鏄惁琛ㄨ揪寮忓弬鏁�(鎷疯礉SystemSettingDAO)
	 * 
	 * @return
	 */
	public static String getFormulaString(String value) {
		if (value == null)
			return "";
		int firstIndex = value.indexOf(beginFormula);
		int lastIndex = value.lastIndexOf(endFormula);
		value = value.substring(firstIndex + 2, lastIndex);
		return value;
	}

	/**
	 * 鑾峰彇鏂囦欢鍚嶇殑鍚庣紑
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExtension(String fileName) {
		return getFileExtension(fileName, "xls");// default xls
	}

	/**
	 * 鑾峰彇鏂囦欢鍚嶇殑鍚庣紑
	 * 
	 * @param fileName
	 * @param defaultExtension
	 * @return
	 */
	public static String getFileExtension(String fileName,
                                          String defaultExtension) {
		if (fileName == null || fileName.equals("")) {
			return "";
		}
		int extensionIndex = fileName.lastIndexOf(".");
		if (extensionIndex < 0) {
			return "";
		} else if (extensionIndex == fileName.length() - 1) {
			return defaultExtension;
		} else {
			return fileName.substring(extensionIndex + 1);
		}
	}

	/**
	 * 鏍规嵁鏂囦欢璺緞鑾峰彇鏂囦欢鍚�
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileNameByPath(String filePath) {
		return getFileNameByPath(filePath, true, File.separator);
	}

	/**
	 * 鏍规嵁鏂囦欢璺緞鑾峰彇鏂囦欢鍚�
	 * 
	 * @param filePath
	 * @param getExtension
	 *            鏄惁鑾峰彇鎵╁睍鍚�
	 * @return
	 */
	public static String getFileNameByPath(String filePath, boolean getExtension) {
		return getFileNameByPath(filePath, getExtension, File.separator);
	}

	/**
	 * 鏍规嵁鏂囦欢璺緞鑾峰彇鏂囦欢鍚�
	 * 
	 * @param filePath
	 * @param getExtension
	 *            鏄惁鑾峰彇鎵╁睍鍚�
	 * @param separator
	 * @return
	 */
	public static String getFileNameByPath(String filePath,
                                           boolean getExtension, String separator) {
		if (filePath == null || filePath.equals("")) {
			return "";
		} else {
			int lastSeparatorIndex = filePath.lastIndexOf(separator);
			if (lastSeparatorIndex < 0) {
				if (getExtension) {
					return filePath;
				} else {
					int extensionIndex = filePath.lastIndexOf(".");
					return filePath.substring(0, extensionIndex);
				}

			} else if (lastSeparatorIndex == filePath.length() - 1) {
				return "";
			} else {
				if (getExtension) {
					return filePath.substring(lastSeparatorIndex + 1);
				} else {
					int extensionIndex = filePath.lastIndexOf(".");
					return filePath.substring(lastSeparatorIndex + 1,
							extensionIndex);
				}
			}
		}
	}

	/**
	 * 鑾峰彇鏂囦欢澶硅矾寰�
	 * 
	 * @param path
	 * @return
	 */
	public static String getFilePath(String path) {
		return getFilePath(path, File.separator);
	}

	/**
	 * 鑾峰彇鏂囦欢澶硅矾寰�
	 * 
	 * @param path
	 * @param separator
	 * @return
	 */
	public static String getFilePath(String path, String separator) {
		if (path == null || path.equals("")) {
			return "";
		} else {
			int lastSeparatorIndex = path.lastIndexOf(separator);
			if (lastSeparatorIndex < 0) {
				return "";
			} else {
				return path.substring(0, lastSeparatorIndex + 1);
			}
		}
	}

	/**
	 * 鍒ゆ柇鏈�鍚庣殑鏄惁鍒嗛殧绗�
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isLastSeparator(String path) {
		return isLastSeparator(path, File.separator);
	}

	/**
	 * 鍒ゆ柇鏈�鍚庣殑鏄惁鍒嗛殧绗�
	 * 
	 * @param path
	 * @param separator
	 * @return
	 */
	public static boolean isLastSeparator(String path, String separator) {
		if (path == null || path.length() == 0) {
			return false;
		}
		return path.substring(path.length() - 1).equals(separator);
	}

	/**
	 * 鍦ㄦ枃浠惰矾寰勪腑澧炲姞鏂囦欢鍚�
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String pathAddFileName(String path, String fileName) {
		if (null2Str(path).equals("")) {
			return "";
		}

		if (null2Str(fileName).equals("")) {
			return path;
		}

		if (isLastSeparator(path)) {
			return path + fileName;
		} else {
			return path + File.separator + fileName;
		}
	}

	/**
	 * 杩斿洖涓や釜瀛楃涓蹭腑闂寸殑鍐呭
	 * 
	 * @param all
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getMiddleString(String all, String start, String end) {
		int beginIdx = all.indexOf(start) + start.length();
		int endIdx = all.indexOf(end);
		return all.substring(beginIdx, endIdx);
	}
}
