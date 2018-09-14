package com.example.newtest.tyjk;


public class TYBaseBean extends BaseBean {



	private boolean isEncrypt;
	private boolean isCompress;
	private String singnStr;
	private int doStatu;// 0失败 1成功 2失败
	private String errCode;
	private String prompt;
	private String doObject;
	private String ServerTime;
	
	

	public String getServerTime() {
		return ServerTime;
	}

	public void setServerTime(String serverTime) {
		ServerTime = serverTime;
	}

	public boolean isEncrypt() {
		return isEncrypt;
	}

	public void setEncrypt(boolean isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

	public int getDoStatu() {
		return doStatu;
	}

	public void setDoStatu(int doStatu) {
		this.doStatu = doStatu;
	}

	public boolean isCompress() {
		return isCompress;
	}

	public void setCompress(boolean isCompress) {
		this.isCompress = isCompress;
	}

	public String getSingnStr() {
		return singnStr;
	}

	public void setSingnStr(String singnStr) {
		this.singnStr = singnStr;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getDoObject() {
		return doObject;
	}

	public void setDoObject(String doObject) {
		this.doObject = doObject;
	}

}
