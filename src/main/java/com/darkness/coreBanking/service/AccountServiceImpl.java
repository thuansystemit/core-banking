package com.darkness.coreBanking.service;

import com.darkness.coreBanking.domain.Account;
import com.darkness.coreBanking.domain.AccountCurrency;
import com.darkness.coreBanking.domain.AccountStatus;
import com.darkness.coreBanking.domain.User;
import com.darkness.coreBanking.dto.AccountDto;
import com.darkness.coreBanking.exception.AccountNotFoundException;
import com.darkness.coreBanking.exception.UserNotFoundException;
import com.darkness.coreBanking.repository.AccountRepository;
import com.darkness.coreBanking.repository.UserRepository;
import com.darkness.coreBanking.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author darkness
 **/
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(final AccountRepository accountRepository,
                    final UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AccountDto openAccount(Long userPk, AccountDto request) {
        User user = userRepository.findByPk(userPk)
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        "Could not found user by pk=[%s]", userPk)));

        Account account = new Account();
        LocalDateTime createdAt = LocalDateTime.now();
        account.setUser(user);
        account.setCurrency(AccountCurrency.VND);
        account.setAccountNumber(Utils.generateAccountNumber());
        account.setCurrency(AccountCurrency.valueOf(request.getCurrency()));
        account.setStatus(AccountStatus.ACTIVE);
        account.setCreatedAt(createdAt);
        account.setUpdatedAt(createdAt);
        Account saved = accountRepository.save(account);
        return convertToDto(saved);
    }

    @Override
    public AccountDto getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format("Could not found account by account number = [%s]",
                                accountNumber)));
        return convertToDto(account);
    }

    @Override
    public Page<AccountDto> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).map(this::convertToDto);
    }

    @Override
    public void updateAccountStatus(String accountNumber, String newStatus) {
        Account account = accountRepository.findByAccountNumber(newStatus)
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format("Could not found account by account number = [%s]",
                                accountNumber)));
        AccountStatus currentAccountStatus = account.getStatus();

        if (newStatus.equals(currentAccountStatus.name())) {
            throw new IllegalStateException(String.format("Account is already in status = [%s]: ", newStatus));
        }
        account.setStatus(AccountStatus.valueOf(newStatus));
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(String accountNumber) {
        updateAccountStatus(accountNumber, AccountStatus.CLOSED.name());
    }

    @Override
    public void frozenAccount(String accountNumber) {
        updateAccountStatus(accountNumber, AccountStatus.FROZEN.name());
    }

    public AccountDto convertToDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setPk(account.getPk());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setBalance(account.getBalance());
        accountDto.setCurrency(account.getCurrency().name());
        accountDto.setStatus(account.getStatus().name());
        accountDto.setVersion(account.getVersion());
        return accountDto;
    }
}
