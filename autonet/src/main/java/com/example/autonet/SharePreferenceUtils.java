package com.example.autonet;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceUtils {


	static final String PREFERENCES_NAME = "ZG_RJ";
	static final String PREFERENCES_CACHE_PHONE = "preferences_cache_phone";
	static final String PREFERENCES_CACHE_CODE = "preferences_cache_code";

	public static void keepContent(Context context, String tag, Object content) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		//tag = rewriteDataWithUserName(context, tag);
		if (content instanceof Boolean) {
			Boolean new_content = (Boolean) content;
			editor.putBoolean(tag, new_content);

		} else if (content instanceof String) {
			String new_content = (String) content;
			editor.putString(tag, new_content);

		} else if (content instanceof Long) {
			Long new_content = (Long) content;
			editor.putLong(tag, new_content);

		} else if (content instanceof Integer) {
			int new_content = (Integer) content;
			editor.putInt(tag, new_content);

		}
		editor.commit();
	}

	public static boolean readBoolean(Context context, String tag) {
		try {
			if (context == null) {
				 
			}
			SharedPreferences pref = context.getSharedPreferences(
					PREFERENCES_NAME, Context.MODE_PRIVATE);
		//	tag = rewriteDataWithUserName(context, tag);
			return pref.getBoolean(tag, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static String readString(Context context, String tag) {
		if (context == null) {
			//context = HpApplication.getInstance();
		}
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		return pref.getString(tag, "");
	}

	public static long readLong(Context context, String tag, long defaultVal) {
		if (context == null) {
		}
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		return pref.getLong(tag, defaultVal);
	}

	public static int readInt(Context context, String tag, int defaultVal) {
		if (context == null) {
		}
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		return pref.getInt(tag, defaultVal);
	}

	public static void clear(Context context) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}



}

