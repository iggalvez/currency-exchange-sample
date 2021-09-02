package com.ignacio.currencyexchangeservice.persistence.repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Ignacio Galvez
 * @param <T> The class to be persisted
 * @param <ID> The class of the primary key
 */
public interface IRepository<T,ID> {

    /**
     * method for retrieving an object of type T
     * with known fields values of The object
     * @param args the diverse know field values of the T objects
     * @return The object with the values passed in args or nul if there's no match
     */
    public Optional<T> find(String... args);
    public Optional<T> findById(ID id);
    public List<T> findAll();
    public void delete(T t);
    public void save(T t);
}
