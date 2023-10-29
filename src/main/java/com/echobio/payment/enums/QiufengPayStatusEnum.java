package com.echobio.payment.enums;

import com.echobio.payment.common.exception.BusinessException;

public enum QiufengPayStatusEnum {
    SUCCESS(1, "TRADE_SUCCESS")
    ;


    private int code;
    private String des;

    QiufengPayStatusEnum(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public static QiufengPayStatusEnum getEnumByCode(int code) {
        for (QiufengPayStatusEnum value : QiufengPayStatusEnum.values()) {
            if (code == value.getCode()){
                return value;
            }
        }
        throw new BusinessException("code is invalid for QiufengPayStatusEnum");
    }

    public static QiufengPayStatusEnum getEnumByCode(String des) {
        for (QiufengPayStatusEnum value : QiufengPayStatusEnum.values()) {
            if (des.equals(value.getDes())){
                return value;
            }
        }
        throw new BusinessException("code is invalid for QiufengPayStatusEnum");
    }

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
