package com.darkness.coreBanking.domain;

/**
 * @author darkness
 **/
public enum AccountStatus {
    ACTIVE("ACTIVE"),
    FROZEN("FROZEN"),
    CLOSED("CLOSED");

    private final String code;

    AccountStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static AccountStatus fromCode(String code) {
        for (AccountStatus status : values()) {
            if (status.getCode().equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}