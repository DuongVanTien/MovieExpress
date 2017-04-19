package vn.framgia.dao;

import vn.framgia.bean.FilmBean;
import vn.framgia.model.Film;
import java.util.List;

/**
 * Created by FRAMGIA\duong.van.tien on 10/04/2017.
 */
public interface IFilmDAO extends IGenericDAO<Film, Integer> {
    public Film save(Film Film);
    public Long count();
    public boolean deleteFilm(Integer id);
}
