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

    }
}
