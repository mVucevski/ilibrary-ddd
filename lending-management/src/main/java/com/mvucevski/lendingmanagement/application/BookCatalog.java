package com.mvucevski.lendingmanagement.application;

import com.mvucevski.lendingmanagement.domain.BookId;

public interface BookCatalog {

    int getAvailableCopiesByBookId(BookId bookId);
}
