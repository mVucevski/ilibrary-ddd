package com.mvucevski.usermanagement.repository;

import com.mvucevski.usermanagement.domain.User;
import com.mvucevski.usermanagement.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUsersRepository extends JpaRepository<User, UserId> {

    Optional<User> findByUsername(String username);
}
