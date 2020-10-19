package com.mvucevski.usermanagement.domain.repository;

import com.mvucevski.usermanagement.domain.model.User;
import com.mvucevski.usermanagement.domain.model.UserId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserById(UserId id);

    User saveUser(User user);
}
