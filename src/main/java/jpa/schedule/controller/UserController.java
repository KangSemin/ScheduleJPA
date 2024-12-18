package jpa.schedule.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jpa.schedule.domain.User;
import jpa.schedule.dto.LoginRequestDto;
import jpa.schedule.dto.UserRequestDto;
import jpa.schedule.dto.UserResponseDto;
import jpa.schedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody UserRequestDto requestDto, HttpServletResponse response) {
        Long userId = userService.createUser(requestDto);
        setSessionCookie(userId, response);
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto, HttpSession session) {
        User user = userService.login(requestDto);
        session.setAttribute("userId", user.getUserId());
        return ResponseEntity.ok("로그인 성공");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> getUser( @CookieValue(required = false) Long userId) {
        if (userId == null ) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        UserResponseDto user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, 
                                         @RequestBody UserRequestDto requestDto,
                                         @CookieValue(required = false) Long userId,
                                         HttpServletResponse response) {
        if (userId == null || !userId.equals(id)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        userService.updateUser(id, requestDto);
        Cookie cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id,
                                           @CookieValue(required = false) Long userId,
                                           HttpSession session,
                                           HttpServletResponse response) {
        if (userId == null || !userId.equals(id)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        
        userService.deleteUser(id);
        
        // 세션 무효화
        session.invalidate();
        
        // 쿠키 삭제
        Cookie cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        
        return ResponseEntity.ok().build();
    }

    private void setSessionCookie(Long userId, HttpServletResponse response) {
        Cookie cookie = new Cookie("userId", userId.toString());
        cookie.setMaxAge(300);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}