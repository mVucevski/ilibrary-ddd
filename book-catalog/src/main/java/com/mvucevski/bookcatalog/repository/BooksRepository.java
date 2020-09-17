package com.mvucevski.bookcatalog.repository;

import com.mvucevski.bookcatalog.domain.Book;
import com.mvucevski.bookcatalog.domain.BookId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository {

    List<Book> getAllBooks();

    Optional<Book> getBookById(BookId bookId);

    Book saveBook(Book book);

    Boolean deleteBook(BookId bookId);

}
