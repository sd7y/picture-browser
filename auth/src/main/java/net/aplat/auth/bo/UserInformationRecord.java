package net.aplat.auth.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserInformationRecord(
        String openid,
        String nickname,
        // 1为男性，2为女性
        Integer sex,
        String province,
        String city,
        String country,
        @JsonProperty("headimgurl") String headImgUrl,
        List<String> privilege,
        @JsonProperty("unionid") String unionId
) {

}
