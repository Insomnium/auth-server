package ru.coddebattle.oidc.repo;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.coddebattle.oidc.domain.ClientDetailsEntity;

import java.util.Optional;


/**
 * @author Ins_
 * Created at 9/11/2018
 */
public interface ClientDetailsMongoRepository extends MongoRepository<ClientDetailsEntity, String> {
    Optional<ClientDetailsEntity> findByClientId(String clientId);
}
