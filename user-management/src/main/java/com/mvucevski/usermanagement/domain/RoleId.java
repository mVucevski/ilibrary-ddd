package com.mvucevski.usermanagement.domain;

import com.mvucevski.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class RoleId extends DomainObjectId {

    public RoleId(String id) {
        super(id);
    }

    public RoleId() {
        super(DomainObjectId.randomId(RoleId.class).toString());
    }


}
