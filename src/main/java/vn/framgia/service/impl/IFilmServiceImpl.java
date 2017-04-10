package vn.framgia.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.framgia.bean.FilmBean;
import vn.framgia.model.Film;
import vn.framgia.model.Schedule;
import vn.framgia.service.IFilmService;
import vn.framgia.ulti.Constant;
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
    public boolean cloneData(String cityName) {
        try {
            String urlCloneData = Constant.URL_ORIGIN+cityName;
            Document document = Jsoup.connect(urlCloneData)
                    .userAgent(Constant.USERAGENT).get();
            Elements listCinemasName = document.select("div[id=content_cinema] h2[class=title-cine]");
            Elements listFilms = document.select("div[class=film clearfix]");
            Elements listImages = listFilms.select("img");
            Random random = new Random();
            int count = 0;
            Session session = getSession();
            for (int i = 0; i < listCinemasName.size(); i++) {
                String cinemaName = listCinemasName.get(i).text();
                String listIdsFilmOfCinema = listCinemasName.get(i).attr("rel");
                String[] listIdFilms = listIdsFilmOfCinema.split(",");
                for (String filmId : listIdFilms) {
                    String image = listImages.get(count).attr("src");
                    String filmName = listFilms.get(count).select("div.show_time h3").text();
                    FilmBean filmBean = new FilmBean(filmId, filmName, image, random.nextInt(30)+5, cinemaName, cityName );
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
            return true;
        } catch (Exception e) {
            logger.error("Exception at function cloneData in IFilmServiceImpl : ", e);
        }
        return false;
    }
}
