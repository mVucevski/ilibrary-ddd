package com.mvucevski.lendingmanagement.application;

import com.mvucevski.lendingmanagement.domain.model.User;
import com.mvucevski.lendingmanagement.domain.model.UserId;

public interface UserManagement {

    User findById(UserId userId);

    User findByUsername(String username);
}
