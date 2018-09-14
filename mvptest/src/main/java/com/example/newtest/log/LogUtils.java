package com.example.newtest.log;

import android.text.TextUtils;
import android.util.Log;


import com.example.newtest.AppConfig;

import java.io.UnsupportedEncodingException;

public class LogUtils {
	//规定每段显示的长度
	private static int LOG_MAX_LENGTH = 4000;
	private static char MYLOG_TYPE = 'v';
	public static void w(String tag, Object msg) { 
		log(tag, msg.toString(), 'w');
	}

	public static void e(String tag, Object msg) { 
		log(tag, msg.toString(), 'e');
	}

	public static void d(String tag, Object msg) {
		log(tag, msg.toString(), 'd');
	}

	public static void i(String tag, Object msg) {
		log(tag, msg.toString(), 'i');
	}

	public static void v(String tag, Object msg) {
		log(tag, msg.toString(), 'v');
	}

	public static void w(String tag, String text) {
		log(tag, text, 'w');
	}

	public static void e(String tag, String text) {
		log(tag, text, 'e');
	}

	public static void d(String tag, String text) {
 		log(tag, text, 'd');
	}

	public static void i(String tag, String text) {
		log(tag, text, 'i');
	}

	public static void v(String tag, String text) {
		log(tag, text, 'v');
	}

	/**
	 *
	 * @param tag
	 * @param msg
	 * @param level
	 * @return void
	 * @since v 1.0
	 */
	private static  void log(String tag, final String msg, char level) {

		if (AppConfig.LOGGING) {
			if (!TextUtils.isEmpty(msg)){

			synchronized (LogUtils.class){

					if (msg.length()<=LOG_MAX_LENGTH){
						lastLog(tag, msg, level);
					}else {

						lastLog(tag, msg.substring(0, LOG_MAX_LENGTH - 1), level);
						log(tag, msg.substring(LOG_MAX_LENGTH, msg.length()), level);
					}
				}
			}

		}
	}

	private static void lastLog(final String tag, final String msg, final char level) {
		if ('e' == level && ('e' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Log.e(tag, msg);
        } else if ('w' == level && ('w' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Log.w(tag, msg);
        } else if ('d' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Log.d(tag, msg);
        } else if ('i' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Log.i(tag, msg);
        } else {
            Log.v(tag, msg);
        }
	}
}

