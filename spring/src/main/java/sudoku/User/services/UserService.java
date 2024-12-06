package sudoku.User.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sudoku.User.dtos.UserDTO;
import sudoku.User.entities.User;
import sudoku.User.mappers.UserMapper;
import sudoku.User.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserMapper userMapper;

    public User updateUser(UserDTO newUserDTO, User user) {
        User updatedUser = userMapper.DTOToEntity(newUserDTO, user);

        userRepo.save(updatedUser);

        return updatedUser;
    }

    public String deleteUser(User user) {
        userRepo.delete(user);

        return "User \"" + user.getUsername() + "\" has been deleted";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepo.findByUsername(username);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
        }
    }

}
