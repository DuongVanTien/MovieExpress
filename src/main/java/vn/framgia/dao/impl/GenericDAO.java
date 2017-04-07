package vn.framgia.dao.impl;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.*;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.List;

/**
 * @author ducda referenced from CaveatEmptor project tm JBoss Hibernate version
 */
public abstract class GenericDAO<E, Id extends Serializable> extends HibernateDaoSupport {
	private Class<E> persistentClass;

	public Class<E> getPersistentClass() {
		return persistentClass;
	}
	
	public void setPersistentClass(Class<E> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public E findById(Id id) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
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

		getSession().setCacheMode(CacheMode.PUT);
		if (lock)
			entity = (E) getSession().get(getPersistentClass(), id, LockMode.UPGRADE);
		else
			entity = (E) getSession().get(getPersistentClass(), id);
		if (entity != null)
			getSession().refresh(entity);
		if (entity == null) {
			getSession().load(getPersistentClass(), id);
		}
		if (entity != null)
			getSession().refresh(entity);
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
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	// return number of row when Searching
	public int count(E exampleInstance, String[] excludeProperty, boolean isLike) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
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
		return  (Long)getSession().createCriteria(this.getPersistentClass()).setProjection(Projections.rowCount())
				.uniqueResult();
	}

	public int count(Criterion... criterion) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return (Integer) crit.setProjection(Projections.rowCount()).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public E save(E entity) {
		getSession().saveOrUpdate(entity);
		getSession().flush();

		return entity;
	}

	public void delete(E entity) {
		getSession().delete(entity);
		getSession().flush();
	}

	@SuppressWarnings("unchecked")
	public List<E> findByCriteria(Criterion... criterion) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<E> list(Integer offset, Integer maxResults) {
		return getSession().createCriteria(getPersistentClass())
				.setFirstResult(offset!=null?offset:0)
				.setMaxResults(maxResults!=null?maxResults:15)
				.list();
	}

}
