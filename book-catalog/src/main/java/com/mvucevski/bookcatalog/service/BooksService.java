package com.mvucevski.bookcatalog.service;

import com.mvucevski.bookcatalog.config.RabbitMqConfig;
import com.mvucevski.bookcatalog.domain.Book;
import com.mvucevski.bookcatalog.domain.BookId;
import com.mvucevski.bookcatalog.domain.event.*;
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

    @RabbitListener(queues = RabbitMqConfig.RESERVATION_CREATED_ROUTING_KEY)
    public void consumeReservationCreatedEvent(final ReservationCreated reservationCreated) {

        Optional<Book> bookOpt = booksRepository.getBookById(new BookId(reservationCreated.bookId()));

        if(bookOpt.isPresent()){
            Book book = bookOpt.get();
            book.subtractCopies(1);
            saveOrUpdateBook(book);
            System.out.println("Book's copies -1 - reservation created");
        }else{
            System.out.println("Book doesnt exist");
        }
        // log.info("Received message, tip is: {}", message);
    }

    @RabbitListener(queues = RabbitMqConfig.LOAN_CREATED_ROUTING_KEY)
    public void consumeReservationCreatedEvent(final LoanCreated loanCreated) {

        Optional<Book> bookOpt = booksRepository.getBookById(new BookId(loanCreated.bookId()));

        if(bookOpt.isPresent()){
            Book book = bookOpt.get();
            book.subtractCopies(1);
            saveOrUpdateBook(book);
            System.out.println("Book's copies -1 - loan created");
        }else{
            System.out.println("Book doesnt exist");
        }
        // log.info("Received message, tip is: {}", message);
    }

    @RabbitListener(queues = RabbitMqConfig.LOAN_RETURNED_ROUTING_KEY)
    public void consumeLoanReturnedEvent(final LoanReturned loanReturned) {

        Optional<Book> bookOpt = booksRepository.getBookById(new BookId(loanReturned.bookId()));

        if(bookOpt.isPresent()){
            Book book = bookOpt.get();
            book.addCopies(1);
            saveOrUpdateBook(book);
            System.out.println("Book's copies +1 - loan returned");
        }else{
            System.out.println("Book doesnt exist");
        }
        // log.info("Received message, tip is: {}", message);
    }
}
