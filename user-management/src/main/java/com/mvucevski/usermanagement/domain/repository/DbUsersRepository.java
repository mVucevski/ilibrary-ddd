package com.mvucevski.usermanagement.domain.repository;

import com.mvucevski.usermanagement.domain.model.User;
import com.mvucevski.usermanagement.domain.model.UserId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DbUsersRepository implements UsersRepository{

    private final JpaUsersRepository repository;

    public DbUsersRepository(JpaUsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<User> findUserById(UserId id) {
        return repository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }
}
