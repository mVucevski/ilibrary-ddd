package com.mvucevski.bookreview.domain;

import com.mvucevski.sharedkernel.domain.base.DomainObjectId;

public class BookId extends DomainObjectId {

    ///@JsonCreator
    public BookId(String id) {
        super(id);
    }
}
