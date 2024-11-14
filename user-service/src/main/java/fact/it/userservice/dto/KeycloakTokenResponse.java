package fact.it.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KeycloakTokenResponse {
    private String accessToken;
    private String refreshToken;
}

