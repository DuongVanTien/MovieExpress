package vn.framgia.dao.impl;

import org.springframework.stereotype.Repository;
import vn.framgia.dao.IScheduleDAO;
import vn.framgia.model.Schedule;

/**
 * Created by FRAMGIA\duong.van.tien on 10/04/2017.
 */
@Repository
public class ScheduleDAOImpl extends GenericDAO<Schedule, Integer> implements IScheduleDAO {
}
