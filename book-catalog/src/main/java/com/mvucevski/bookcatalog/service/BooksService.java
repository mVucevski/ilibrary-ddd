package com.mvucevski.bookcatalog.service;

import com.mvucevski.bookcatalog.config.RabbitMqConfig;
import com.mvucevski.bookcatalog.domain.Book;
import com.mvucevski.bookcatalog.domain.BookId;
import com.mvucevski.bookcatalog.domain.event.ReviewAdded;
import com.mvucevski.bookcatalog.domain.event.ReviewEdited;
import com.mvucevski.bookcatalog.repository.BooksRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    public Book saveOrUpdateBook(Book book){
        return booksRepository.saveBook(book);
    }

    @RabbitListener(queues = RabbitMqConfig.BOOK_REVIEW_ADDED_ROUTING_KEY)
    public void consumeReviewAddedEvent(final ReviewAdded reviewAdded) {
        System.out.println("ReviewAdded: " + reviewAdded.content());

        Optional<Book> bookOpt = booksRepository.getBookById(new BookId(reviewAdded.bookId()));

        if(bookOpt.isPresent()){
            Book book = bookOpt.get();
            book.addReview(reviewAdded.rating());
            saveOrUpdateBook(book);
        }

        // log.info("Received message, tip is: {}", message);
    }

    @RabbitListener(queues = RabbitMqConfig.BOOK_REVIEW_EDITED_ROUTING_KEY)
    public void consumeReviewAddedEvent(final ReviewEdited reviewEdited) {
        System.out.println("ReviewEdited: " + reviewEdited.oldRating());
        System.out.println("ReviewEdited new ratng: " + reviewEdited.newRating());

        Optional<Book> bookOpt = booksRepository.getBookById(new BookId(reviewEdited.bookId()));

        if(bookOpt.isPresent()){
            Book book = bookOpt.get();
            book.editReview(reviewEdited.oldRating(), reviewEdited.newRating());
            saveOrUpdateBook(book);
        }
        // log.info("Received message, tip is: {}", message);
    }
}
