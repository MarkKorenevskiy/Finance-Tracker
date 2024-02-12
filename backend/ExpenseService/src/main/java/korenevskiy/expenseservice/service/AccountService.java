package korenevskiy.expenseservice.service;

import korenevskiy.expenseservice.dto.account.AccountRequest;
import korenevskiy.expenseservice.model.Account;
import korenevskiy.expenseservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    public void addNewAccount(AccountRequest request) {

        log.info("Creating new account ID-NAME pair. ID: {}, NAME: {}",
                request.getId(), request.getName());

        Account account = Account.builder()
                .id(request.getId())
                .name(request.getName())
                .build();

        accountRepository.save(account);
    }

    public void updateAccount(AccountRequest request) {

        log.info("Updating account with ID: {}", request.getId());

        Account account = accountRepository.findById(request.getId()).orElse(null);

        if (account == null) {
            log.error("No account with such ID: {}", request.getId());
            return;
        }

        account.setName(request.getName());
        accountRepository.save(account);
        log.info("Updated account with ID: {}", account.getId());
    }

    public void deleteAccount(UUID id) {

        log.info("Deleting account with ID: {}", id);
        accountRepository.deleteById(id);
        log.info("Deleted.");
    }

}
