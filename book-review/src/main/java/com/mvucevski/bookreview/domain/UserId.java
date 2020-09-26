package com.mvucevski.bookreview.domain;

import com.mvucevski.sharedkernel.domain.base.DomainObjectId;

public class UserId extends DomainObjectId {

    ///@JsonCreator
    public UserId(String id) {
        super(id);
    }
}
