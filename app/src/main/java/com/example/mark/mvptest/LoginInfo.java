package com.example.mark.mvptest;

import java.io.Serializable;

public class LoginInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nice_name;

	private String customer_code;

	private String user_id;

	private String Mobile_Phone;

	private String Push_Tag;

	public String getPush_Tag() {
		return Push_Tag;
	}

	public void setPush_Tag(String push_Tag) {
		Push_Tag = push_Tag;
	}

	public String getMobile_Phone() {
		return Mobile_Phone;
	}

	public void setMobile_Phone(String mobile_Phone) {
		Mobile_Phone = mobile_Phone;
	}

	public String getNice_name() {
		return nice_name;
	}

	public void setNice_name(String nice_name) {
		this.nice_name = nice_name;
	}

	public String getCustomer_code() {
		return customer_code;
	}

	public void setCustomer_code(String customer_code) {
		this.customer_code = customer_code;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "LoginInfo [nice_name=" + nice_name + ", customer_code="
				+ customer_code + ", user_id=" + user_id + ", Mobile_Phone="
				+ Mobile_Phone + "]";
	}

}

