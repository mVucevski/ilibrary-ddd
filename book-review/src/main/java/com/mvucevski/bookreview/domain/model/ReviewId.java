package com.mvucevski.bookreview.domain.model;

import com.mvucevski.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class ReviewId extends DomainObjectId {

    public ReviewId() {
        super(DomainObjectId.randomId(ReviewId.class).toString());
    }

    public ReviewId(String id) {
        super(id);
    }
}
