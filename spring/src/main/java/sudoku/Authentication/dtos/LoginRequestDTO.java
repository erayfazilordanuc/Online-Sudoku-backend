package sudoku.Authentication.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import sudoku.Common.validation.login.ValidLoginRequestDTO;

@Getter
@Setter
@ValidLoginRequestDTO(username = "username", email = "email")
public class LoginRequestDTO {

    private String username;

    private String email;

    @NotEmpty
    private String password;
}
