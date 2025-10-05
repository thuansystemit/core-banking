package com.darkness.coreBanking.exception;

/**
 * @author darkness
 **/
public class UserExistException extends RuntimeException {
    public UserExistException(String msg) {
        super(msg);
    }
}
