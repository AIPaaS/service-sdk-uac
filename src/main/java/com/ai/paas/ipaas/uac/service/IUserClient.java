package com.ai.paas.ipaas.uac.service;

import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.uac.vo.AuthResult;

public interface IUserClient {
	/**
	 * 校验用户名、密码(弃用)
	 * @param ad
	 * @return true 合法  false 不合法
	 * @throws Exception 
	 */
	@Deprecated
	public AuthResult authUser(AuthDescriptor ad);
	/**
	 * 校验用户名、密码
	 * @param ad 添加sdk校验参数
	 * @return true 合法  false 不合法
	 * @throws Exception 
	 */
	public AuthResult auth(AuthDescriptor ap);
}
