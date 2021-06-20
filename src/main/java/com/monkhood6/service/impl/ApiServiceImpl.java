/**
 * 
 */
package com.monkhood6.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.monkhood6.repository.DB;
import com.monkhood6.service.ApiService;

/**
 * @author Khatri
 *
 */
@Service
public class ApiServiceImpl<T> extends DB<T> implements ApiService<T> {

	@Override
	public void save(T obj) {
		saveToDB(obj);
	}

	@Override
	public void update(T obj) {
		updateToDB(obj);
	}

	@Override
	public void delete(Class<T> classType, long id) {
		removeFromDB(classType, id);
	}

	@Override
	public T get(Class<T> classType, long id) {
		return find(classType, id);
	}

	@Override
	public List<T> getAll(Class<T> classType) {
		return findAll(classType);
	}

	@Override
	public List<T> getAll(Class<T> classType, String fieldName, String fieldValue) {
		return findAll(classType, fieldName, fieldValue);
	}

	@Override
	public T getSingleResult(Class<T> classType, String fieldName, String fieldValue) {
		return findSingleResult(classType, fieldName, fieldValue);
	}

	@Override
	public List<T> getAllByQuery(String query) {
		return findAllByQuery(query);
	}

	@Override
	public long getLastEntryId(String tableName) {
		return lastEntryId(tableName);
	}

	@Override
	public void executeQuery(String query) {
		runQuery(query);
	}

	@Override
	public void saveOrUpdate(T obj) {
		saveToDBOrUpdate(obj);
	}

}
