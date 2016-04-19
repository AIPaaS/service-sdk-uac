package com.ai.paas.ipaas.uac.service;

import com.ai.paas.ipaas.uac.service.impl.UserClientImpl;

public class UserClientFactory {
	private static volatile IUserClient iUserClient;

	private UserClientFactory() {

	}

	public static IUserClient getUserClient() {
		if (iUserClient == null) {
			synchronized (UserClientFactory.class) {
				if (null == iUserClient)
					iUserClient = new UserClientImpl();
			}

		}
		return iUserClient;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		IUserClient iUserClient = UserClientFactory.getUserClient();
	}
}
