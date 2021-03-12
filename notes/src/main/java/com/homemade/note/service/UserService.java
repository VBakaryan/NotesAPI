package com.homemade.note.service;


import com.homemade.note.domain.User;

public interface UserService {

    User getUserById(Long id);

    boolean exist(Long id);

}
