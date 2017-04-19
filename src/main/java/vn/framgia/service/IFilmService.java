package vn.framgia.service;

import java.io.Serializable;
import vn.framgia.bean.FilmBean;

import java.util.Date;
import java.util.List;

/**
 * Created by FRAMGIA\duong.van.tien on 10/04/2017.
 */
public interface IFilmService extends Serializable {
    public boolean cloneData (String urlCloneData) throws Exception;
    public List<FilmBean> list(Integer offset, Integer maxResults, String cityName, String today);
    public Long count();
}
