package com.mvucevski.bookcatalog.service;

import com.mvucevski.bookcatalog.domain.Book;
import com.mvucevski.bookcatalog.domain.BookId;
import com.mvucevski.bookcatalog.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BooksService {

    private final BooksRepository booksRepository;

    public BooksService(@Qualifier("dbBooksRepository") BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> getAllBooks(){
        return booksRepository.getAllBooks();
    }

    public Optional<Book> getBookById(BookId bookId){
//        return booksRepository.getBookById(bookId)
//                .orElseThrow(() -> new RuntimeException("Book with Id: " + bookId.getId() + " doesn't exist."));
        return booksRepository.getBookById(bookId);
    }

    public Book addNewBook(Book book){
        return booksRepository.saveBook(book);
    }


}
