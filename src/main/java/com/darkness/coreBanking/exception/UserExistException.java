package com.darkness.coreBanking.exception;

public class UserExistException extends RuntimeException {
    public UserExistException(String msg) {
        super(msg);
    }
}
