package net.aplat.auth.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenRecord {
        private String accessToken;
        private String expiresIn;
        private String refreshToken;
        private String openid;
        private String scope;
        @JsonProperty("unionid")
        private String unionId;

        public String getAccessToken() {
                return accessToken;
        }

        public void setAccessToken(String accessToken) {
                this.accessToken = accessToken;
        }

        public String getExpiresIn() {
                return expiresIn;
        }

        public void setExpiresIn(String expiresIn) {
                this.expiresIn = expiresIn;
        }

        public String getRefreshToken() {
                return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
                this.refreshToken = refreshToken;
        }

        public String getOpenid() {
                return openid;
        }

        public void setOpenid(String openid) {
                this.openid = openid;
        }

        public String getScope() {
                return scope;
        }

        public void setScope(String scope) {
                this.scope = scope;
        }

        public String getUnionId() {
                return unionId;
        }

        public void setUnionId(String unionId) {
                this.unionId = unionId;
        }
}
