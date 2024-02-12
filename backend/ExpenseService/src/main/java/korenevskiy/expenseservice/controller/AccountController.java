package korenevskiy.expenseservice.controller;

import korenevskiy.expenseservice.dto.account.AccountRequest;
import korenevskiy.expenseservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public void addNewAccount(@RequestBody AccountRequest request) {
        accountService.addNewAccount(request);
    }

    @PutMapping
    public void updateAccount(@RequestBody AccountRequest request) {
        accountService.updateAccount(request);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
    }

}
