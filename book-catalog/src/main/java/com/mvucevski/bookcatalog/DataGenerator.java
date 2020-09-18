package com.mvucevski.bookcatalog;

import com.mvucevski.bookcatalog.domain.*;
import com.mvucevski.bookcatalog.repository.DbBooksRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
public class DataGenerator {

    private final DbBooksRepository booksRepository;

    public DataGenerator(DbBooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @PostConstruct
    @Transactional
    public void generateData(){

        if(booksRepository.getAllBooks().isEmpty()){
            BookId id = new BookId();
            System.out.println("-----------------"+id.toString());
            Book book = new Book(id,
                    new Title("The Choice"),
                    new Author("Edith Eger"),
                    "Even in hell, hope can flower",
                    Language.English,
                    Genre.NonFiction,
                    3,
                    "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1501539010l/30753738.jpg",
                    LocalDate.of(2019, 1, 28));

        booksRepository.saveBook(book);
        }
    }
}
