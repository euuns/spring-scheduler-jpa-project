package com.example.scheduleserver.repository;

import com.example.scheduleserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // id로 user를 찾고, 있으면 User Entity로 반환한다.
    // or Else 만약 없으면 Throw 예외를 던진다.
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id: " + id));
    }


    @Query(value = "select user " +
            "from User user " +
            "where user.email = :email")
    Optional<User> findByEmail(@Param("email") String email);


    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist email: " + email));
    }

}
