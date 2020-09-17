package com.mvucevski.bookcatalog.repository;

import com.mvucevski.bookcatalog.domain.Book;
import com.mvucevski.bookcatalog.domain.BookId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBooksRepository extends JpaRepository<Book, BookId> {
}
