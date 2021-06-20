package com.monkhood6.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

/* DB Class is implemented for communicating with DataBase only. No other class will directly interact with DB class, 
* only ApiService Implementation class will have right to access the methods of DB class. 
*/
@SuppressWarnings({ "unchecked", "deprecation" })
@Transactional
public class DB<T> {

	/*
	 * Session Factory Object => used to open/close Hibernate Session and to perform
	 * Database operation.
	 */
	@Autowired
	private SessionFactory factory;

	/*
	 * It will save the given object into the Database. If any error occured, it
	 * will rollback.
	 */
	protected void saveToDB(T obj) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		try {
			session.save(obj);
		} catch (ConstraintViolationException e) {
			session.getTransaction().rollback();
			session.close();
			throw new ConstraintViolationException(e.getMessage(), e.getSQLException(), e.getConstraintName());
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			throw new RuntimeException("Error in Saving Object to Database..!");
		}
		session.getTransaction().commit();
		session.close();
	}

	/* function to update or save the object (FOR BATCH OPERATION) */
	protected void saveToDBOrUpdate(T obj) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		try {
			session.saveOrUpdate(obj);
		} catch (ConstraintViolationException e) {
			session.getTransaction().rollback();
			session.close();
			throw new ConstraintViolationException(e.getMessage(), e.getSQLException(), e.getConstraintName());
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			throw new RuntimeException("Error in Saving Object to Database..!");
		}
		session.getTransaction().commit();
		session.close();
	}

	/* update the object to Database */
	protected void updateToDB(T obj) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		try {
			session.update(obj);
		} catch (ConstraintViolationException e) {
			session.getTransaction().rollback();
			session.close();
			throw new ConstraintViolationException(e.getMessage(), e.getSQLException(), e.getConstraintName());
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			throw new RuntimeException("Error in Updating Object to Database..!");
		}
		session.getTransaction().commit();
		session.close();
	}

	/* Delete the object to Database */
	protected void removeFromDB(Class<T> classType, long id) {
		T data = find(classType, id);
		if (data == null)
			return;
		Session session = factory.openSession();
		session.getTransaction().begin();
		try {
			session.delete(data);
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			throw new RuntimeException("Error in Deleting Object to Database..!");
		}
		session.getTransaction().commit();
		session.close();
	}

	/*
	 * Returns a Single Object with given corresponding classType<T> [id => Long
	 * Type]
	 */
	protected T find(Class<T> classType, long id) {
		Session session = factory.openSession();
		T obj = session.get(classType, id);
		session.close();
		return obj;
	}

	/* Returns All the Records/Data from corresponding classType<T> */
	protected List<T> findAll(Class<T> classType) {
		Session session = factory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(classType);
		criteria.from(classType);
		List<T> data = session.createQuery(criteria).getResultList();
		session.close();
		if (data == null || data.isEmpty() || data.get(0) == null)
			return null;
		else
			return data;
	}

	/*
	 * Returns All the Records after matching a query for example: state -> Delhi,
	 * it will return all those records
	 */
	protected List<T> findAll(Class<T> classType, String fieldName, String fieldValue) {
		Session session = factory.openSession();
		Criteria criteria = session.createCriteria(classType);
		criteria.add(Restrictions.eq(fieldName, fieldValue));
		List<T> result = criteria.list();
		session.close();
		if (result == null || result.isEmpty() || result.get(0) == null)
			return null;
		else
			return result;
	}

	/*
	 * Returns All the Records after matching a query for example: email ->
	 * abc@xyz.com, it will return only first record
	 */
	protected T findSingleResult(Class<T> classType, String fieldName, String fieldValue) {
		List<T> result = findAll(classType, fieldName, fieldValue);
		if (result == null)
			return null;
		else
			return result.get(0);
	}

	/* function to fetch all records by Query */
	protected List<T> findAllByQuery(String query) {
		if (query == null || query.trim().isEmpty())
			return null;
		Session session = factory.openSession();
		List<T> result = session.createQuery(query).list();
		session.close();
		if (result == null || result.isEmpty() || result.get(0) == null)
			return null;
		else
			return result;
	}

	/* function to fetch Execute Query */
	protected void runQuery(String query) {
		if (query == null || query.trim().isEmpty())
			return;
		Session session = factory.openSession();
		session.getTransaction().begin();
		try {
			session.createQuery(query).executeUpdate();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
		}
	}

	/* Returns the last Record entered Primary Key(Id) */
	protected long lastEntryId(String tableName) {
		Session session = factory.openSession();
		Query<Long> query = session.createQuery("select max(id) from " + tableName);
		List<Long> list = query.list();
		session.close();
		if (list == null || list.isEmpty() || list.get(0) == null)
			return 0;
		else
			return list.get(0);
	}

}
