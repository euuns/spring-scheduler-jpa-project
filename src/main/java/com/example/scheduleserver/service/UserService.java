package com.example.scheduleserver.service;

import com.example.scheduleserver.config.PasswordEncoder;
import com.example.scheduleserver.dto.user.UserResponseDto;
import com.example.scheduleserver.entity.User;
import com.example.scheduleserver.exception.ExceptionCode;
import com.example.scheduleserver.exception.ValidException;
import com.example.scheduleserver.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidateSessionService validateSessionService;


    // 회원 가입
    public UserResponseDto signup(String name, String email, String password) {
        // 비밀번호를 암호화
        String encoderPassword = passwordEncoder.encoder(password);

        // 정보를 담은 User 생성
        User getUser = new User(name, email, encoderPassword);

        // SpringDataJPA를 이용해 SimpleJpaRepository 사용 -> save() 저장
        // savedUser는 DB에 저장된 user entity
        // getUser는 DB에 값이 저장되지 않아 id, createdDate, modifiedDate -> null
        User savedUser = userRepository.save(getUser);

        return new UserResponseDto(savedUser.getName(), savedUser.getEmail(),
                savedUser.getCreatedDate(), savedUser.getModifiedDate());
    }


    // 로그인
    public UserResponseDto login(String email, String password, HttpServletRequest httpServletRequest) {

        // Unique인 email을 이용해서 정보 조회
        User findUser = userRepository.findByEmailOrElseThrow(email);

        // 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new ValidException(ExceptionCode.PASSWORD_NOT_MATCH);
        }

        // 유저 정보를 찾았고, 비밀번호가 일치한다면 로그인 성공 -> 세션 응답
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("login", findUser);

        return new UserResponseDto(findUser.getName(), findUser.getEmail(), findUser.getCreatedDate(), findUser.getModifiedDate());
    }


    // 정보 조회
    public UserResponseDto findById(Long id, HttpSession session) {
        // 요청한 id와 등록된 세션의 id가 일치하는지 검사 -> 아닐 경우 예외 처리
        User login = (User) session.getAttribute("login");
        validateSessionService.validateSessionUser(login.getId(), id);

        // 로그인한 유저일 경우 id를 이용해 정보 조회
        User findUser = userRepository.findByIdOrElseThrow(id);
        return new UserResponseDto(findUser.getName(), findUser.getEmail(), findUser.getCreatedDate(), findUser.getModifiedDate());
    }


    // 개인 정보 수정
    @Transactional
    public UserResponseDto updateUserInfo(Long id, String name, String password, User user) {
        validateSessionService.validateSessionUser(user.getId(), id);
        User findUser = userRepository.findByIdOrElseThrow(id);

        String encoderPassword = passwordEncoder.encoder(password);

        // 만약 name, password 중 변경하지 않는 내용이 있으면 이전과 동일하게 유지
        String updateName = findUser.getName();
        String updatePassword = findUser.getPassword();
        if (name != null) {
            updateName = name;
        }
        if (password != null) {
            updatePassword = encoderPassword;
        }

        // 변경된 정보 저장
        findUser.updateInfo(updateName, updatePassword);
        return new UserResponseDto(findUser.getName(), findUser.getEmail(), findUser.getCreatedDate(), findUser.getModifiedDate());
    }


    // 회원 탈퇴
    public void delete(Long id, HttpSession session) {
        // 알맞는 유저가 요청을 했으면 session 삭제
        User login = (User) session.getAttribute("login");
        validateSessionService.validateSessionUser(login.getId(), id);
        session.invalidate();

        // 유저 정보를 찾아 데이터 삭제
        User deleteUser = userRepository.findByIdOrElseThrow(id);
        userRepository.delete(deleteUser);
    }

}
