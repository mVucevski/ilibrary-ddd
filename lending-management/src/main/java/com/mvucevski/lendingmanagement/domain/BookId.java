package com.mvucevski.lendingmanagement.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mvucevski.sharedkernel.domain.base.DomainObjectId;

public class BookId extends DomainObjectId {

    ///@JsonCreator
    public BookId(String id) {
        super(id);
    }
}
