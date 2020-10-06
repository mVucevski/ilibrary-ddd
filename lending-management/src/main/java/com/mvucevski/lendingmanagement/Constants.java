package com.mvucevski.lendingmanagement;

public class Constants {
    public static final int MAX_RESERVATIONS_PER_USER = 2;
    public static final int MAX_LOANS_PER_USER = 1;
    public static final String FILE_PATH_IMG_STORAGE = "src/main/resources/images/";

    public static final String EXCHANGE_NAME = "topic_exchange";
    public static final String LOAN_CREATED_ROUTING_KEY = "loan_created";
    public static final String RESERVATION_CREATED_ROUTING_KEY = "reservation_created";
    public static final String LOAN_RETURNED_ROUTING_KEY = "loan_returned";
}
