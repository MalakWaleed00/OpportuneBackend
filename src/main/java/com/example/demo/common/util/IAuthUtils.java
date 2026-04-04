package com.example.demo.common.util;

import com.example.demo.southbound.entity.User;

public interface IAuthUtils {

    User getCurrentUser();

    Long getCurrentUserId();

    String getCurrentUsername();
}