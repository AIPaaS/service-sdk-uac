package com.ai.paas.ipaas.uac.service.impl;

import com.ai.paas.ipaas.PaaSConstant;
import com.ai.paas.ipaas.uac.UserClientException;
import com.ai.paas.ipaas.uac.constants.UserSDKConstants;
import com.ai.paas.ipaas.uac.service.IUserClient;
import com.ai.paas.ipaas.uac.util.HttpRequestUtil;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.uac.vo.AuthResult;
import com.ai.paas.ipaas.util.CiperUtil;
import com.ai.paas.ipaas.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class UserClientImpl implements IUserClient {

	@Override
	/**
	 * 用户认证 用户名、密码认证，服务号认证
	 * jianhua.ma ---2015-04-28
	 */
	public AuthResult authUser(AuthDescriptor ad) {
		String username, password, authAdress, serviceId = null;
		if (ad == null) {
			throw new UserClientException(
					PaaSConstant.ExceptionCode.USER_AUTH_ERROR,
					"AuthDescriptor is null");
		} else {
			username = ad.getUserName();
			password = CiperUtil.encrypt(UserSDKConstants.SECURITY_KEY,
					ad.getPassword());
			authAdress = ad.getAuthAdress();
			serviceId = ad.getServiceId();
		}
		if (StringUtil.isBlank(username) || StringUtil.isBlank(password)
				|| StringUtil.isBlank(authAdress)
				|| StringUtil.isBlank(serviceId)) {
			throw new UserClientException(
					PaaSConstant.ExceptionCode.USER_AUTH_ERROR,
					"username or password or authAdress or serviceId is blank");
		}
		username = username.trim();
		password = password.trim();
		authAdress = authAdress.trim();
		serviceId = serviceId.trim();
		String param = "password=" + password + "&authUserName=" + username
				+ "&serviceId=" + serviceId;
		String postRes = HttpRequestUtil.sendPost(authAdress, param);

		Gson gson = new Gson();
		JsonObject postResJson = gson.fromJson(postRes, JsonObject.class);

		boolean successed = postResJson.get("successed").getAsBoolean();
		AuthResult ar = new AuthResult();
		if (successed) {
			ar.setUserId(postResJson.get("userId").getAsString());
			ar.setUserName(postResJson.get("userName").getAsString());
			ar.setConfigPasswd(postResJson.get("configPasswd").getAsString());
			ar.setConfigAddr(postResJson.get("configAddr").getAsString());
			ar.setConfigUser(postResJson.get("configUser").getAsString());
			if (null != postResJson.get("pid"))
				ar.setPid(postResJson.get("pid").getAsString());
		} else {
			throw new UserClientException(
					PaaSConstant.ExceptionCode.USER_AUTH_ERROR,
					"username or password is wrong!");
		}
		return ar;
	}

	@Override
	public AuthResult auth(AuthDescriptor ap) {
		String serviceId, pid, password, authAdress = null;
		if (ap == null) {
			throw new UserClientException(
					PaaSConstant.ExceptionCode.USER_AUTH_ERROR,
					"AuthDescriptor is null");
		} else {
			password = CiperUtil.encrypt(UserSDKConstants.SECURITY_KEY,
					ap.getPassword());
			pid = ap.getPid();
			authAdress = ap.getAuthAdress();
			serviceId = ap.getServiceId();

		}
		if (StringUtil.isBlank(password) || StringUtil.isBlank(pid)
				|| StringUtil.isBlank(authAdress)
				|| StringUtil.isBlank(serviceId)) {
			throw new UserClientException(
					PaaSConstant.ExceptionCode.USER_AUTH_ERROR,
					"password or authAdress or serviceId or pId is blank");
		}
		String param = "password=" + password + "&pId=" + pid + "&serviceId="
				+ serviceId;
		String postRes = HttpRequestUtil.sendPost(authAdress, param);

		Gson gson = new Gson();
		JsonObject postResJson = gson.fromJson(postRes, JsonObject.class);

		boolean successed = postResJson.get("successed").getAsBoolean();
		AuthResult ar = new AuthResult();
		if (successed) {
			ar.setUserId(postResJson.get("userId").getAsString());
			ar.setUserName(postResJson.get("userName").getAsString());
			ar.setConfigPasswd(postResJson.get("configPasswd").getAsString());
			ar.setConfigAddr(postResJson.get("configAddr").getAsString());
			ar.setConfigUser(postResJson.get("configUser").getAsString());
		} else {
			throw new UserClientException(
					PaaSConstant.ExceptionCode.USER_AUTH_ERROR,
					"username or password is wrong!");
		}
		return ar;
	}

}
