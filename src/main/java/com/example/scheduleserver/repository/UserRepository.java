package com.example.scheduleserver.repository;

import com.example.scheduleserver.entity.User;
import com.example.scheduleserver.exception.ExceptionCode;
import com.example.scheduleserver.exception.ValidException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // id로 user를 찾고, 있으면 User Entity로 반환한다.
    // or Else 만약 없으면 Throw 예외를 던진다.
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new ValidException(ExceptionCode.USER_NOT_FOUND));
    }

    Optional<User> findByEmail(String email);


    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() ->
                new ValidException(ExceptionCode.EMAIL_NOT_FOUND));
    }

}
