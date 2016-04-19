package com.ai.paas.ipaas.uac.vo;

import java.io.Serializable;

public class AuthResult implements Serializable {
	private static final long serialVersionUID = -1064949882761359027L;

	public AuthResult() {
	}

	private String userId = null;
	private String pid = null;
	private String userName = null;
	/**
	 * 配置地址，ZK模式，IP:PORT;IP:PORT
	 */
	private String configAddr = null;
	private String configUser = null;
	private String configPasswd = null;


	public String getConfigAddr() {
		return configAddr;
	}

	public void setConfigAddr(String configAddr) {
		this.configAddr = configAddr;
	}

	public String getConfigUser() {
		return configUser;
	}

	public void setConfigUser(String configUser) {
		this.configUser = configUser;
	}

	public String getConfigPasswd() {
		return configPasswd;
	}

	public void setConfigPasswd(String configPasswd) {
		this.configPasswd = configPasswd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}


}
