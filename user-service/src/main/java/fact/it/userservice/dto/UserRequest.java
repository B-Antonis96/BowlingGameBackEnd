package fact.it.userservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "Username is mandatory")
    @Size(max = 16, message = "Username cannot exceed 16 characters")
    private String username;

    @NotBlank(message = "Token is mandatory")
    private String token;

    @Min(value = 0, message = "High score must be non-negative")
    private int highScore;
}
