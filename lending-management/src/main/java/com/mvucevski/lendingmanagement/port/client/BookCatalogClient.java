package com.mvucevski.lendingmanagement.port.client;

import com.mvucevski.lendingmanagement.application.BookCatalog;
import com.mvucevski.lendingmanagement.domain.model.BookId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BookCatalogClient implements BookCatalog {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookCatalogClient.class);

    private final RestTemplate restTemplate;
    private final String serverUrl;

    public BookCatalogClient(@Value("${app.book-catalog.url}") String serverUrl,
                                @Value("${app.book-catalog.connect-timeout-ms}") int connectTimeout,
                                @Value("${app.book-catalog.read-timeout-ms}") int readTimeout
    ) {
        this.serverUrl = serverUrl;
        restTemplate = new RestTemplate();
        var requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        restTemplate.setRequestFactory(requestFactory);
    }

    private UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(serverUrl);
    }

    @Override
    public int getAvailableCopiesByBookId(BookId bookId) {
        try {
            return restTemplate.exchange(uri().path("/api/books/" + bookId.getId() + "/copiesLeft").build().toUri(), HttpMethod.GET, null,
                    Integer.class).getBody();
        } catch (Exception ex) {
            LOGGER.error("Error retrieving product by id");
            return -1;
        }

    }
}
