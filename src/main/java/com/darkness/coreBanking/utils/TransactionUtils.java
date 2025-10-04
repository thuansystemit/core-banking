package com.darkness.coreBanking.utils;

import java.util.UUID;

public class TransactionUtils {
    public static String generateTransactionReference() {
        return "TXN-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
