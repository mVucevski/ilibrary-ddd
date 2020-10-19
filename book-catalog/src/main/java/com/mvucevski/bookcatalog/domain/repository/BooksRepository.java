package com.mvucevski.bookcatalog.domain.repository;

import com.mvucevski.bookcatalog.domain.model.Book;
import com.mvucevski.bookcatalog.domain.model.BookId;
import com.mvucevski.bookcatalog.domain.model.Genre;
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
