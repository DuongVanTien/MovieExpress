package vn.framgia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import vn.framgia.model.City;
import vn.framgia.service.ICityService;
import vn.framgia.service.IFilmService;
import vn.framgia.ulti.Constant;
import java.util.List;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by FRAMGIA\duong.van.tien on 10/04/2017.
 */
@Controller
public class CloneDataController {

    @Autowired
    private ICityService iCityService;

    @Autowired
    private IFilmService iFilmService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    LocaleResolver localeResolver;

    @RequestMapping(value= Constant.URI_CLONE_DATA)
    public String cloneData(Model model) {
        try {
            if (iCityService.findAllCities() == null) {
                model.addAttribute("controller.error", "controller.error_message");
            }
            List<City> listCities = iCityService.findAllCities();
            model.addAttribute("listCities", listCities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping(value= Constant.URI_CLONE_DATA+"/{cityName}")
    public String cloneDataByCity(Model model, @PathVariable(value = "cityName") String cityName) {
        try {
            if (!iFilmService.cloneData(cityName))
                return "false";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    public String getMessage(String key, HttpServletRequest request) {
        return messageSource.getMessage(key, null, localeResolver.resolveLocale(request));
    }
}
