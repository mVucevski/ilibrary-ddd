package com.mvucevski.lendingmanagement.application;

import com.mvucevski.lendingmanagement.domain.User;
import com.mvucevski.lendingmanagement.domain.UserId;

public interface UserManagement {

    User findById(UserId userId);

    User findByUsername(String username);
}
