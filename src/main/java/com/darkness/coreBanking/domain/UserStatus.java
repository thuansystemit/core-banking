package com.darkness.coreBanking.domain;

/**
 * @author darkness
 **/
public enum UserStatus {
    PENDING_KYC("PENDING_KYC"),
    ACTIVE("ACTIVE"),
    SUSPENDED("SUSPENDED"),
    CLOSED("CLOSED"),
    LOCKED("LOCKED");

    private final String code;

    UserStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static UserStatus fromCode(String code) {
        for (UserStatus status : values()) {
            if (status.getCode().equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
