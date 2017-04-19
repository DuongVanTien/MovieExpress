package vn.framgia.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import vn.framgia.dao.IFilmDAO;
import vn.framgia.model.Film;

/**
 * Created by FRAMGIA\duong.van.tien on 10/04/2017.
 */
@Repository
public class FilmDAOImpl extends GenericDAO<Film, Integer> implements IFilmDAO {
    public FilmDAOImpl() {
        super.setPersistentClass(Film.class);
    }

    @Override
    public boolean deleteFilm(Integer id) {
        String hql = "delete from Film where id = :id";
        Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        if (query == null)
            return false;
        query.executeUpdate();
        return true;
    }
}
