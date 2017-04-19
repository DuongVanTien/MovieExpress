package vn.framgia.service.impl;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import vn.framgia.bean.FilmBean;
import vn.framgia.model.Film;
import vn.framgia.model.Schedule;
import vn.framgia.service.IFilmService;
import vn.framgia.ulti.Constant;
import vn.framgia.ulti.Helpers;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.transaction.annotation.Propagation;

/**
 * Created by FRAMGIA\duong.van.tien on 10/04/2017.
 */
@Service
@EnableTransactionManagement
@Transactional
public class IFilmServiceImpl extends BaseserviceImpl implements IFilmService {

    private static final Logger logger = Logger.getLogger(CityServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public boolean cloneData(String cityName) throws Exception {
        try {
            List<Integer> listFilmIds = iScheduleDAO.findFilmIdByDate(Helpers.convertDatetoString(new Date()), cityName);
            for (Integer filmId : listFilmIds) {
                iFilmDAO.deleteFilm(filmId);
                iScheduleDAO.deleteScheduleByFilmId(filmId);
            }
            String urlCloneData = Constant.URL_ORIGIN+cityName;
            Document document = Jsoup.connect(urlCloneData).userAgent(Constant.USERAGENT).get();
            Elements listCinemasName = document.select("div[id=content_cinema] h2[class=title-cine]");
            Elements listFilms = document.select("div[class=film clearfix]");
            Elements listImages = listFilms.select("img");
            Random random = new Random();
            int count = 0;
            for (int i = 0; i < listCinemasName.size(); i++) {
                String cinemaName = listCinemasName.get(i).text();
                String listIdsFilmOfCinema = listCinemasName.get(i).attr("rel");
                String[] listIdFilms = listIdsFilmOfCinema.split(",");
                if (listIdFilms.length>1) {
                    for (String filmId : listIdFilms) {
                        String image = listImages.get(count).attr("src");
                        String filmName = listFilms.get(count).select("div.show_time h3").text();
                        String[] listTimesOfFilm = listFilms.get(count).select("div.show_time ul li").text().split(" ");
                        FilmBean filmBean = new FilmBean(Integer.parseInt(filmId), filmName,
                                image, random.nextInt(30)+5, cinemaName, cityName);
                        Film film = filmBean.convertToFilm(filmBean);
                        iFilmDAO.save(film);
                        for (String time : listTimesOfFilm) {
                            Schedule schedule = new Schedule(Helpers.convertDatetoString(new Date()), time, film);
                            iScheduleDAO.save(schedule);
                        }
                        count++;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("Exception at function cloneData in IFilmServiceImpl : ", e);
            throw e;
        }
    }

    @Override
    public List<FilmBean> list(Integer offset, Integer maxResults, String cityName, String today) {
        try {
            List<Film> listFilms = iFilmDAO.list(offset, maxResults, cityName);
            List<Schedule> listSchedules = iScheduleDAO.findScheduleToday(today);
            if(!Helpers.isEmpty(listFilms)) {
                List<FilmBean> listFilmBeans = new ArrayList<FilmBean>();
                for(Film film : listFilms) {
                    FilmBean filmBean = new FilmBean();
                    BeanUtils.copyProperties(film, filmBean);
                    for(Schedule schedule : listSchedules) {
                        if (schedule.getFilm().getId().equals(filmBean.getId())) {
                            filmBean.addSchedule(schedule.getTime());
                        }
                    }
                    listFilmBeans.add(filmBean);
                }
                return listFilmBeans;
            }
        } catch (Exception e) {
            logger.error("Exception at function list in class IFilmServiceImpl  : ", e);
        }
        return null;
    }

    @Override
    public Long count() {
        return iFilmDAO.count();
    }
}
