package com.mvucevski.bookcatalog.api;

import com.mvucevski.bookcatalog.api.payload.BookDTO;
import com.mvucevski.bookcatalog.api.payload.BookListItem;
import com.mvucevski.bookcatalog.domain.Book;
import com.mvucevski.bookcatalog.domain.BookId;
import com.mvucevski.bookcatalog.service.BooksService;
import com.mvucevski.bookcatalog.service.ImagesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/books")
public class BooksController {

    private final BooksService booksService;
    private final ImagesService imagesService;

    public BooksController(BooksService booksService, ImagesService imagesService) {
        this.booksService = booksService;
        this.imagesService = imagesService;
    }

    @GetMapping
    public List<BookListItem> getAllBooks(){

        return booksService.getAllBooks().stream().map(e->
                new BookListItem(
                        e.getId().getId(),
                        e.getTitle().getTitle(),
                        e.getAuthor().getName(),
                        e.getCoverUrl(),
                        e.getAvailableCopies(),
                        e.getRating()
                )).collect(Collectors.toList());
    }

    //TODO Return DTO instead of entity
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable String id){
        return booksService.getBookById(new BookId(id))
                .map(e->new ResponseEntity<BookDTO>(
                        new BookDTO(e.getId().getId(),
                                e.getTitle().getTitle(),
                                e.getAuthor().getName(),
                                e.getIsbn().getIsbn(),
                                e.getDescription(),
                                e.getLanguage().name(),
                                e.getGenre().name(),
                                e.getCoverUrl(),
                                e.getAvailableCopies(),
                                e.getPublicationDate(),
                                e.getRating()), HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<?> createNewBook(@RequestBody Book book) {

        Book newBook = booksService.saveOrUpdateBook(book);
        return new ResponseEntity<Book>(newBook, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/copiesLeft")
    public ResponseEntity<?> getCopiesLeftByBookId(@PathVariable String id){
        return booksService.getBookById(new BookId(id))
                .map(book -> new ResponseEntity<>(book.getAvailableCopies(), HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/uploadImg")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws Exception {

        if (file == null) {
            //throw new ImageUploadException("Please upload an jpeg or png image");
            throw new FileNotFoundException("Please upload an jpeg or png image");
        }

        String imagePath = imagesService.uploadImage(file);

        return new ResponseEntity<String>(imagePath, HttpStatus.OK);
    }

    @GetMapping(
            value = "/image/{name}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImageWithMediaType(@PathVariable String name) throws IOException {

        return imagesService.getImage(name);
    }
}
