package com.homemade.note.service;


import com.homemade.note.domain.User;

public interface UserService {

    User getUserById(Long id);

    User getUserByEmail(String email);

    boolean exist(Long id);
}
