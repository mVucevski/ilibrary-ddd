package com.mvucevski.bookcatalog.api;

import com.mvucevski.bookcatalog.domain.Book;
import com.mvucevski.bookcatalog.domain.BookId;
import com.mvucevski.bookcatalog.service.BooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Book> getAllBooks(@PathVariable String id){
        return booksService.getBookById(new BookId(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
