package vn.framgia.dao.impl;

import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.Serializable;
import java.util.List;

/**
 * @author ducda referenced from CaveatEmptor project tm JBoss Hibernate version
 */
public abstract class GenericDAO<E, Id extends Serializable> {
	
	@Autowired
	SessionFactory sessionFactory;

	public Session getSession() {
		return this.sessionFactory.openSession();
	}
	
	private Class<E> persistentClass;

	public Class<E> getPersistentClass() {
		return persistentClass;
	}
	
	public void setPersistentClass(Class<E> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public E findById(Id id) {
		Criteria criteria = sessionFactory.openSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("id", id));
		Object obj = criteria.uniqueResult();
		if (obj == null) {
			return null;
		}
		return (E) obj;
	}

	@SuppressWarnings("unchecked")
	public E findById(Id id, boolean lock) {
		E entity;

		sessionFactory.openSession().setCacheMode(CacheMode.PUT);
		if (lock)
			entity = (E) sessionFactory.openSession().get(getPersistentClass(), id, LockMode.UPGRADE);
		else
			entity = (E) sessionFactory.openSession().get(getPersistentClass(), id);
		if (entity != null)
			sessionFactory.openSession().refresh(entity);
		if (entity == null) {
			sessionFactory.openSession().load(getPersistentClass(), id);
		}
		if (entity != null)
			sessionFactory.openSession().refresh(entity);
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		return findByCriteria();
	}

	@SuppressWarnings("unchecked")
	// exampleInstance la mau Object
	// excludeProperty la mot mang String chua ten cac property ma ta ko muon
	// dua vao tieu chi tim kiem
	public List<E> findByExample(E exampleInstance, String[] excludeProperty) {
		Criteria crit = sessionFactory.openSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	// return number of row when Searching
	public int count(E exampleInstance, String[] excludeProperty, boolean isLike) {
		Criteria crit = sessionFactory.openSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		if (isLike) {
			example.enableLike(MatchMode.ANYWHERE).ignoreCase();
		}
		return (Integer) crit.add(example).setProjection(Projections.rowCount()).uniqueResult();
	}

	public Long count() {
		return  (Long)sessionFactory.openSession().createCriteria(this.getPersistentClass()).setProjection(Projections.rowCount())
				.uniqueResult();
	}

	public int count(Criterion... criterion) {
		Criteria crit = sessionFactory.openSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return (Integer) crit.setProjection(Projections.rowCount()).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public E save(E entity) {
		sessionFactory.openSession().saveOrUpdate(entity);
		sessionFactory.openSession().flush();
		return entity;
	}

	public void delete(E entity) {
		sessionFactory.openSession().delete(entity);
		sessionFactory.openSession().flush();
	}

	@SuppressWarnings("unchecked")
	public List<E> findByCriteria(Criterion... criterion) {
		Criteria crit = sessionFactory.openSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<E> list(Integer offset, Integer maxResults) {
		return sessionFactory.openSession().createCriteria(getPersistentClass())
				.setFirstResult(offset!=null?offset:0)
				.setMaxResults(maxResults!=null?maxResults:15)
				.list();
	}

	@SuppressWarnings("unchecked")
	public E save(E entity, Session session) {
		session.saveOrUpdate(entity);
		session.flush();
		return entity;
	}

}
