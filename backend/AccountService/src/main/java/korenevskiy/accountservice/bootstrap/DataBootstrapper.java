package korenevskiy.accountservice.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataBootstrapper implements CommandLineRunner {

    private final AccountBootstrapper accountBootstrapper;

    @Override
    public void run(String... args) throws Exception {
    }
}
