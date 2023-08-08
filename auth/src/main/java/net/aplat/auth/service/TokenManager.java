package net.aplat.auth.service;

import net.aplat.auth.bo.AccessTokenRecord;
import net.aplat.auth.bo.TokenBO;
import org.springframework.stereotype.Component;

@Component
public interface TokenManager {
    TokenBO getToken(String tokenId);

    TokenBO newToken(AccessTokenRecord accessToken);

    boolean validate(String tokenId);
}
