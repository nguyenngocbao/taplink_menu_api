package taplink.network.menu.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taplink.network.menu.api.commons.constants.AppConstants;
import taplink.network.menu.api.commons.utils.ObjectMapperUtils;
import taplink.network.menu.api.dtos.request.SignupRequestDto;
import taplink.network.menu.api.dtos.response.UserResponseDto;
import taplink.network.menu.api.exceptions.DuplicateException;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.Role;
import taplink.network.menu.api.models.User;
import taplink.network.menu.api.repositories.RoleRepository;
import taplink.network.menu.api.repositories.UserRepository;
import taplink.network.menu.api.services.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapperUtils objectMapperUtils;

    @Override
    public void signup(SignupRequestDto requestDto) {
        String email = requestDto.email();
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new DuplicateException(String.format("User with the email address '%s' already exists.", email));
        }

//        String hashedPassword = passwordEncoder.encode(requestDto.password());
        Role storeOwnerRole = roleRepository.findByCode(AppConstants.STORE_OWNER_ROLE).orElseThrow(() -> new ResourceNotFoundException("Role owner could not be found"));
        Set<Role> roles = new HashSet<>();
        roles.add(storeOwnerRole);
        User user = User.builder().
                username(requestDto.username())
                .email(requestDto.email())
                .phone(requestDto.phone())
//                .password(hashedPassword)
                .fullName(requestDto.fullName())
                .roles(roles)
                .active(true)
                .isAdmin(false).build();
        userRepository.save(user);
    }

    @Override
    public UserResponseDto getUserProfile(String username) {
        User user = findByUsernameOrEmail(username, username);
        return objectMapperUtils.convertEntityAndDto(user, UserResponseDto.class);
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, username).orElseThrow(() -> new ResourceNotFoundException("User could not be found for username or email: " + username));
    }
}
