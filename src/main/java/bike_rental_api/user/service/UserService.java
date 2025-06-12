package bike_rental_api.user.service;

import bike_rental_api.user.Role;
import bike_rental_api.user.User;
import bike_rental_api.user.dto.UserSignUpDto;
import bike_rental_api.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {

        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                        .email(userSignUpDto.getEmail())
                        .password(userSignUpDto.getPassword())
                        .nickname(userSignUpDto.getNickname())
                        .role(Role.USER)
                        .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }
}