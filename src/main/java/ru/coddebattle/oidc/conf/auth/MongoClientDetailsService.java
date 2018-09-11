package ru.coddebattle.oidc.conf.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import ru.coddebattle.oidc.domain.ClientDetailsEntity;
import ru.coddebattle.oidc.repo.ClientDetailsMongoRepository;

/**
 * @author Igor_Petrov@epam.com
 * Created at 9/11/2018
 */
@Service
@RequiredArgsConstructor
public class MongoClientDetailsService implements ClientDetailsService {

    private final ClientDetailsMongoRepository clientDetailsMongoRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clientDetailsMongoRepository.findByClientId(clientId)
                .map(this::toClientDetails)
                .orElseThrow(() ->new NoSuchClientException("No client with requested id: " + clientId));
    }

    private ClientDetails toClientDetails(ClientDetailsEntity clientDetailsEntity) {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(clientDetailsEntity.getClientId());
        clientDetails.setClientSecret(clientDetailsEntity.getClientSecret());
        clientDetails.setAuthorizedGrantTypes(clientDetailsEntity.getAuthorizedGrantTypes());
        clientDetails.setScope(clientDetailsEntity.getScope());
        clientDetails.setAutoApproveScopes(clientDetailsEntity.getAutoApproveScopes());
        clientDetails.setRegisteredRedirectUri(clientDetailsEntity.getRegisteredRedirectUris());
        clientDetails.setAccessTokenValiditySeconds(clientDetailsEntity.getAccessTokenValiditySeconds());
        clientDetails.setRefreshTokenValiditySeconds(clientDetailsEntity.getRefreshTokenValiditySeconds());
        return clientDetails;
    }
}
