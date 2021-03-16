package com.homemade.etl.service;

import java.util.Date;
import java.util.List;


public interface CommonService<T> {

    List<T> getAll();

    List<T> getRecentWithPagination(int page, int size, Date from, Date to);
}
