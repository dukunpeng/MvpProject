package com.example.autonet.contentprovider;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncomingSmsObserver extends ContentObserver {
	private Context context;
	public static final String PARAM_INTENT_AUTH_CODE = "param_intent_verify_code";
	public static final String INTENT_FILTER_MESSAGE_AUTH_CODE = "intent_filter_message_verify_code";

	private boolean isTagCreateOwn;
	private boolean isMatchWithRule;
	private String[] tags;
	private String[] platformTags;
	private List<Pattern> rules;

	public IncomingSmsObserver(Context context, Handler handler) {
		super(handler);
		this.context = context;
		Log.i("Leo-SmsObserver", "My Oberver on create");
	}

	public IncomingSmsObserver(Context context, Handler handler, String[] tags,
			String[] platformTags) {
		super(handler);
		this.context = context;
		this.isTagCreateOwn = true;
		this.tags = tags;
		this.platformTags = platformTags;
		Log.i("Leo-SmsObserver", "My Oberver on create");
	}

	public IncomingSmsObserver(Context context, Handler handler,
			boolean isMatchWithRule, List<Pattern> rules) {
		super(handler);
		this.context = context;
		this.isMatchWithRule = isMatchWithRule;
		this.rules = rules;
		Log.i("Leo-SmsObserver", "My Oberver on create");
	}

	@Override
	public void onChange(boolean selfChange) {
		Cursor cur = null;
		try {
			ContentResolver cr = context.getContentResolver();
			String[] projection = new String[] { "body" };// "_id", "address",
															// "person",,
															// "date",
															// "type
			String where = " date >  "
					+ (System.currentTimeMillis() - 5 * 60 * 1000);
			cur = cr.query(Uri.parse("content://sms/"), projection, where,
					null, "date desc");
			if (null == cur)
				return;
			if (cur.moveToNext()) {
				String content = cur.getString(cur.getColumnIndex("body"));
				String authCode = null;
				if (isTagCreateOwn) {
					authCode = parseAuthCode(content, tags, platformTags);
				} else {
					if (isMatchWithRule) {
						authCode = parseAuthCodeWithRule(content, rules);

					}else{
						authCode = parseAuthCode(content);
					}
				}
				if (!TextUtils.isEmpty(authCode)) {
					Intent intento = new Intent(
							INTENT_FILTER_MESSAGE_AUTH_CODE);
					intento.putExtra(PARAM_INTENT_AUTH_CODE,
							authCode);
					LocalBroadcastManager.getInstance(context).sendBroadcast(
							intento);
				}
			}
		} catch (Exception e) {
			Log.e("SmsObserver", "sms onChange exception ");
		} finally {
			if (cur != null && !cur.isClosed()) {
				cur.close();
			}
		}
		Log.i("SmsObserver", "sms onChange###### ");
	}

	public static String parseAuthCode(String msg) {
		if ((msg.contains("中国外汇交易中心") )
				&& (msg.contains("验证码") || msg.contains("校验码") || msg
						.contains("动态码"))) {
			Matcher m = PATTERN_AUTH_CODE.matcher(msg);
			while (m.find()) {
				return m.group();
			}
		}
		return null;
	}
	public final static Pattern PATTERN_AUTH_CODE = Pattern
			.compile("(?<!\\d)\\d{6}(?!\\d)");
	public final static Pattern PATTERN_AUTH_CODE_FOUR = Pattern
			.compile("(?<!\\d)\\d{4}(?!\\d)");
	public static String parseAuthCode(String msg, String[] tag,
			String[] platformTags) {

		if (platformTags != null && tag != null) {
			for (int i = 0; i < platformTags.length; i++) {
				if (msg.contains(tag[i])) {
					for (int j = 0; j < tag.length; j++) {
						if (msg.contains(tag[j])) {
							Matcher m = PATTERN_AUTH_CODE
									.matcher(msg);
							while (m.find()) {
								return m.group();
							}
							m = PATTERN_AUTH_CODE_FOUR
									.matcher(msg);
							while (m.find()) {
								return m.group();
							}

						}

					}

				}
			}
		}
		return null;
	}

	public static String parseAuthCodeWithRule(String msg, List<Pattern> rules) {

		if (!TextUtils.isEmpty(msg)) {
			if (rules != null) {

				for (int i = 0; i < rules.size(); i++) {
					if (rules.get(i) != null) {

						Matcher m = rules.get(i).matcher(msg);
						while (m.find()) {
							try {
								String s = m.group(1);
								return s;
							} catch (Exception e) {
								// TODO: handle exception
							}
							//return m.group();
						}
					}
				}
			}

		}
		return null;
	}
}
