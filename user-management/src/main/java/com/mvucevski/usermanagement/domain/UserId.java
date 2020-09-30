package com.mvucevski.usermanagement.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mvucevski.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class UserId extends DomainObjectId {

    public UserId(String id) {
        super(id);
    }

    public UserId() {
        super(DomainObjectId.randomId(UserId.class).toString());
    }
}
