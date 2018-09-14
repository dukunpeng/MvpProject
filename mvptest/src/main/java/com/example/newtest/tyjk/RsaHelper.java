package com.example.newtest.tyjk;

import android.util.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.Cipher;

public class RsaHelper {

	/**
	 * 生成RSA密钥对(默认密钥长度为1024)
	 * 
	 * @return
	 */
	public static RsaKeys generateRSAKeyPair() throws Exception {
		return new RsaKeys(generateRSAKeyPair(1024));

	}

	/**
	 * 生成RSA密钥对
	 * 
	 * @param keyLength
	 *            密钥长度，范围：512～2048
	 * @return
	 */
	private static KeyPair generateRSAKeyPair(int keyLength) throws Exception {

		KeyPairGenerator kpg = KeyPairGenerator
				.getInstance("RSA/ECB/PKCS1Padding");
		kpg.initialize(keyLength);
		return kpg.genKeyPair();

	}

	/**
	 * 加密数据
	 * 
	 * @param zipdata
	 *            待加密数据
	 * @param pubKey
	 *            公钥
	 * @return 加密的byte[]
	 */
	public static byte[] encryptData(byte[] zipdata, PublicKey pubKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		ArrayList<Byte> albyte = new ArrayList<Byte>();

		int datasize = 117;
		int dataRate = zipdata.length / datasize;
		if (zipdata.length % 117 != 0) {
			dataRate++;
		}
		for (int i = 0; i < dataRate; i++) {
			if (dataRate == i + 1) {
				byte[] tmpBytes = GetBytes(zipdata, datasize * i,
						zipdata.length - 1);
				tmpBytes = cipher.doFinal(tmpBytes);
				for (int j = 0; j < tmpBytes.length; j++) {
					albyte.add(tmpBytes[j]);
				}
			} else {
				byte[] tmpBytes = GetBytes(zipdata, datasize * i, datasize
						* (i + 1) - 1);
				tmpBytes = cipher.doFinal(tmpBytes);
				for (int j = 0; j < tmpBytes.length; j++) {
					albyte.add(tmpBytes[j]);
				}
			}
		}
		int size = albyte.size();
		byte[] result = new byte[size];

		for (int i = 0; i < size; i++) {
			result[i] = albyte.get(i).byteValue();
		}
		return result;
	}

	/**
	 * 加密数据
	 * 
	 * @param str
	 *            待加密字符串
	 * @param pubKey
	 *            公钥
	 * @return 加密的byte[]
	 * @throws Exception
	 */
	public static String encryptData(String str, PublicKey pubKey)
			throws Exception {
		byte[] data = encryptData(str.getBytes("utf-8"), pubKey);
		return Base64.encodeToString(data, Base64.NO_WRAP);
	}

	/**
	 * 加密数据
	 * 
	 * @param str
	 *            待加密字符串
	 * @param pubKeyXML
	 *            公钥
	 * @return 加密的byte[]
	 * @throws Exception
	 */
	public static String encryptData(String str, String pubKeyXML)
			throws Exception {
		byte[] data = encryptData(str.getBytes("utf-8"),
				RsaKeys.decodePublicKeyFromXml(pubKeyXML));
		return Base64Helper.encode(data);
		// return Base64.encodeToString(data, Base64.NO_WRAP);

	}

	/**
	 * 解密数据
	 * 
	 * @param encryptedData
	 *            加密的byte[]
	 * @param priKey
	 *            私钥
	 * @return 原始的byte[]
	 */
	public static byte[] decryptData(byte[] encryptedData, PrivateKey priKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			ArrayList<Byte> albyte = new ArrayList<Byte>();
			for (int i = 0; i < encryptedData.length / 128; i++) {
				byte[] tmpBytes = GetBytes(encryptedData, 128 * i,
						128 * (i + 1) - 1);
				tmpBytes = cipher.doFinal(tmpBytes);
				for (int j = 0; j < tmpBytes.length; j++) {
					albyte.add(tmpBytes[j]);
				}
			}
			int size = albyte.size();
			byte[] result = new byte[size];

			for (int i = 0; i < size; i++) {
				result[i] = albyte.get(i).byteValue();
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	private static byte[] GetBytes(byte[] orgByte, int from, int end) {
		if (end <= from && end != 0)
			return null;

		byte[] tmpByte = new byte[end - from + 1];
		for (int i = 0; i < end - from + 1; i++) {
			if (end > orgByte.length)
				break;
			tmpByte[i] = orgByte[i + from];
		}
		return tmpByte;
	}

	/**
	 * 解密数据
	 * 
	 * @param Base64Str
	 *            加密的字符串
	 * @param priKey
	 *            私钥
	 * @return 原始的字符串
	 */
	public static String decryptData(String Base64Str, PrivateKey priKey)
			throws Exception {
		byte[] encryptedData = Base64.decode(Base64Str, Base64.NO_WRAP);
		byte[] data = decryptData(encryptedData, priKey);
		return new String(data, "utf-8");
	}

	/**
	 * 解密数据
	 * 
	 * @param Base64Str
	 *            加密的字符串
	 * @param priKeyXML
	 *            私钥
	 * @return 原始的字符串
	 */
	public static String decryptData(String Base64Str, String priKeyXML)
			throws Exception {
		byte[] encryptedData = Base64Helper.decode(Base64Str);
		// byte[] encryptedData=Base64.decode(Base64Str, Base64.NO_WRAP);
		byte[] data = decryptData(encryptedData,
				RsaKeys.decodePrivateKeyFromXml(priKeyXML));
		return new String(data, "utf-8");
	}

	/**
	 * 根据指定私钥对数据进行签名(默认签名算法为"SHA1withRSA")
	 * 
	 * @param data
	 *            要签名的数据
	 * @param priKey
	 *            私钥
	 * @return
	 */
	public static byte[] signData(byte[] data, PrivateKey priKey) {
		return signData(data, priKey, "SHA1withRSA");
	}

	/**
	 * 根据指定私钥和算法对数据进行签名
	 * 
	 * @param data
	 *            要签名的数据
	 * @param priKey
	 *            私钥
	 * @param algorithm
	 *            签名算法
	 * @return
	 */
	public static byte[] signData(byte[] data, PrivateKey priKey,
			String algorithm) {
		try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(priKey);
			signature.update(data);
			return signature.sign();
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 用指定的公钥进行签名验证(默认签名算法为"SHA1withRSA")
	 * 
	 * @param data
	 *            数据
	 * @param sign
	 *            签名结果
	 * @param pubKey
	 *            公钥
	 * @return
	 */
	public static boolean verifySign(byte[] data, byte[] sign, PublicKey pubKey) {
		return verifySign(data, sign, pubKey, "SHA1withRSA");
	}

	/**
	 * 
	 * @param data
	 *            数据
	 * @param sign
	 *            签名结果
	 * @param pubKey
	 *            公钥
	 * @param algorithm
	 *            签名算法
	 * @return
	 */
	public static boolean verifySign(byte[] data, byte[] sign,
                                     PublicKey pubKey, String algorithm) {
		try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initVerify(pubKey);
			signature.update(data);
			return signature.verify(sign);
		} catch (Exception ex) {
			return false;
		}
	}

	public static void main55(String[] args) throws Exception {
		KeyPair kp = RsaHelper.generateRSAKeyPair(1024);
		PublicKey pubKey = kp.getPublic();
		PrivateKey priKey = kp.getPrivate();

		String pubKeyXml = RsaKeys.encodePublicKeyToXml(pubKey);
		String priKeyXml = RsaKeys.encodePrivateKeyToXml(priKey);
		System.out.println("====公钥====");
		System.out.println(pubKeyXml);
		System.out.println("====私钥====");
		System.out.println(priKeyXml);

		PublicKey pubKey2 = RsaKeys.decodePublicKeyFromXml(pubKeyXml);
		PrivateKey priKey2 = RsaKeys.decodePrivateKeyFromXml(priKeyXml);

		System.out.println("====公钥对比====");
		System.out.println(pubKey.toString());
		System.out.println("------");
		System.out.println(pubKey2.toString());

		System.out.println("====私钥对比====");
		System.out.println(priKey.toString());
		System.out.println("------");
		System.out.println(priKey2.toString());

		try {
			String pubKeyXml3 = "<RSAKeyValue><Modulus>rHESyuI3ny4MLsqDBalW9ySaodCL0e6Bsrl01Q5G1qm2wjUoGULazZSNqZY+JQNjU92tW3Snk5RPIkv+wDj+uOT9LTUjQImltHnzqMvbt06GipVXDOyBLTa7G/zRIe/CrjyJ+XEYX2xIhpe5ayowl3HHUpZ71jRNioyxaVVZ8S0=</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>";
			String priKeyXml3 = "<RSAKeyValue><Modulus>rHESyuI3ny4MLsqDBalW9ySaodCL0e6Bsrl01Q5G1qm2wjUoGULazZSNqZY+JQNjU92tW3Snk5RPIkv+wDj+uOT9LTUjQImltHnzqMvbt06GipVXDOyBLTa7G/zRIe/CrjyJ+XEYX2xIhpe5ayowl3HHUpZ71jRNioyxaVVZ8S0=</Modulus><Exponent>AQAB</Exponent><P>5a7uM+IeY8QMVQl0q88ZTqWbB555l7+366cUIClTN8z2ZXzTnWFCNoQzUrG14FouJFYumFZD12Ni5MkJK6gqSw==</P><Q>wDMhwwO4kz82uSG+FlCBr06fYk2COTg0TofmSp/5OrVqgkBIe7FgpTpVGzGLk0mvOLcy6UZftq//W0Saow6nZw==</Q><DP>FbjDgliiMyE5YVlxlUYSyKNU1BWivj09caXte1UtL5vMubBiewHVtz4tdGamIr+kmX8lDPcrl1Uo5yY0HdLbnQ==</DP><DQ>kIjjJsgxkWnEOUyKqjU4kSDK8x3ehDEkBLpmEFBlGCU9R14YJAyr5RUM0zpbABQ1VK1P9+UYLUYE/hmFQIHQmQ==</DQ><InverseQ>pxQDThwSnUZ4EaNaCPl1ovYypdQUZaZ/Sld1+0n8FEjkmRcGP1R9VMuj1ViPZg3rvm2GeP8Xv1SJqJUVueWiGA==</InverseQ><D>DxBNoPWEAF7IZ6n/KhZx52MGMw6BuFQKdm9m+lml7Iik03BLUXGapYzNlzvtr9QM8D2UMEIPhX/WLdvPpEEWVzGnD7XpLXjGwfu1ZkJRcXPEZEZ2subh5ZBqOWCFWKv5WwgGYWuYDLHfrBlBgSFWR8cZuyqkmMsWl4CiadXqGA0=</D></RSAKeyValue>";

			System.out.println((new Date()).toLocaleString() + ": 加载公钥中。。。");
			PublicKey pubKey3 = RsaKeys.decodePublicKeyFromXml(pubKeyXml3);
			System.out.println((new Date()).toLocaleString() + ": 加载私钥中。。。");
			PrivateKey priKey3 = RsaKeys.decodePrivateKeyFromXml(priKeyXml3);

			String dataStr = "Java与.NET和平共处万岁！";
			byte[] dataByteArray = dataStr.getBytes("utf-8");
			System.out.println("data的Base64表示："
					+ Base64Helper.encode(dataByteArray));

			System.out.println((new Date()).toLocaleString() + ": 加密中。。。"); // 加密
			byte[] encryptedDataByteArray = RsaHelper.encryptData(
					dataByteArray, pubKey3);

			System.out.println("encryptedData的Base64表示："
					+ Base64Helper.encode(encryptedDataByteArray));
			System.out.println((new Date()).toLocaleString() + ": 解密中。。。"); // 解密
																			// byte[]
			byte[] decryptedDataByteArray = RsaHelper.decryptData(
					encryptedDataByteArray, priKey3);
			System.out.println(new String(decryptedDataByteArray, "utf-8"));// 签名
			System.out.println((new Date()).toLocaleString() + ": 签名中。。。");
			byte[] signDataByteArray = RsaHelper.signData(dataByteArray,
					priKey3);
			System.out.println("signData的Base64表示："
					+ Base64Helper.encode(signDataByteArray)); // 验签
			System.out.println((new Date()).toLocaleString() + ": 验签中。。。");
			boolean isMatch = RsaHelper.verifySign(dataByteArray,
					signDataByteArray, pubKey3);
			System.out.println("验签结果：" + isMatch);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

