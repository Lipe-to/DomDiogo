package com.domdiogo.repository;

import java.util.List;

public interface Repository {
    List<Object> read();
    Object findById(int id);
    int create(Object object);
    int delete(int id);
}
