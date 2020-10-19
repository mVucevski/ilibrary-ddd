package com.mvucevski.usermanagement.domain.repository;

import com.mvucevski.usermanagement.domain.model.User;
import com.mvucevski.usermanagement.domain.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUsersRepository extends JpaRepository<User, UserId> {

    Optional<User> findByUsername(String username);
}
