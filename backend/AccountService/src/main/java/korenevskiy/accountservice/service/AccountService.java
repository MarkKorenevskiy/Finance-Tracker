package korenevskiy.accountservice.service;

import korenevskiy.accountservice.dto.account.AccountBalanceUpdateRequest;
import korenevskiy.accountservice.dto.account.AccountCreateRequest;
import korenevskiy.accountservice.dto.account.AccountResponseInfo;
import korenevskiy.accountservice.dto.account.AccountUpdateInfoRequest;
import korenevskiy.accountservice.dto.expenseService.ExpenseServiceAccountAddRequest;
import korenevskiy.accountservice.dto.expenseService.ExpenseServiceAccountUpdateRequest;
import korenevskiy.accountservice.model.Account;
import korenevskiy.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final RestClient restClient;

    private final String expenseServiceUrl = "http://localhost:8180";

    public List<AccountResponseInfo> getAllAccounts() {
        log.info("Getting all accounts");
        return accountRepository.findAll().stream()
                .map(this::mapToAccountResponseInfo)
                .toList();
    }

    public AccountResponseInfo getAccountById(UUID accountId) {
        log.info("Getting account with ID: {}", accountId);

        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return null;
        }

        return mapToAccountResponseInfo(account);
    }

    public void updateAccountBalance(AccountBalanceUpdateRequest request) {

        log.info("Updating account balance with ID: {}. Delta: {}",
                request.getAccountId(), request.getBalanceDelta());

        Account account = accountRepository.findById(request.getAccountId()).orElse(null);

        // TODO: change to Exception for circuit breaker pattern
        if (account == null) {
            log.error("Invalid account ID: {}", request.getAccountId());
            return;
        }

        account.setBalance(account.getBalance().add(request.getBalanceDelta()));
        log.info("New balance for account - {} is {}", account.getId(), account.getBalance());
        accountRepository.save(account);
    }

    public boolean createAccount(AccountCreateRequest request) {
        log.info("Creating new account with Name: {}, Init balance: {}",
                request.getName(), request.getBalance());

        // check that there is no account with the same name
        if (accountRepository.existsAccountByName(request.getName())) {
            log.warn("Account with provided name - {} already exist", request.getName());
            return false;
        }

        Account account = Account.builder()
                .name(request.getName())
                .balance(request.getBalance())
                .build();

        log.info("Saving new account.");
        accountRepository.save(account);

        log.info("Sending request to ExpenseService.");
        // Sending request to ExpenseService to add new account to its ID-NAME table
        restClient.post()
                .uri(expenseServiceUrl + "/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(ExpenseServiceAccountAddRequest.builder()
                        .id(account.getId())
                        .name(account.getName())
                        .build())
                .retrieve()
                .toBodilessEntity();

        log.info("New account successfully created.");
        return true;
    }

    public void updateInfoAccount(AccountUpdateInfoRequest request) {

        log.info("Updating account with ID: {}", request.getId());

        var account = accountRepository.findById(request.getId()).orElse(null);

        if (account == null) {
            log.warn("No account with provided ID-{}", request.getId());
            return;
        }

        // check whether user has changed the name of account. If yes, then we should send a request
        // to ExpenseService to update its accounts ID-NAME table
        if (!Objects.equals(account.getName(), request.getName())) {

            log.info("Sending request to ExpenseService to update ID-NAME table");
            restClient.put()
                    .uri(expenseServiceUrl + "/api/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ExpenseServiceAccountUpdateRequest.builder()
                            .id(account.getId())
                            .name(request.getName())
                            .build())
                    .retrieve()
                    .toBodilessEntity();

            // Updating account's name
            account.setName(request.getName());
        }

        // Name has remained the same, so change only balance
        account.setBalance(request.getBalance());
        accountRepository.save(account);
        log.info("Account with ID-{} has been updated", account.getId());
    }

    public void deleteAccount(UUID id) {

        log.info("Deleting account with ID-{}", id);

        // Sending request to ExpenseService to delete this account from its
        // ID-NAME table
        log.info("Sending request to ExpenseService");
        restClient.delete()
                .uri(expenseServiceUrl + "/api/accounts/" + id)
                .retrieve()
                .toBodilessEntity();
        accountRepository.deleteById(id);
        log.info("Account with ID-{} has been deleted", id);
    }

    private AccountResponseInfo mapToAccountResponseInfo(Account account) {
        return AccountResponseInfo.builder()
                .id(account.getId())
                .name(account.getName())
                .balance(account.getBalance())
                .build();
    }
}
