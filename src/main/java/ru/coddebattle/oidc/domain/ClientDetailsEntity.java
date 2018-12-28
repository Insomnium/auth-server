package ru.coddebattle.oidc.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

import static ru.coddebattle.oidc.domain.ClientDetailsEntity.MONGO_COLLECTION;

/**
 * @author Ins_
 * Created at 9/11/2018
 */
@Document(collection = MONGO_COLLECTION)
@Data
@Accessors(chain = true)
public class ClientDetailsEntity {
    public static final String MONGO_COLLECTION = "clients";

    @Id
    private String id;

    @Indexed(unique = true)
    private String clientId;

    private String clientSecret;
    private Set<String> scope;
    private Set<String> authorizedGrantTypes;
    private Set<String> registeredRedirectUris;
    private Set<String> autoApproveScopes;
    private Set<String> resourceIds;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
}
