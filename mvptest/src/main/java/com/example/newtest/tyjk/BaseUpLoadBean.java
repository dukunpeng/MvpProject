package com.example.newtest.tyjk;

public class BaseUpLoadBean extends BaseBean {


	private String version;
	private String platform;
	private boolean isEncrypt;
	private boolean isCompress;
	private String code;
	private String singnStr;
	private String token;
	private String clienTime;
	private String deviceId;
	private String paramStr;
	private String clientDataMD5;
	private String osVersion;

	public String getOsVersion() {
		return this.osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	/**
	 * @return the clientDataMD5
	 */
	public String getClientDataMD5() {
		return clientDataMD5;
	}

	/**
	 * @param clientDataMD5
	 *            the clientDataMD5 to set
	 */
	public void setClientDataMD5(String clientDataMD5) {
		this.clientDataMD5 = clientDataMD5;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public boolean isEncrypt() {
		return isEncrypt;
	}

	public void setEncrypt(boolean isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

	public boolean isCompress() {
		return isCompress;
	}

	public void setCompress(boolean isCompress) {
		this.isCompress = isCompress;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSingnStr() {
		return singnStr;
	}

	public void setSingnStr(String singnStr) {
		this.singnStr = singnStr;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getClienTime() {
		return clienTime;
	}

	public void setClienTime(String clienTime) {
		this.clienTime = clienTime;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getParamStr() {
		return paramStr;
	}

	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}

}
