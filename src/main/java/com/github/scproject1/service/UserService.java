package com.github.scproject1.service;

import ch.qos.logback.core.util.StringUtil;
import com.github.scproject1.dto.UserLoginDto;
import com.github.scproject1.dto.UserSignUpDto;
import com.github.scproject1.entity.User;
import com.github.scproject1.repository.UserRepository;
import com.github.scproject1.security.JWTToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTToken jwtToken;

    @Transactional(readOnly = true)
    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public void signUp(UserSignUpDto userSignUpDto) {
        String encodedPassword = passwordEncoder.encode(userSignUpDto.getPassword());

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(encodedPassword)
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String login(UserLoginDto userLoginDto) {
        User user = userRepository.findById(userLoginDto.getEmail())
                .orElseThrow(()->new IllegalArgumentException("가입되지 않은 이메일입니다."));

        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtToken.createToken(user.getEmail());
    }

    public void logout(String bearerToken){
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            jwtToken.logout(token);
        }
    }

}
