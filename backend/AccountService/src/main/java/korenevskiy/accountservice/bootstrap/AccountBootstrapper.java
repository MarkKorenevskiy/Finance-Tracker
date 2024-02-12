package korenevskiy.accountservice.bootstrap;

import korenevskiy.accountservice.model.Account;
import korenevskiy.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountBootstrapper implements IDevDataBootstrapper {

    private final AccountRepository accountRepository;

    @Override
    public void bootstrap() {

        log.info("Bootstrapping accounts");

        Account chequing = Account.builder()
                .id(UUID.fromString("3c92f93b-a8e4-499f-89d5-9a1297a83982"))
                .name("Chequing")
                .balance(BigDecimal.valueOf(3456.78))
                .build();

        Account savings = Account.builder()
                .id(UUID.fromString("f0b6c09f-ebbb-4efe-8470-5f6bac5c1867"))
                .name("Savings")
                .balance(BigDecimal.valueOf(10000))
                .build();

        accountRepository.save(chequing);
        accountRepository.save(savings);

        log.info("Saved savings and chequing accounts");
    }
}
