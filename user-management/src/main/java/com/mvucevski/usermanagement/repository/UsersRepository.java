package com.mvucevski.usermanagement.repository;

import com.mvucevski.usermanagement.domain.User;
import com.mvucevski.usermanagement.domain.UserId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserById(UserId id);

    User saveUser(User user);
}
