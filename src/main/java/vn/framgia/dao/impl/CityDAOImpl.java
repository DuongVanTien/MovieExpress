package vn.framgia.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import vn.framgia.dao.ICityDAO;
import vn.framgia.model.City;

/**
 * Created by FRAMGIA\duong.van.tien on 10/04/2017.
 */
@Repository
@EnableTransactionManagement
public class CityDAOImpl extends GenericDAO<City, Integer> implements ICityDAO {
    public CityDAOImpl() {
        super.setPersistentClass(City.class);
    }
}
