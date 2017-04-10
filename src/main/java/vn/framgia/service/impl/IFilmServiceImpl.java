package vn.framgia.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.framgia.bean.FilmBean;
import vn.framgia.model.Film;
import vn.framgia.model.Schedule;
import vn.framgia.service.IFilmService;

import java.util.Date;
import java.util.Random;

/**
 * Created by FRAMGIA\duong.van.tien on 10/04/2017.
 */
@Service
@Transactional
public class IFilmServiceImpl extends BaseserviceImpl implements IFilmService {

    private static final Logger logger = Logger.getLogger(CityServiceImpl.class);

    public Session getSession() {
        return iFilmDAO.getSession();
    }

    @Override
    public boolean addFilm(FilmBean filmBean, Session session) {
        try {
            Film film = filmBean.convertToFilm(filmBean);
            iFilmDAO.save(film, session);
            return true;
        } catch (Exception e) {
            logger.error("Exception in function addItem : ", e);
        }
        return false;
    }

    @Override
    public boolean addFilm(Elements listCinemasName, Elements listFilms, Elements listImages, String cityName) {
        try {
            Random rd = new Random();
            int count = 0;
            Session session = getSession();
            for (int i = 0; i < listCinemasName.size(); i++) {
                String cinemaName = listCinemasName.get(i).text();
                String listIdsFilmOfCinema = listCinemasName.get(i).attr("rel");
                String[] listIdFilms = listIdsFilmOfCinema.split(",");
                for (String filmId : listIdFilms) {
                    String image = listImages.get(count).attr("src");
                    String filmName = listFilms.get(count).select("div.show_time h3").text();
                    FilmBean filmBean = new FilmBean(filmId, filmName, image, rd.nextInt(10), cinemaName, cityName );
                    Film film = filmBean.convertToFilm(filmBean);
                    iFilmDAO.save(film, session);
                    String[] listTimesOfFilm = listFilms.get(count).select("div.show_time ul li").text().split(" ");
                    for (String time : listTimesOfFilm) {
                        Schedule schedule = new Schedule(new Date(), time, filmId);
                        iScheduleDAO.save(schedule, session);
                    }
                    count++;
                }
            }
            session.close();
        } catch (Exception e) {
            logger.error("Exception in function addFilm : ", e);
        }
        return false;
    }
}
