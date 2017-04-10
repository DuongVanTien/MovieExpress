package vn.framgia.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.framgia.model.City;
import vn.framgia.service.ICityService;

import java.util.List;

@Service
@Transactional
public class CityServiceImpl extends BaseserviceImpl implements ICityService {

    private static final Logger logger = Logger.getLogger(CityServiceImpl.class);

    @Override
    public List<City> findAllCities() {
        try {
            return iCityDAO.findAll();
        } catch (Exception e) {
            logger.error("Exception at function findAllCities in class CityServiceImpl ", e);
        }
        return null;
    }
}
