package com.ai.paas.ipaas.uac.service;

import com.ai.paas.ipaas.uac.service.impl.UserClientImpl;

public class UserClientFactory {
    private static IUserClient iUserClient;

    private UserClientFactory() {

    }

    public synchronized static IUserClient getUserClient() {
        if (iUserClient == null) {
            iUserClient = new UserClientImpl();

        }
        return iUserClient;
    }

}
