package com.github.scproject1.service;

import com.github.scproject1.dto.UserLoginDto;
import com.github.scproject1.dto.UserSignUpDto;
import com.github.scproject1.entity.User;
import com.github.scproject1.repository.UserRepository;
import com.github.scproject1.security.JWTToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTToken jwtToken;

    // 이메일 체크
    @Transactional(readOnly = true)
    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // 회원가입 로직
    @Transactional
    public void signUp(UserSignUpDto userSignUpDto) {

        if (userRepository.existsByEmail(userSignUpDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(userSignUpDto.getPassword());

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(encodedPassword)
                .build();

        userRepository.save(user);
    }

    // 로그인 로직
    @Transactional(readOnly = true)
    public String login(UserLoginDto userLoginDto) {
        User user = userRepository.findById(userLoginDto.getEmail())
                .orElseThrow(()->new IllegalArgumentException("가입되지 않은 이메일입니다."));

        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtToken.createToken(user.getEmail());
    }

    
    // 로그아웃 로직
    public void logout(String bearerToken){
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            jwtToken.logout(token);
        }
    }

}
