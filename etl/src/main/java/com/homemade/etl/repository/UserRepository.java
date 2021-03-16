package com.homemade.etl.repository;

import com.homemade.etl.domain.User;

import java.util.Date;
import java.util.List;


public interface UserRepository {

    List<User> getAllUsers();

    List<User> getRecentUsersWithPagination(int page, int size, Date from, Date to);

}
