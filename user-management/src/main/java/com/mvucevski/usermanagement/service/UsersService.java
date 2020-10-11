package com.mvucevski.usermanagement.service;

import com.mvucevski.usermanagement.domain.Role;
import com.mvucevski.usermanagement.domain.User;
import com.mvucevski.usermanagement.domain.UserId;
import com.mvucevski.usermanagement.exception.UsernameAlreadyExistsException;
import com.mvucevski.usermanagement.repository.RolesRepository;
import com.mvucevski.usermanagement.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersService(@Qualifier("dbUsersRepository") UsersRepository repository,
                        RolesRepository rolesRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = repository;
        this.rolesRepository = rolesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findUserByUsername(username).orElseThrow(()->new UsernameNotFoundException("User " + username + " Not Found"));
    }

    public User loadUserById(UserId userId){
        return usersRepository.findUserById(userId).orElseThrow(()->new UsernameNotFoundException("User wiht id " + userId.getId() + " Not Found"));
    }

    public User saveUser(User newUser){
        try{

            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setConfirmPassword("");

            Iterable<Role> roles = rolesRepository.findAllByNameEquals("USER");

            if(roles.iterator().hasNext()){
                Role role = roles.iterator().next();
                newUser.setRoles(Stream.of(role).collect(Collectors.toSet()));
            }else{
                Role role = new Role();
                role.setName("USER");
                newUser.setRoles(Stream.of(role).collect(Collectors.toSet()));
            }

            return usersRepository.saveUser(newUser);

        }catch(Exception ex){
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }
    }

    public User createUser(String username, String password, String fullName){
        User user = new User(username, fullName, password);
        return saveUser(user);
    }

    public User getUser(String username){
        return loadUserByUsername(username);
    }

    public boolean grantMembership(String username){
        User user = loadUserByUsername(username);

        if(user == null){
            throw new UsernameAlreadyExistsException("The user with username: '" + username + "' doesn't exist!");
        }

        if(!user.isMemebershipExpired()){
            throw new UsernameAlreadyExistsException("User with username: '" + username + "' is already an active member!");
        }

        user.setMembershipExpirationDate(LocalDateTime.now().plusYears(1));
        usersRepository.saveUser(user);

        return true;
    }
}
