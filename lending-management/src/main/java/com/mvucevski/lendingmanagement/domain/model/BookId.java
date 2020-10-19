package com.mvucevski.lendingmanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mvucevski.sharedkernel.domain.base.DomainObjectId;

public class BookId extends DomainObjectId {

    public BookId() {
        super("");
    }

    @JsonCreator
    public BookId(String id) {
        super(id);
    }
}
