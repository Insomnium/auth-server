package ru.coddebattle.oidc.conf;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.coddebattle.oidc.repo.ClientDetailsMongoRepository;
import ru.coddebattle.oidc.repo.UserMongoRepository;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.stream.Collectors;


/**
 * @author Igor_Petrov@epam.com
 * Created at 9/11/2018
 */
@Component
@Profile("initdb")
@RequiredArgsConstructor
@Slf4j
public class SampleAuthInitializer {

    private final ClientDetailsMongoRepository clientRepo;
    private final UserMongoRepository userRepo;
    private final AuthServerProperties authServerProperties;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void reInitSampleClients() {
        initSampleClients();
        initSampleUsers();
    }

    private void initSampleClients() {
        log.warn("Re-initializing clients db");

        clientRepo.deleteAll();
        clientRepo.saveAll(authServerProperties.getSampleClients().stream()
                .map(client -> client.setClientSecret(passwordEncoder.encode(client.getClientSecret())))
                .collect(Collectors.toList()));
    }

    private void initSampleUsers() {
        log.warn("Re-initializing users db");

        userRepo.deleteAll();
        userRepo.saveAll(authServerProperties.getSampleUsers().stream()
                .map(user -> user.setPassword(passwordEncoder.encode(user.getPassword())))
                .map(user -> user.setActiveUntil(Date.from(ZonedDateTime.now().plusMonths(1).toInstant())))
                .collect(Collectors.toList()));
    }
}
