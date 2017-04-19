package vn.framgia.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import vn.framgia.model.City;
import vn.framgia.service.ICityService;
import vn.framgia.service.IFilmService;
import vn.framgia.ulti.Constant;
import javax.servlet.http.HttpServletRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Created by FRAMGIA\duong.van.tien on 10/04/2017.
 */
@Controller
@Component
@EnableScheduling
public class CloneDataController {

    private static final Logger logger = Logger.getLogger(CloneDataController.class);

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
            logger.error(messageSource.getMessage("controller.error_message", null, null), e);
        }
        return "index";
    }

    @RequestMapping(value= Constant.URI_CLONE_DATA+"/{cityName}")
    public String cloneDataByCity(@PathVariable(value = "cityName") String cityName) {
        try {
            if (!iFilmService.cloneData(cityName))
                return "false";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    public String getMessageHttp(String key, HttpServletRequest request) {
        return messageSource.getMessage(key, null, localeResolver.resolveLocale(request));
    }

    @Scheduled(cron="0 02 14 * * *", zone="Asia/Saigon")
    public String scheduleCloneDataByCity() {
        List<String> listCities = Constant.listCities;

        for(int i=0; i<listCities.size(); ++i) {
            new ThreadCloneData(listCities.get(i), iFilmService).start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "index";
    }
}
