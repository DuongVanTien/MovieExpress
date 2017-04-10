package vn.framgia.dao.impl;

import org.springframework.stereotype.Repository;
import vn.framgia.dao.IFilmDAO;
import vn.framgia.dao.IGenericDAO;
import vn.framgia.model.Film;

/**
 * Created by FRAMGIA\duong.van.tien on 10/04/2017.
 */
@Repository
public class FilmDAOImpl extends GenericDAO<Film, Integer> implements IFilmDAO {
}
