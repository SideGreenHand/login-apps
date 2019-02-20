package com.example.ssoserver.utils;

import java.util.UUID;

public class IdGenerator {

    /**
     * 生成UUID
     * @return
     */
    public static String generatorId() {
        return UUID.randomUUID().toString();
    }

}
