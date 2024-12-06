package sudoku.User.mappers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import sudoku.User.dtos.UserDTO;
import sudoku.User.entities.User;

@Component
public class UserMapper {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User DTOToEntity(UserDTO newUserDTO, User user) {
        User newUser = new User(user.getId(), newUserDTO.getUsername(), newUserDTO.getEmail(),
                passwordEncoder.encode(newUserDTO.getPassword()));

        return newUser;
    }
}
