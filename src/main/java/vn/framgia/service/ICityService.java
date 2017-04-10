package vn.framgia.service;

import vn.framgia.model.City;
import java.io.Serializable;
import java.util.List;

public interface ICityService extends Serializable {
    public List<City> findAllCities();
}
