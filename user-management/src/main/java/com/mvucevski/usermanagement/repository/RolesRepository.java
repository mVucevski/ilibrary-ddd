package com.mvucevski.usermanagement.repository;

import com.mvucevski.usermanagement.domain.Role;
import com.mvucevski.usermanagement.domain.RoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Role, RoleId> {
    List<Role> findAllByNameEquals(String roleName);
}
