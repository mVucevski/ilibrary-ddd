package com.mvucevski.bookcatalog.domain;

import com.mvucevski.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class BookId extends DomainObjectId {

    public BookId() {
        super(DomainObjectId.randomId(BookId.class).toString());
    }

    public BookId(String id) {
        super(id);
    }
}
