package com.mvucevski.bookcatalog.domain.repository;

import com.mvucevski.bookcatalog.domain.model.Book;
import com.mvucevski.bookcatalog.domain.model.BookId;
import com.mvucevski.bookcatalog.domain.model.Genre;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DbBooksRepository implements BooksRepository{

    private final JpaBooksRepository repository;

    public DbBooksRepository(JpaBooksRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @Override
    public Optional<Book> getBookById(BookId bookId) {
        return repository.findById(bookId);
    }

    @Override
    public Book saveBook(Book book) {
        return repository.save(book);
    }

    @Override
    public Boolean deleteBook(BookId bookId) {
        repository.deleteById(bookId);
        return true;
    }

    @Override
    public List<Book> searchBooks(String isbn, String title, String authorName) {
        return repository.searchByTitleOrAuthorNameOrISBN(isbn, title, authorName);
    }

    @Override
    public List<Book> findAllByGenre(Genre genre) {
        return repository.findAllBooksByGenre(genre);
    }
}
