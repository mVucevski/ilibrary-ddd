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

    @Bean
    Queue bookReviewAddedQueue() {
        return new Queue(BOOK_REVIEW_ADDED_PARSING_QUEUE);
    }

    @Bean
    Queue bookReviewEditedQueue() {
        return new Queue(BOOK_REVIEW_EDITED_PARSING_QUEUE);
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
