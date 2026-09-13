package authservice.Repository;

import authservice.Entities.RefreshToken;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface RefreshTokenRepo extends CrudRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token);
}
