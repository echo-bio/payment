package com.echobio.payment.enums;

public enum CryptoTypeEnum {
    MD5("MD5")
    ;


    private String name;

    CryptoTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
