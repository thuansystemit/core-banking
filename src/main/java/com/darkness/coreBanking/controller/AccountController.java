package com.darkness.coreBanking.controller;

import com.darkness.coreBanking.dto.AccountDto;
import com.darkness.coreBanking.dto.UserDto;
import com.darkness.coreBanking.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Page<AccountDto> getAllAccounts(@RequestParam(defaultValue = "0") int offset,
                                        @RequestParam(defaultValue = "10") int limit,
                                        @RequestParam(defaultValue = "pk,asc") String[] sort) {
        Sort sortSpec = Sort.by(Sort.Order.by(sort[0]));
        if (sort.length > 1 && sort[1].equalsIgnoreCase("desc")) {
            sortSpec = sortSpec.descending();
        }

        int page = offset / limit;
        Pageable pageable = PageRequest.of(page, limit, sortSpec);
        return accountService.getAllAccounts(pageable);
    }

    @PostMapping("openAccount/{pk}")
    public ResponseEntity<AccountDto> openAccount(@PathVariable Long userPk, @RequestBody AccountDto request) {
        return ResponseEntity.ok(accountService.openAccount(userPk, request));
    }

    @PostMapping("closeAccount/{accountNumber}")
    public ResponseEntity<AccountDto> closeAccount(@PathVariable String accountNumber) {
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.ok(accountService.getAccountByNumber(accountNumber));
    }

    @PostMapping("frozenAccount/{accountNumber}")
    public ResponseEntity<AccountDto> frozenAccount(@PathVariable String accountNumber) {
        accountService.frozenAccount(accountNumber);
        return ResponseEntity.ok(accountService.getAccountByNumber(accountNumber));
    }
}
