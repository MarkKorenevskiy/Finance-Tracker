package korenevskiy.accountservice.controller;

import korenevskiy.accountservice.dto.account.AccountBalanceUpdateRequest;
import korenevskiy.accountservice.dto.account.AccountCreateRequest;
import korenevskiy.accountservice.dto.account.AccountResponseInfo;
import korenevskiy.accountservice.dto.account.AccountUpdateInfoRequest;
import korenevskiy.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@CrossOrigin
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<AccountResponseInfo> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{accountId}")
    public AccountResponseInfo getAccountById(@PathVariable UUID accountId) {
        return accountService.getAccountById(accountId);
    }


    @PostMapping
    public void updateAccountBalance(@RequestBody AccountBalanceUpdateRequest request) {
        accountService.updateAccountBalance(request);
    }

    @PostMapping("/create")
    public boolean createAccount(@RequestBody AccountCreateRequest request) {
        return accountService.createAccount(request);
    }

    @PutMapping("/update")
    public void updateAccount(@RequestBody AccountUpdateInfoRequest request) {
        accountService.updateInfoAccount(request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
    }
}
