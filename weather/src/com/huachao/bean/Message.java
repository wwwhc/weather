package com.huachao.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
public class Message {
	@NotNull
	@Size(min=2,max=6)
	private String province;
	@NotNull
	@Size(min=2,max=6)
	private String city;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
