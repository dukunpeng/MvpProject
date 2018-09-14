package com.example.newtest.tyjk;


import com.example.newtest.log.XLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtil {
	// 压缩
	public static String compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return Base64Helper.encode(out.toByteArray());
		// return Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP);
	}

	// 解压缩
	public static String uncompress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(
				Base64Helper.decode(str));
		// ByteArrayInputStream in = new ByteArrayInputStream(Base64.decode(str,
		// Base64.NO_WRAP));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
		XLog.e("解压以后的数据==", "" + out.toString());
		return out.toString();
	}

	// 测试方法
	public static void test() throws IOException {
		String temp = "oasdsfjajf 阿拉丁神灯见啦  。。sad.as,[[][[";
		System.out.println("原字符串=" + temp);
		System.out.println("原长=" + temp.length());
		String temp1 = GZipUtil.compress(temp);
		System.out.println("压缩后的字符串=" + temp1);
		System.out.println("压缩后的长=" + temp1.length());
		System.out.println("解压后的字符串=" + GZipUtil.uncompress(temp1));
	}

}

