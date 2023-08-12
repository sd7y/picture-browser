package net.aplat.auth.respository;

import net.aplat.auth.entity.TokenEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<TokenEntity, String> {
}
