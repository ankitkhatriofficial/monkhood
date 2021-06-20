package com.monkhood6.service;

import java.util.List;

public interface ApiService<T> {

	public void save(T obj);

	public void update(T obj);

	public void delete(Class<T> classType, long id);

	public T get(Class<T> classType, long id);

	public List<T> getAll(Class<T> classType);

	public List<T> getAll(Class<T> classType, String fieldName, String fieldValue);

	public T getSingleResult(Class<T> classType, String fieldName, String fieldValue);

	public List<T> getAllByQuery(String query);

	public void executeQuery(String query);

	public long getLastEntryId(String tableName);

	public void saveOrUpdate(T obj);

}
