package com.homemade.etl.service.impl;


import com.homemade.etl.domain.User;
import com.homemade.etl.repository.UserRepository;
import com.homemade.etl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAllUsers();
    }

    @Override
    public List<User> getRecentWithPagination(int page, int size, Date from, Date to) {
        return userRepository.getRecentUsersWithPagination(page, size, from, to);
    }

}
