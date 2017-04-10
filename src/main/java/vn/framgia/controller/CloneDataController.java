package vn.framgia.controller;

import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.framgia.bean.FilmBean;
import vn.framgia.model.City;
import vn.framgia.model.Film;
import vn.framgia.service.ICityService;
import vn.framgia.service.IFilmService;
import vn.framgia.ulti.Constant;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by FRAMGIA\duong.van.tien on 10/04/2017.
 */
@Controller
public class CloneDataController {

    @Autowired
    private ICityService iCityService;

    @Autowired
    private IFilmService iFilmService;

    @RequestMapping(value= Constant.URI_CLONE_DATA)
    public String cloneData(Model model) {
        try {
            if (iCityService.findAllCities() == null) {
                model.addAttribute("error", "list city empty");
            }
            List<City> listCities = iCityService.findAllCities();
            model.addAttribute("listCities", listCities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping(value= Constant.URI_CLONE_DATA+"/{cityName}")
    public String cloneDataByCity(@PathVariable(value = "cityName") String cityName) {
        try {
            Document document = Jsoup.connect(Constant.URL_ORIGIN+cityName)
                    .userAgent(Constant.USERAGENT).get();
            Elements listCinemasName = document.select("div[id=content_cinema] h2[class=title-cine]");
            Elements listFilms = document.select("div[class=film clearfix]");
            Elements listImages = listFilms.select("img");
            iFilmService.addFilm(listCinemasName, listFilms, listImages, cityName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "User succesfully saved!";
    }
}
