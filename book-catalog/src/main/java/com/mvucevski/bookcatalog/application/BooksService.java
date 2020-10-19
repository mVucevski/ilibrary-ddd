package com.mvucevski.bookcatalog.application;

import com.mvucevski.bookcatalog.domain.model.*;
import com.mvucevski.bookcatalog.port.payload.CreateBookRequest;
import com.mvucevski.bookcatalog.config.RabbitMqConfig;
import com.mvucevski.bookcatalog.domain.event.*;
import com.mvucevski.bookcatalog.exceptions.BookNotFoundException;
import com.mvucevski.bookcatalog.domain.repository.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BooksService {

    private final BooksRepository booksRepository;
    private final Logger logger;

    public BooksService(@Qualifier("dbBooksRepository") BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
        logger = LoggerFactory.getLogger(BooksService.class);
    }

    public List<Book> getAllBooks(){
        return booksRepository.getAllBooks();
    }

    public Optional<Book> getBookById(BookId bookId){
        return booksRepository.getBookById(bookId);
    }

    public Book updateBook(String bookId, Book updatedBook){
        Book book = getBookById(new BookId(bookId)).orElseThrow(()-> new BookNotFoundException("Book with Id: " + updatedBook.getId().getId() + " doesn't exist."));

        book.setAuthor(updatedBook.getAuthor());
        book.setTitle(updatedBook.getTitle());
        book.setAvailableCopies(updatedBook.getAvailableCopies());
        book.setIsbn(book.getIsbn());
        book.setCoverUrl(updatedBook.getCoverUrl());
        book.setDescription(updatedBook.getDescription());
        book.setGenre(updatedBook.getGenre());
        book.setLanguage(updatedBook.getLanguage());
        book.setPublicationDate(updatedBook.getPublicationDate());

        return booksRepository.saveBook(book);
    }

    public Book saveOrUpdateBook(CreateBookRequest bookRequest){

        Book book = new Book(new Title(bookRequest.getTitle()), new Author(bookRequest.getAuthor()),
                bookRequest.getDescription(),
                new ISBN(bookRequest.getIsbn()),
                Language.valueOf(bookRequest.getLanguage()),
                Genre.valueOf(bookRequest.getGenre()),
                bookRequest.getAvailableCopies(),
                bookRequest.getCoverUrl(),
                bookRequest.getPublicationDate());;

        if(book.getCoverUrl()==null || book.getCoverUrl().length() < 1){
            book.setCoverUrl("https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9780/2410/9780241003435.jpg");
        }

        if(bookRequest.getId() == null){
            logger.info("Saving book with id: " + book.getId().getId());
            return saveBook(book);
        }else{
            logger.info("Updating book with id: " + book.getId().getId());
            return updateBook(bookRequest.getId(), book);
        }

    }

    private Book saveBook(Book book){
        return booksRepository.saveBook(book);
    }

    public void removeBookById(BookId bookId){
        booksRepository.getBookById(bookId)
                .orElseThrow(() -> new RuntimeException("Book with Id: " + bookId.getId() + " doesn't exist."));

        booksRepository.deleteBook(bookId);
    }

    public List<Book> searchBooks(String keyword){
        if(keyword.equals("")){
            return booksRepository.getAllBooks();
        }
        return booksRepository.searchBooks(keyword, keyword, keyword);
    }

    public List<Book> findAllByGenre(String genre){
        if(genre == null || genre.length() == 0){
            return new ArrayList<>();
        }

        return booksRepository.findAllByGenre(Genre.valueOf(genre));
    }

    @RabbitListener(queues = RabbitMqConfig.BOOK_REVIEW_ADDED_ROUTING_KEY)
    public void consumeReviewAddedEvent(final ReviewAdded reviewAdded) {
        Optional<Book> bookOpt = booksRepository.getBookById(new BookId(reviewAdded.bookId()));

        if(bookOpt.isPresent()){
            Book book = bookOpt.get();
            book.addReview(reviewAdded.rating());
            saveBook(book);
        }

        logger.info("Consumed Review Added Event for book with id: " + reviewAdded.bookId());
    }

    @RabbitListener(queues = RabbitMqConfig.BOOK_REVIEW_EDITED_ROUTING_KEY)
    public void consumeReviewEditedEvent(final ReviewEdited reviewEdited) {
        Optional<Book> bookOpt = booksRepository.getBookById(new BookId(reviewEdited.bookId()));

        if(bookOpt.isPresent()){
            Book book = bookOpt.get();
            book.editReview(reviewEdited.oldRating(), reviewEdited.newRating());
            saveBook(book);
        }
        logger.info("Consumed Review Edited Event for book with id: " + reviewEdited.bookId());
    }

    @RabbitListener(queues = RabbitMqConfig.RESERVATION_CREATED_ROUTING_KEY)
    public void consumeReservationCreatedEvent(final ReservationCreated reservationCreated) {

        Optional<Book> bookOpt = booksRepository.getBookById(new BookId(reservationCreated.bookId()));

        if(bookOpt.isPresent()){
            Book book = bookOpt.get();
            book.subtractCopies(1);
            saveBook(book);
            logger.info("Consumed Reservation Created Event for book with id: " + reservationCreated.bookId());
        }else{
            logger.error("Consumed Reservation Created Event but book doesn't exist with id: " + reservationCreated.bookId());
        }
    }

    @RabbitListener(queues = RabbitMqConfig.LOAN_CREATED_ROUTING_KEY)
    public void consumeLoanCreatedEvent(final LoanCreated loanCreated) {

        Optional<Book> bookOpt = booksRepository.getBookById(new BookId(loanCreated.bookId()));

        if(bookOpt.isPresent()){
            Book book = bookOpt.get();
            book.subtractCopies(1);
            saveBook(book);
            logger.info("Consumed Loan Created Event for book with id: " + loanCreated.bookId());
        }else{
            logger.error("Consumed Loan Created Event but book doesn't exist with id: " + loanCreated.bookId());
        }
    }

    @RabbitListener(queues = RabbitMqConfig.LOAN_RETURNED_ROUTING_KEY)
    public void consumeLoanReturnedEvent(final LoanReturned loanReturned) {

        Optional<Book> bookOpt = booksRepository.getBookById(new BookId(loanReturned.bookId()));

        if(bookOpt.isPresent()){
            Book book = bookOpt.get();
            book.addCopies(1);
            saveBook(book);
            logger.info("Consumed Loan Returned Event for book with id: " + loanReturned.bookId());
        }else{
            logger.error("Consumed Loan Returned Event but book doesn't exist with id: " + loanReturned.bookId());
        }
    }
}
