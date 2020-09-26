package com.mvucevski.bookreview.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mvucevski.sharedkernel.domain.base.DomainObjectId;

public class UserId extends DomainObjectId {

    public UserId() {
        super("");
    }

    @JsonCreator
    public UserId(String id) {
        super(id);
    }
}
