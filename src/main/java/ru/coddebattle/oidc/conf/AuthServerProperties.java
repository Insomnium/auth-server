package ru.coddebattle.oidc.conf;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.coddebattle.oidc.domain.ClientDetailsEntity;
import ru.coddebattle.oidc.domain.UserEntity;

import java.util.List;

/**
 * @author Igor_Petrov@epam.com
 * Created at 9/11/2018
 */
@ConfigurationProperties("auth")
@Data
public class AuthServerProperties {
    private Integer accountValidityDays;
    private List<String> defaultRoles;
    private List<ClientDetailsEntity> sampleClients;
    private List<UserEntity> sampleUsers;
}
