package ru.coddebattle.oidc.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.coddebattle.oidc.domain.UserEntity;

import java.util.Optional;

/**
 * @author Igor_Petrov@epam.com
 * Created at 9/11/2018
 */
public interface UserMongoRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
}
