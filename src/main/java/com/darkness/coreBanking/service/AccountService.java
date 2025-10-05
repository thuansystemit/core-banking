package com.darkness.coreBanking.service;

import com.darkness.coreBanking.domain.AccountStatus;
import com.darkness.coreBanking.dto.AccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    AccountDto openAccount(Long userId, AccountDto request);

    AccountDto getAccountByNumber(String accountNumber);

    Page<AccountDto> getAllAccounts(Pageable pageable);

    void updateAccountStatus(String accountNumber, String accountStatus);

    void deleteAccount(String accountNumber);

//    FROZEN
    void frozenAccount(String accountNumber);
}
