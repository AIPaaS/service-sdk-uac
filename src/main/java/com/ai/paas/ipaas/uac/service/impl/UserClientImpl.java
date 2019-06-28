package com.ai.paas.ipaas.uac.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.ai.paas.Constant;
import com.ai.paas.ipaas.uac.UserClientException;
import com.ai.paas.ipaas.uac.constants.UserSDKConstants;
import com.ai.paas.ipaas.uac.service.IUserClient;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.uac.vo.AuthResult;
import com.ai.paas.serialize.TypeGetter;
import com.ai.paas.util.CiperUtil;
import com.ai.paas.util.HttpUtil;
import com.ai.paas.util.JsonUtil;
import com.ai.paas.util.StringUtil;

public class UserClientImpl implements IUserClient {

    @Override
    /**
     * 用户认证 用户名、密码认证，服务号认证 jianhua.ma ---2015-04-28
     */
    public AuthResult authUser(AuthDescriptor ad) {
        String username;
        String password;
        String authAdress;
        String serviceId = null;
        if (ad == null) {
            throw new UserClientException(Constant.ExceptionCode.USER_AUTH_ERROR, "AuthDescriptor is null");
        } else {
            username = ad.getUserName();
            password = CiperUtil.encrypt(UserSDKConstants.SECURITY_KEY, ad.getPassword());
            authAdress = ad.getAuthAdress();
            serviceId = ad.getServiceId();
        }
        if (StringUtil.isBlank(username) || StringUtil.isBlank(password) || StringUtil.isBlank(authAdress)
                || StringUtil.isBlank(serviceId)) {
            throw new UserClientException(Constant.ExceptionCode.USER_AUTH_ERROR,
                    "username or password or authAdress or serviceId is blank");
        }
        username = username.trim();
        password = password.trim();
        authAdress = authAdress.trim();
        serviceId = serviceId.trim();
        Map<String, String> param = new HashMap<>();
        param.put("password", password);
        param.put("authUserName", username);
        param.put("serviceId", serviceId);
        String postRes = HttpUtil.doPost(authAdress, param);

        Map<String, String> postResJson = JsonUtil.fromJson(postRes, new TypeGetter<Map<String, String>>() {
        });

        boolean successed = Boolean.parseBoolean(postResJson.get("successed"));
        AuthResult ar = new AuthResult();
        if (successed) {
            ar.setUserId(postResJson.get("userId"));
            ar.setUserName(postResJson.get("userName"));
            ar.setConfigPasswd(postResJson.get("configPasswd"));
            ar.setConfigAddr(postResJson.get("configAddr"));
            ar.setConfigUser(postResJson.get("configUser"));
            if (null != postResJson.get("pid"))
                ar.setPid(postResJson.get("pid"));
        } else {
            throw new UserClientException(Constant.ExceptionCode.USER_AUTH_ERROR, "username or password is wrong!");
        }
        return ar;
    }

    @Override
    public AuthResult auth(AuthDescriptor ap) {
        String serviceId;
        String pid;
        String password;
        String authAdress = null;
        if (ap == null) {
            throw new UserClientException(Constant.ExceptionCode.USER_AUTH_ERROR, "AuthDescriptor is null");
        } else {
            password = CiperUtil.encrypt(UserSDKConstants.SECURITY_KEY, ap.getPassword());
            pid = ap.getPid();
            authAdress = ap.getAuthAdress();
            serviceId = ap.getServiceId();

        }
        if (StringUtil.isBlank(password) || StringUtil.isBlank(pid) || StringUtil.isBlank(authAdress)
                || StringUtil.isBlank(serviceId)) {
            throw new UserClientException(Constant.ExceptionCode.USER_AUTH_ERROR,
                    "password or authAdress or serviceId or pId is blank");
        }
        Map<String, String> param = new HashMap<>();
        param.put("password", password);
        param.put("pId", pid);
        param.put("serviceId", serviceId);
        String postRes = HttpUtil.doPost(authAdress, param);

        Map<String, String> postResJson = JsonUtil.fromJson(postRes, new TypeGetter<Map<String, String>>() {
        });

        boolean successed = Boolean.parseBoolean(postResJson.get("successed"));
        AuthResult ar = new AuthResult();
        if (successed) {
            ar.setUserId(postResJson.get("userId"));
            ar.setUserName(postResJson.get("userName"));
            ar.setConfigPasswd(postResJson.get("configPasswd"));
            ar.setConfigAddr(postResJson.get("configAddr"));
            ar.setConfigUser(postResJson.get("configUser"));
        } else {
            throw new UserClientException(Constant.ExceptionCode.USER_AUTH_ERROR, "username or password is wrong!");
        }
        return ar;
    }

}
