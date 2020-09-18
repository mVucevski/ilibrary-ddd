package com.mvucevski.bookcatalog.api;

import com.mvucevski.bookcatalog.domain.Book;
import com.mvucevski.bookcatalog.domain.BookId;
import com.mvucevski.bookcatalog.service.BooksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return booksService.getAllBooks();
    }

    //TODO Return DTO instead of entity
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id){
        return booksService.getBookById(new BookId(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<?> createNewBook(@RequestBody Book book) {

        Book newBook = booksService.saveOrUpdateBook(book);
        return new ResponseEntity<Book>(newBook, HttpStatus.CREATED);
    }
}
