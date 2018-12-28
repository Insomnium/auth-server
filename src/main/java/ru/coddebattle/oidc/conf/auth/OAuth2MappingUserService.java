package ru.coddebattle.oidc.conf.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * @author Ins_
 * Created at 9/11/2018
 */
@RequiredArgsConstructor
public class OAuth2MappingUserService extends DefaultOAuth2UserService {

    private final UserManager userManager;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        userManager.populateUser(oAuth2User);
        return oAuth2User;
    }
}
