package com.homemade.note.service.impl;

import com.homemade.note.domain.User;
import com.homemade.note.entity.UserEntity;
import com.homemade.note.repository.UserRepository;
import com.homemade.note.service.UserService;
import com.homemade.note.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
         UserEntity userEntity = userRepository.findByEmail(email);

        return userMapper.map(userEntity, User.class);
    }

    @Override
    public boolean exist(Long id) {
        return userRepository.existsById(id);
    }

}
