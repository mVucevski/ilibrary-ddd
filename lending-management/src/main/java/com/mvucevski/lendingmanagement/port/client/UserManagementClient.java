package com.mvucevski.lendingmanagement.port.client;

import com.mvucevski.lendingmanagement.application.UserManagement;
import com.mvucevski.lendingmanagement.domain.User;
import com.mvucevski.lendingmanagement.domain.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserManagementClient implements UserManagement {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagementClient.class);

    private final RestTemplate restTemplate;
    private final String serverUrl;

    public UserManagementClient(@Value("${app.user-management.url}") String serverUrl,
                                @Value("${app.user-management.connect-timeout-ms}") int connectTimeout,
                                @Value("${app.user-management.read-timeout-ms}") int readTimeout
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
    public User findById(UserId userId) {
        try {
            return restTemplate.exchange(uri().path("/api/users/"+userId.getId()).build().toUri(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<User>() {
                    }).getBody();
        } catch (Exception ex) {
            LOGGER.error("Error retrieving user by id", ex);
            return null;
        }

    }

    @Override
    public User findByUsername(String username) {
        try {
            return restTemplate.exchange(uri().path("/api/users/username/"+username).build().toUri(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<User>() {
                    }).getBody();
        } catch (Exception ex) {
            LOGGER.error("Error retrieving user by username", ex);
            return null;
        }
    }
}
