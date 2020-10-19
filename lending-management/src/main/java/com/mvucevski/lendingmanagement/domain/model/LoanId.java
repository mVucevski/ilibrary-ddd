package com.mvucevski.lendingmanagement.domain.model;

import com.mvucevski.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class LoanId extends DomainObjectId {

    public LoanId(String id) {
        super(id);
    }

    public LoanId() {
        super(DomainObjectId.randomId(LoanId.class).toString());
    }
}
