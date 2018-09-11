package ru.coddebattle.oidc.conf.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.coddebattle.oidc.conf.AuthServerProperties;
import ru.coddebattle.oidc.domain.UserEntity;
import ru.coddebattle.oidc.repo.UserMongoRepository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @author Igor_Petrov@epam.com
 * Created at 9/11/2018
 */
@Service
@RequiredArgsConstructor
public final class UserManager {

    private final UserMongoRepository userMongoRepository;
    private final AuthServerProperties authServerProperties;

    UserEntity populateUser(OAuth2User oauth2User) {
        Map<String, Object> userAttributes = oauth2User.getAttributes();
        String email = extract(userAttributes, "email");

        Optional<UserEntity> user = userMongoRepository.findByEmail(email);

        return user.orElseGet(() -> {
            UserEntity newUser = UserEntity.builder()
                    .email(email)
                    .name(extract(userAttributes, "name"))
                    .login(email) // FIXME: really?
                    .locked(false)
                    .activeUntil(makeValidityDate())
                    .roles(authServerProperties.getDefaultRoles())
                    .build();
            return userMongoRepository.save(newUser);
        });
    }

    private Date makeValidityDate() {
        return Date.from(ZonedDateTime.now().plusDays(authServerProperties.getAccountValidityDays()).toInstant());
    }

    @SuppressWarnings("unchecked")
    private <T> T extract(Map<String, Object> userAttributes, String attrKey) {
        return (T) userAttributes.get(attrKey);
    }
}
