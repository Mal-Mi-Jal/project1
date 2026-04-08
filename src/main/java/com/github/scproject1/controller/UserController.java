package com.github.scproject1.controller;

import com.github.scproject1.dto.EmailCheckDto;
import com.github.scproject1.dto.LoginCheckDto;
import com.github.scproject1.dto.UserLoginDto;
import com.github.scproject1.dto.UserSignUpDto;
import com.github.scproject1.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/check-email")
    public ResponseEntity<EmailCheckDto> checkEmail(@RequestParam String email){
        boolean isDuplicate = userService.checkEmail(email);

        if(isDuplicate){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new EmailCheckDto(false, "이미 사용중인 이메일입니다."));
        }

        return ResponseEntity.ok(new EmailCheckDto(true, "사용 가능한 이메일입니다."));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserSignUpDto userSignUpDto){
        userService.signUp(userSignUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공!!!");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginCheckDto> login(@RequestBody UserLoginDto userLoginDto){
        String token = userService.login(userLoginDto);
        return ResponseEntity.ok(new LoginCheckDto(token, "로그인 성공!!"));
    }

    @GetMapping("/me")
    public ResponseEntity<String> getMyInfo() {
        // 시큐리티 홀더에서 인증된 유저의 이메일을 꺼내옵니다.
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok("당신의 이메일은: " + email + " 입니다. 인증 성공!");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        userService.logout(token);
        return ResponseEntity.ok("로그아웃 성공! 이제 이 토큰은 사용할 수 없습니다.");
    }
}
