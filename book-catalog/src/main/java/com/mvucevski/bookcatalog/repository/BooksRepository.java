package com.mvucevski.bookcatalog.repository;

import com.mvucevski.bookcatalog.domain.Book;
import com.mvucevski.bookcatalog.domain.BookId;
import com.mvucevski.bookcatalog.domain.Genre;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository {

    List<Book> getAllBooks();

    Optional<Book> getBookById(BookId bookId);

    Book saveBook(Book book);

    Boolean deleteBook(BookId bookId);

    List<Book> searchBooks(String isbn, String title, String authorName);

    List<Book> findAllByGenre(Genre genre);

}
