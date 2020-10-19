package com.mvucevski.usermanagement.domain.model;

import com.mvucevski.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="roles")
@Getter
public class Role extends AbstractEntity<RoleId> {

    private String name;

    @Version
    private Long version;

    public Role(String name) {
        super(RoleId.randomId(RoleId.class));
        this.name = name;
    }

    public Role() {
    }

    public void setName(String name) {
        this.name = name;
    }
}
