package com.mvucevski.usermanagement.service;

import com.mvucevski.usermanagement.domain.Role;
import com.mvucevski.usermanagement.domain.User;
import com.mvucevski.usermanagement.domain.UserId;
import com.mvucevski.usermanagement.exception.UsernameAlreadyExistsException;
import com.mvucevski.usermanagement.exception.UsernameDoesntExistException;
import com.mvucevski.usermanagement.repository.RolesRepository;
import com.mvucevski.usermanagement.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private Logger logger;

    public UsersService(@Qualifier("dbUsersRepository") UsersRepository repository,
                        RolesRepository rolesRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = repository;
        this.rolesRepository = rolesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        logger = LoggerFactory.getLogger(UsersService.class);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findUserByUsername(username).orElseThrow(()->new UsernameDoesntExistException("User " + username + " Not Found"));
    }

    public User loadUserById(UserId userId){
        return usersRepository.findUserById(userId).orElseThrow(()->new UsernameDoesntExistException("User wiht id " + userId.getId() + " Not Found"));
    }

    public User saveUser(User newUser){
        if(usersRepository.findUserByUsername(newUser.getUsername()).isPresent()){
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }

        if(!validateEmail(newUser.getUsername())){
            throw new UsernameAlreadyExistsException("Please use valid e-mail address.");
        }

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

        logger.info("Saving User with id: " + newUser.getId());
        return usersRepository.saveUser(newUser);

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

        if(!user.isMembershipExpired()){
            throw new UsernameAlreadyExistsException("User with username: '" + username + "' is already an active member!");
        }

        user.setMembershipExpirationDate(LocalDateTime.now().plusYears(1));
        usersRepository.saveUser(user);
        logger.info("Granting membership to User with id: " + user.getId());

        return true;
    }

    public final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
