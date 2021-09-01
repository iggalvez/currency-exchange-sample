package com.ignacio.currencyexchangeservice.persistence.repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T,ID> {

    public T find(String... args);
    public Optional<T> findById(ID id);
    public List<T> findAll();
    public void delete(T t);
    public void save(T t);
}
