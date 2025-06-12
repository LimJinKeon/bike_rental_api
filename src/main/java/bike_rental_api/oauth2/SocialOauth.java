package bike_rental_api.oauth2;

public interface SocialOauth {

    String getOauthRedirectURL();
    String requestAccessToken(String code);

    default SocialLoginType type() {
        if (this instanceof GoogleOauth) {
            return SocialLoginType.google;
        } else if (this instanceof NaverOauth) {
            return SocialLoginType.naver;
        } else if (this instanceof KakaoOauth) {
            return SocialLoginType.kakao;
        } else {
            return null;
        }
    }
}