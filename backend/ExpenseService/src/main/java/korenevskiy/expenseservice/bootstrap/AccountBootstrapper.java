package korenevskiy.expenseservice.bootstrap;

import korenevskiy.expenseservice.model.Account;
import korenevskiy.expenseservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountBootstrapper implements IDevDataBootstrapper {

    private final AccountRepository accountRepository;

    @Override
    public void bootstrap() {
        log.info("Bootstrapping accounts");

        Account chequing = Account.builder()
                .id(UUID.fromString("3c92f93b-a8e4-499f-89d5-9a1297a83982"))
                .name("Chequing")
                .build();

        Account savings = Account.builder()
                .id(UUID.fromString("f0b6c09f-ebbb-4efe-8470-5f6bac5c1867"))
                .name("Savings")
                .build();

        accountRepository.save(chequing);
        accountRepository.save(savings);

        log.info("Account bootstrapping done.");
    }
}
