package com.domain.service;

import com.database.entity.BookingEntity;
import com.domain.model.Booking;
import com.domain.model.User;

import java.util.List;

public interface UserServiceForAdmin {

    User getUserForLogin(String login, String password);

    void updateUserByAdmin(User user);


}

