package vn.framgia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.framgia.dao.*;
import java.io.Serializable;
/**
 * Created by FRAMGIA\duong.van.tien on 06/03/2017.
 *
 */
@Service
public class BaseserviceImpl implements Serializable {

    @Autowired
    protected ICityDAO iCityDAO;

    @Autowired
    protected IFilmDAO iFilmDAO;

    @Autowired
    protected IScheduleDAO iScheduleDAO;
}
