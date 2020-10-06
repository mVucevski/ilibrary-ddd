package com.mvucevski.bookcatalog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String EXCHANGE_NAME = "topic_exchange";
    public static final String BOOK_REVIEW_ADDED_PARSING_QUEUE = "book_review_added";
    public static final String BOOK_REVIEW_EDITED_PARSING_QUEUE = "book_review_edited";
    public static final String BOOK_REVIEW_ADDED_ROUTING_KEY = "book_review_added";
    public static final String BOOK_REVIEW_EDITED_ROUTING_KEY = "book_review_edited";

    public static final String LOAN_CREATED_PARSING_QUEUE = "loan_created";
    public static final String RESERVATION_CREATED_PARSING_QUEUE = "reservation_created";
    public static final String LOAN_CREATED_ROUTING_KEY = "loan_created";
    public static final String RESERVATION_CREATED_ROUTING_KEY = "reservation_created";

    public static final String LOAN_RETURNED_PARSING_QUEUE = "loan_returned";
    public static final String LOAN_RETURNED_ROUTING_KEY = "loan_returned";


    @Bean
    Queue bookReviewAddedQueue() {
        return new Queue(BOOK_REVIEW_ADDED_PARSING_QUEUE);
    }

    @Bean
    Queue bookReviewEditedQueue() {
        return new Queue(BOOK_REVIEW_EDITED_PARSING_QUEUE);
    }

    @Bean
    Queue loanCreatedQueue() {
        return new Queue(LOAN_CREATED_PARSING_QUEUE);
    }

    @Bean
    Queue reservationCreatedQueue() {
        return new Queue(RESERVATION_CREATED_PARSING_QUEUE);
    }

    @Bean
    Queue loanReturnedQueue() {
        return new Queue(LOAN_RETURNED_PARSING_QUEUE);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding bookReviewAddedBinding() {
        return BindingBuilder.bind(bookReviewAddedQueue()).to(exchange()).with(BOOK_REVIEW_ADDED_ROUTING_KEY);
    }

    @Bean
    Binding bookReviewEditedBinding() {
        return BindingBuilder.bind(bookReviewEditedQueue()).to(exchange()).with(BOOK_REVIEW_EDITED_ROUTING_KEY);
    }

    @Bean
    Binding loanCreatedBinding() {
        return BindingBuilder.bind(loanCreatedQueue()).to(exchange()).with(LOAN_CREATED_ROUTING_KEY);
    }

    @Bean
    Binding reservationCreatedBinding() {
        return BindingBuilder.bind(reservationCreatedQueue()).to(exchange()).with(RESERVATION_CREATED_ROUTING_KEY);
    }

    @Bean
    Binding loanReturnedBinding() {
        return BindingBuilder.bind(loanReturnedQueue()).to(exchange()).with(LOAN_RETURNED_ROUTING_KEY);
    }


    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
//        ObjectMapper mapper = new ObjectMapper();
//        return new Jackson2JsonMessageConverter(mapper);
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
