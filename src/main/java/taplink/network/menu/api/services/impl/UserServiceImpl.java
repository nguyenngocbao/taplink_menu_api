package taplink.network.menu.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taplink.network.menu.api.dtos.request.SignupRequestDto;
import taplink.network.menu.api.exceptions.DuplicateException;
import taplink.network.menu.api.models.User;
import taplink.network.menu.api.repositories.UserRepository;
import taplink.network.menu.api.services.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(SignupRequestDto requestDto) {
        String email = requestDto.email();
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new DuplicateException(String.format("User with the email address '%s' already exists.", email));
        }

//        String hashedPassword = passwordEncoder.encode(requestDto.password());
        User user = User.builder().
                username(requestDto.username())
                .email(requestDto.email())
//                .password(hashedPassword)
                .fullName(requestDto.fullName())
                .active(true)
                .build();
        userRepository.save(user);
    }
}
