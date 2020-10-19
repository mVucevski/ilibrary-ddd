package com.mvucevski.bookcatalog.domain.repository;

import com.mvucevski.bookcatalog.domain.model.Book;
import com.mvucevski.bookcatalog.domain.model.BookId;
import com.mvucevski.bookcatalog.domain.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaBooksRepository extends JpaRepository<Book, BookId> {

    @Query("select b from Book b where lower(b.title) LIKE lower(concat('%', :title,'%'))" +
            "or b.isbn LIKE lower(concat('%', :isbn,'%')) or lower(b.author) LIKE lower(concat('%', :authorName,'%'))")
    List<Book> searchByTitleOrAuthorNameOrISBN(String isbn, String title, String authorName);

    @Query("select b from Book b where b.genre LIKE :genre")
    List<Book> findAllBooksByGenre(Genre genre);
}
