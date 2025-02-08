package com.example.scheduleserver.controller;

import com.example.scheduleserver.dto.LoginRequestDto;
import com.example.scheduleserver.dto.SignupRequestDto;
import com.example.scheduleserver.dto.UpdateUserInfoRequestDto;
import com.example.scheduleserver.dto.UserResponseDto;
import com.example.scheduleserver.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // 회원가입
    @PostMapping
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignupRequestDto requestDto){
        UserResponseDto user = userService.signup(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    // 세션을 받아오기 위한 로그인
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpServletRequest httpServletRequest){
        UserResponseDto login = userService.login(requestDto.getEmail(), requestDto.getPassword(), httpServletRequest);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }


    // 로그인이 성공하면 세션이 저장되어 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id, HttpServletRequest httpServletRequest){
        UserResponseDto user = userService.findById(id, httpServletRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    // 개인 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserInfo(@PathVariable Long id, @RequestBody UpdateUserInfoRequestDto requestDto, HttpServletRequest httpServletRequest){
        UserResponseDto user = userService.updateUserInfo(id, requestDto.getName(), requestDto.getPassword(), httpServletRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpServletRequest httpServletRequest){
        userService.delete(id, httpServletRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
