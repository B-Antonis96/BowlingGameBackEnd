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
public class UserLoginRequest {
    @NotBlank(message = "Username is mandatory")
    @Size(max = 16, message = "Username cannot exceed 16 characters")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(max = 64, message = "Password cannot exceed 64 characters")
    private String password;
}
