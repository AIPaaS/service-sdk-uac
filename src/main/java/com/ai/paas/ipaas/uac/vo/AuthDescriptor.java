package com.ai.paas.ipaas.uac.vo;

import java.io.Serializable;

public class AuthDescriptor implements Serializable{
	private static final long serialVersionUID = -431793174759343174L;
	private String pid = null;
	private String userName = null;
	private String servicePwd = null;
	private String serviceId  = null;
	private String authAdress =null; //用户认证地址，rest地址，我们通过这个地址去请求我们的web，然后调用dubbo服务做认证。
	public AuthDescriptor() {

	}

	public AuthDescriptor(String authAdress,String pid, String servicePwd, String serviceId)  {
		this.authAdress=authAdress;
		if(pid != null && pid.indexOf("@") > -1){
			this.userName = pid;
		}
		this.setPid(pid);
		this.servicePwd = servicePwd;
		this.serviceId = serviceId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return servicePwd;
	}

	public void setPassword(String password) {
		this.servicePwd = password;
	}

	public String getAuthAdress() {
		return authAdress;
	}

	public void setAuthAdress(String authAdress) {
		this.authAdress = authAdress;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
}