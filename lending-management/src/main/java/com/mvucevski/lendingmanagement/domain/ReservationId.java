package com.mvucevski.lendingmanagement.domain;

import com.mvucevski.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class ReservationId extends DomainObjectId {

    public ReservationId(String id) {
        super(id);
    }

    public ReservationId() {
        super(DomainObjectId.randomId(ReservationId.class).toString());
    }
}
