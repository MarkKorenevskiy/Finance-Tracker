package korenevskiy.expenseservice.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataBootstrapper implements CommandLineRunner {

    private final ExpenseBootstrapper expenseBootstrapper;
    private final CategoryBootstrapper categoryBootstrapper;
    private final AccountBootstrapper accountBootstrapper;

    @Override
    public void run(String... args) throws Exception {
        log.info("Data bootstrapper running.");

        categoryBootstrapper.bootstrap();
//        accountBootstrapper.bootstrap();
        expenseBootstrapper.bootstrap();

        log.info("Bootstrapping done.");
    }
}
