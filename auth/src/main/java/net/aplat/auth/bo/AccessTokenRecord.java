package net.aplat.auth.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenRecord(
        String accessToken,
        String expiresIn,
        String refreshToken,
        String openid,
        String scope,
        @JsonProperty("unionid") String unionId
        ) { }
