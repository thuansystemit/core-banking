package com.darkness.coreBanking.exception;

/**
 * @author darkness
 **/
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String msg) {
        super(msg);
    }
}
