package com.example.newtest.tyjk;

import android.util.Base64;

import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	public static void Test() throws Exception {
		String key = GenKey();
		String testdata = "测试数据";

		String sss = encrypt(testdata, key);
		String snew = decrypt(sss, key);

	}

	/**
	 * 随机生成Key
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String GenKey() throws Exception {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
	}

	/**
	 * 对称加密
	 * 
	 * @param data
	 *            待加密数据(可序列化)
	 * @param key
	 *            随机生成的KEY 16位
	 * @return Base64位字符串
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		try {
			byte[] ivbytes = key.getBytes("utf-8");
			byte[] dataBytes = data.getBytes("utf-8");
			SecretKeySpec keyspec = new SecretKeySpec(ivbytes, "AES");
			IvParameterSpec ivspec = new IvParameterSpec(ivbytes);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7P5dding");
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(dataBytes);
			return Base64.encodeToString(encrypted, Base64.NO_WRAP);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 对称解密
	 * 
	 * @param EncryptStr
	 *            加密后的Base64字符串
	 * @param key
	 *            随机生成的KEY 16位
	 * @return 原始字符串
	 * @throws Exception
	 */
	public static String decrypt(String EncryptStr, String key)
			throws Exception {
		try {
			byte[] ivbytes = key.getBytes("utf-8");
			byte[] encrypted1 = Base64.decode(EncryptStr, Base64.NO_WRAP);

			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes("utf-8"),
					"AES");
			IvParameterSpec ivspec = new IvParameterSpec(ivbytes);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted1);
			return new String(original, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String Key = "";

	public AES() throws Exception {
		this.Key = GenKey();
		InitCipher();
	}

	public AES(String _key) throws Exception {
		this.Key = _key;
		InitCipher();
	}

	/**
	 * 对称加密
	 * 
	 * @param data
	 *            待加密数据(可序列化)
	 * @return Base64位字符串
	 * @throws Exception
	 */
	public String encrypt(String data) throws Exception {
		try {
			byte[] encrypted = cipher_encrypt.doFinal(data.getBytes("utf-8"));
			return Base64.encodeToString(encrypted, Base64.NO_WRAP);
			// return Base64.encodeToString(encrypted, Base64.NO_WRAP);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Cipher cipher_decrypt = null;
	private Cipher cipher_encrypt = null;

	private void InitCipher() throws Exception {
		byte[] ivbytes = this.Key.getBytes("utf-8");
		SecretKeySpec keyspec = new SecretKeySpec(ivbytes, "AES");
		IvParameterSpec ivspec = new IvParameterSpec(ivbytes);
		cipher_decrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher_decrypt.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
		cipher_encrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher_encrypt.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
	}

	/**
	 * 对称解密
	 * 
	 * @param EncryptStr
	 *            加密后的Base64字符串
	 * @return 原始字符串
	 * @throws Exception
	 */
	public String decrypt(String EncryptStr) throws Exception {
		try {
			byte[] original = cipher_decrypt.doFinal(Base64.decode(EncryptStr,
					Base64.NO_WRAP));
			return new String(original, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

