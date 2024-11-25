package com.happy.transapi.requests.user;

public class CreateUserRequest {
	private String name;
	private String mobile;
	private String email;
	private String password;
	private String loginBy;
	private String loginRawData;

	public String getName(){
		return name;
	}

	public String getMobile(){
		return mobile;
	}

	public String getEmail(){
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginBy() {
		return loginBy;
	}

	public void setLoginBy(String loginBy) {
		this.loginBy = loginBy;
	}

	public String getLoginRawData() {
		return loginRawData;
	}

	public void setLoginRawData(String loginRawData) {
		this.loginRawData = loginRawData;
	}
}
