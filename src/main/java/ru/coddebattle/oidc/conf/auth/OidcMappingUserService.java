package ru.coddebattle.oidc.conf.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

/**
 * @author Igor_Petrov@epam.com
 * Created at 9/11/2018
 */
@RequiredArgsConstructor
public class OidcMappingUserService extends OidcUserService {

    private final UserManager userManager;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        userManager.populateUser(oidcUser);
        return oidcUser;
    }
}
