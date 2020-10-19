package com.mvucevski.bookcatalog;

import com.mvucevski.bookcatalog.domain.repository.DbBooksRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

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
