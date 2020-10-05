package com.mvucevski.bookreview.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mvucevski.sharedkernel.domain.base.DomainObjectId;
import lombok.NoArgsConstructor;

public class BookId extends DomainObjectId {

    //Unused
    public BookId(){
        super("");
    }

    @JsonCreator
    public BookId(String id) {
        super(id);
    }
}
