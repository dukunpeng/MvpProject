package com.example.newtest.tyjk;


import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RsaKeys {
	public RsaKeys(KeyPair kp) {
		PublicKeyObject = kp.getPublic();
		PrivateKeyObject = kp.getPrivate();
		PublicKeyXml = encodePublicKeyToXml(PublicKeyObject);
		PrivateKeyXml = encodePrivateKeyToXml(PrivateKeyObject);
	}

	public String PublicKeyXml = "";
	public String PrivateKeyXml = "";
	public PublicKey PublicKeyObject;
	public PrivateKey PrivateKeyObject;

	/*
	 * java端公钥转换成C#公钥
	 */
	public static String encodePublicKeyToXml(PublicKey key) {
		if (!RSAPublicKey.class.isInstance(key)) {
			return null;
		}
		RSAPublicKey pubKey = (RSAPublicKey) key;
		StringBuilder sb = new StringBuilder();

		sb.append("<RSAKeyValue>");
		sb.append("<Modulus>")
				.append(Base64Helper.encode(pubKey.getModulus().toByteArray()))
				.append("</Modulus>");
		sb.append("<Exponent>")
				.append(Base64Helper.encode(pubKey.getPublicExponent()
						.toByteArray())).append("</Exponent>");
		sb.append("</RSAKeyValue>");
		return sb.toString();
	}

	/*
	 * C#端公钥转换成java公钥
	 */
	public static PublicKey decodePublicKeyFromXml(String xml) {
		xml = xml.replaceAll("\r", "").replaceAll("\n", "");
		BigInteger modulus = new BigInteger(1, Base64Helper.decode(StringUtils
				.getMiddleString(xml, "<Modulus>", "</Modulus>")));
		BigInteger publicExponent = new BigInteger(1,
				Base64Helper.decode(StringUtils.getMiddleString(xml,
						"<Exponent>", "</Exponent>")));

		RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus,
				publicExponent);

		KeyFactory keyf;
		try {
			keyf = KeyFactory.getInstance("RSA");
			return keyf.generatePublic(rsaPubKey);
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * C#端私钥转换成java私钥
	 */
	public static PrivateKey decodePrivateKeyFromXml(String xml) {
		xml = xml.replaceAll("\r", "").replaceAll("\n", "");
		BigInteger modulus = new BigInteger(1, Base64Helper.decode(StringUtils
				.getMiddleString(xml, "<Modulus>", "</Modulus>")));
		BigInteger publicExponent = new BigInteger(1,
				Base64Helper.decode(StringUtils.getMiddleString(xml,
						"<Exponent>", "</Exponent>")));
		BigInteger privateExponent = new BigInteger(1,
				Base64Helper.decode(StringUtils.getMiddleString(xml, "<D>",
						"</D>")));
		BigInteger primeP = new BigInteger(1, Base64Helper.decode(StringUtils
				.getMiddleString(xml, "<P>", "</P>")));
		BigInteger primeQ = new BigInteger(1, Base64Helper.decode(StringUtils
				.getMiddleString(xml, "<Q>", "</Q>")));
		BigInteger primeExponentP = new BigInteger(1,
				Base64Helper.decode(StringUtils.getMiddleString(xml, "<DP>",
						"</DP>")));
		BigInteger primeExponentQ = new BigInteger(1,
				Base64Helper.decode(StringUtils.getMiddleString(xml, "<DQ>",
						"</DQ>")));
		BigInteger crtCoefficient = new BigInteger(1,
				Base64Helper.decode(StringUtils.getMiddleString(xml,
						"<InverseQ>", "</InverseQ>")));

		RSAPrivateCrtKeySpec rsaPriKey = new RSAPrivateCrtKeySpec(modulus,
				publicExponent, privateExponent, primeP, primeQ,
				primeExponentP, primeExponentQ, crtCoefficient);

		KeyFactory keyf;
		try {
			keyf = KeyFactory.getInstance("RSA");
			return keyf.generatePrivate(rsaPriKey);
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * java端私钥转换成C#私钥
	 */
	public static String encodePrivateKeyToXml(PrivateKey key) {
		if (!RSAPrivateCrtKey.class.isInstance(key)) {
			return null;
		}
		RSAPrivateCrtKey priKey = (RSAPrivateCrtKey) key;
		StringBuilder sb = new StringBuilder();

		sb.append("<RSAKeyValue>");
		sb.append("<Modulus>")
				.append(Base64Helper.encode(priKey.getModulus().toByteArray()))
				.append("</Modulus>");
		sb.append("<Exponent>")
				.append(Base64Helper.encode(priKey.getPublicExponent()
						.toByteArray())).append("</Exponent>");
		sb.append("<P>")
				.append(Base64Helper.encode(priKey.getPrimeP().toByteArray()))
				.append("</P>");
		sb.append("<Q>")
				.append(Base64Helper.encode(priKey.getPrimeQ().toByteArray()))
				.append("</Q>");
		sb.append("<DP>")
				.append(Base64Helper.encode(priKey.getPrimeExponentP()
						.toByteArray())).append("</DP>");
		sb.append("<DQ>")
				.append(Base64Helper.encode(priKey.getPrimeExponentQ()
						.toByteArray())).append("</DQ>");
		sb.append("<InverseQ>")
				.append(Base64Helper.encode(priKey.getCrtCoefficient()
						.toByteArray())).append("</InverseQ>");
		sb.append("<D>")
				.append(Base64Helper.encode(priKey.getPrivateExponent()
						.toByteArray())).append("</D>");
		sb.append("</RSAKeyValue>");
		return sb.toString();
	}
}
