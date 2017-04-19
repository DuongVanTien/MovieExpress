package vn.framgia.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<E, Id extends Serializable> extends Serializable {

	public List<E> findAll();

	public E save(E entity);

	public void delete(E entity);

	public List<E> list(Integer offset, Integer maxResults, String cityName);

	public E findById(Id id);
}
