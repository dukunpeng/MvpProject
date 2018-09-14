package com.example.newtest.common;

import android.text.TextUtils;

import java.io.Serializable;

public class APPClientParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*** 金额 ***/
	private String Amount;
	/*** 银行卡号 ***/
	private String Bank_Account;
	/*** 是否语音 ***/
	private String IsVoice;
	/*** 登录名 ***/
	private String Login_Name;
	/*** 登录密码 ***/
	private String Login_Pwd;
	/******/
	private String StrID;
	/*** 验证码 ***/
	private String Identifying_Code;
	/********/
	private String Bank_Code;

	/********/
	private String IntID;
	/********/
	private String PageIndex;

	/********/
	private String Memo;

	/********/
	private String orderdetail_id;

	/********/
	private String order_id;
	/********/
	private String Password;
	/********/
	private String Rate;
	/********/
	private String Mobile_Phone;

	private String ChannelId;
	private String SmsType;

	private String Real_Name;
	private String Cust_Idcard;
	private String province_code;
	private String city_code;
	private String subbranch;
	private String device_name;
	private String Str1;
	private String Str2;
	private String Str3;

	public String getStr1() {
		return this.Str1;
	}

	public APPClientParam setStr1(String str1) {
		this.Str1 = str1;
		return this;

	}

	public String getStr2() {
		return this.Str2;
	}

	public APPClientParam setStr2(String str2) {
		this.Str2 = str2;
		return this;
	}

	public String getStr3() {
		return this.Str3;
	}

	public APPClientParam setStr3(String str3) {
		this.Str3 = str3;
		return this;
	}

	public String getDevice_name() {
		return this.device_name;
	}

	public APPClientParam setDevice_name(String device_name) {
		this.device_name = device_name;
		return this;
	}

	public String getProvince_code() {
		return this.province_code;
	}

	public APPClientParam setProvince_code(String province_code) {
		this.province_code = province_code;
		return this;
	}

	public String getCity_code() {
		return this.city_code;
	}

	public APPClientParam setCity_code(String city_code) {
		this.city_code = city_code;
		return this;
	}

	public String getSubbranch() {
		return this.subbranch;
	}

	public APPClientParam setSubbranch(String subbranch) {
		this.subbranch = subbranch;
		return this;
	}

	public String getCust_Idcard() {
		return this.Cust_Idcard;
	}

	public APPClientParam setCust_Idcard(String cust_Idcard) {
		this.Cust_Idcard = cust_Idcard;
		return this;
	}

	public String getReal_Name() {
		return this.Real_Name;
	}

	public APPClientParam setReal_Name(String real_Name) {
		this.Real_Name = real_Name;
		return this;
	}

	public String getSmsType() {
		return SmsType;
	}

	public APPClientParam setSmsType(String smsType) {
		SmsType = smsType;
		return this;
	}

	public String getChannelId() {
		return ChannelId;
	}

	public APPClientParam setChannelId(String channelId) {
		ChannelId = channelId;
		return this;
	}

	public String getMobile_Phone() {
		return Mobile_Phone;
	}

	public APPClientParam setMobile_Phone(String mobile_Phone) {
		Mobile_Phone = mobile_Phone;
		return this;
	}

	public String getRate() {
		return Rate;
	}

	public APPClientParam setRate(String rate) {
		Rate = rate;
		return this;
	}

	public String getPassword() {
		return Password;
	}

	public APPClientParam setPassword(String password) {
		Password = password;
		return this;
	}

	public String getPageIndex() {
		return PageIndex;
	}

	public APPClientParam setPageIndex(String pageIndex) {
		PageIndex = pageIndex;
		return this;
	}

	public String getOrderdetail_id() {
		return orderdetail_id;
	}

	public APPClientParam setOrderdetail_id(String orderdetail_id) {
		this.orderdetail_id = orderdetail_id;
		return this;
	}

	public String getOrder_id() {
		return order_id;
	}

	public APPClientParam setOrder_id(String order_id) {
		this.order_id = order_id;
		return this;
	}

	public String getBank_Code() {
		return Bank_Code;
	}

	public APPClientParam setBank_Code(String bank_Code) {
		Bank_Code = bank_Code;
		return this;
	}

	public String getIntID() {
		return IntID;
	}

	public APPClientParam setIntID(String intID) {
		if (TextUtils.isEmpty(intID)) {
			IntID = "0";
		} else {
			IntID = intID;
		}
		return this;
	}

	public String getMemo() {
		return Memo;
	}

	public APPClientParam setMemo(String memo) {
		Memo = memo;
		return this;
	}

	public String getLogin_Name() {
		return Login_Name;
	}

	public APPClientParam setLogin_Name(String login_Name) {
		Login_Name = login_Name;
		return this;
	}

	public String getLogin_Pwd() {
		return Login_Pwd;
	}

	public APPClientParam setLogin_Pwd(String login_Pwd) {
		Login_Pwd = login_Pwd;
		return this;
	}

	public String getStrID() {
		return StrID;
	}

	public APPClientParam setStrID(String strID) {
		StrID = strID;
		return this;
	}

	public String getIdentifying_Code() {
		return Identifying_Code;
	}

	public APPClientParam setIdentifying_Code(String identifying_Code) {
		Identifying_Code = identifying_Code;
		return this;
	}

	public String getAmount() {
		return Amount;
	}

	public APPClientParam setAmount(String amount) {
		Amount = amount;
		return this;
	}

	public String getBank_Account() {
		return Bank_Account;
	}

	public APPClientParam setBank_Account(String bank_Account) {
		Bank_Account = bank_Account;
		return this;
	}

	public String getIsVoice() {
		return IsVoice;
	}

	public APPClientParam setIsVoice(String isVoice) {
		IsVoice = isVoice;
		return this;
	}

}

