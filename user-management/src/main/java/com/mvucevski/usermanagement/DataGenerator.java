package com.mvucevski.usermanagement;

import com.mvucevski.usermanagement.domain.Role;
import com.mvucevski.usermanagement.domain.User;
import com.mvucevski.usermanagement.repository.RolesRepository;
import com.mvucevski.usermanagement.repository.UsersRepository;
import com.mvucevski.usermanagement.service.UsersService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
public class DataGenerator {

    private final UsersService service;
    private final RolesRepository rolesRepository;

    public DataGenerator(UsersService service, RolesRepository rolesRepository) {
        this.service = service;
        this.rolesRepository = rolesRepository;
    }

    @PostConstruct
    @Transactional
    public void generateData(){
        if(rolesRepository.findAll().isEmpty()){
            rolesRepository.save(new Role("EMPLOYEE"));
            rolesRepository.save(new Role("USER"));
        }

    }
}
