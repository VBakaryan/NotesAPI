package com.homemade.note.service;


import com.homemade.note.domain.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    User getUserByEmail(String email);

    List<User> getUsers(Boolean includeNotes, Integer page, Integer size);

    boolean exist(Long id);
}
