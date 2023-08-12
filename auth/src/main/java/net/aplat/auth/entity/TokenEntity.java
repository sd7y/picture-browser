package net.aplat.auth.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("token")
@Data
public class TokenEntity {
    @Id
    private String id;
    private String unionId;
    private String userName;
    private String headImageUrl;
    private LocalDateTime expireTime;
    private Integer expirationTime;
}
