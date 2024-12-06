package sudoku.Authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sudoku.Authentication.dtos.LoginRequestDTO;
import sudoku.Authentication.dtos.RegisterRequestDTO;
import sudoku.User.entities.User;
import sudoku.User.repositories.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(LoginRequestDTO requestDTO) {
        requestDTO.setUsername((requestDTO.getUsername() == null) ? userRepo.findByEmail(requestDTO.getEmail()).getUsername() : requestDTO.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(requestDTO.getUsername(), requestDTO.getPassword());

            return "Access Token : " + token;
        } else {
            throw new UsernameNotFoundException("Invalid username-email or password");
        }
    }

    public String register(RegisterRequestDTO requestDTO) {
        User user = new User(null, requestDTO.getUsername(), requestDTO.getEmail(), passwordEncoder.encode(requestDTO.getPassword()));
        userRepo.save(user);
        String token = jwtService.generateToken(requestDTO.getUsername(), requestDTO.getPassword());

        return "Access Token : " + token;
    }

    public String guest(String username) {
        User user = new User(null, username, null, null);
        userRepo.save(user);
        String token = jwtService.generateToken(username, null);

        return "Access Token : " + token;
    }
}
