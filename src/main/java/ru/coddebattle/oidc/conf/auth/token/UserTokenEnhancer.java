package ru.coddebattle.oidc.conf.auth.token;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import ru.coddebattle.oidc.domain.UserEntity;
import ru.coddebattle.oidc.repo.UserMongoRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor_Petrov@epam.com
 * Created at 1/22/2019
 */
@RequiredArgsConstructor
public class UserTokenEnhancer implements TokenEnhancer {

    private final UserMongoRepository userMongoRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Object principal = authentication.getUserAuthentication().getPrincipal();
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(extractAdditionalInfo(principal));
        return accessToken;
    }

    private Map<String, Object> extractAdditionalInfo(Object principal) {
        String email = null;
        if (principal instanceof OAuth2User) {
            email = (String) ((OAuth2User) principal).getAttributes().get("email");
        } else if (principal instanceof UserEntity) {
            email = ((UserEntity) principal).getEmail();
        }

        UserEntity userEntity = userMongoRepository.findByEmail(email.toLowerCase())
                .orElseThrow(IllegalArgumentException::new);

        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("email", userEntity.getEmail());
        additionalInfo.put("login", userEntity.getLogin());
        additionalInfo.put("fullName", userEntity.getName());
        return additionalInfo;
    }
}
