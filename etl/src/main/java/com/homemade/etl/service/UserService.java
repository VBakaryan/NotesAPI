package com.homemade.etl.service;


import com.homemade.etl.domain.User;

import java.util.Date;
import java.util.List;

public interface UserService {

    List<User> getAll();

    List<User> getRecentWithPagination(int page, int size, Date from, Date to);

}
