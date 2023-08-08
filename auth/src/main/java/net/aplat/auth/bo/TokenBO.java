package net.aplat.auth.bo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TokenBO {
    private String tokenId;
    private String unionId;
    private String userName;
    private String headImageUrl;
    private LocalDateTime expireTime;
    private Integer expirationTime;
    private AccessTokenRecord accessToken;
    private UserInformationRecord userInfo;
}
