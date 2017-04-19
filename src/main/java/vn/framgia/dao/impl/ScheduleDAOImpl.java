package vn.framgia.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import vn.framgia.dao.IScheduleDAO;
import vn.framgia.model.Schedule;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;

/**
 * Created by FRAMGIA\duong.van.tien on 10/04/2017.
 */
@Repository
public class ScheduleDAOImpl extends GenericDAO<Schedule, Integer> implements IScheduleDAO {
    public ScheduleDAOImpl() {
        super.setPersistentClass(Schedule.class);
    }

    @Override
    public List<Schedule> findScheduleToday(String today) {
        Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Schedule.class);
        return criteria.add(Restrictions.like("day", "%"+today+"%")).list();
    }

    @Override
    public List<String> findFilmIdByDate(String day, String cityName) {
        Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(this.getPersistentClass(), "schedule");
        criteria.createAlias("schedule.film","film");
        criteria.add(Restrictions.eq("day", day));
        criteria.add(Restrictions.eq("film.cityName", cityName));
        criteria.setProjection(Projections.groupProperty("film.id"));
        return criteria.list();
    }

    @Override
    public boolean deleteScheduleByFilmId(Integer filmId) {
        String hql = "delete from Schedule where filmId = :filmId";
        Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("filmId", filmId);
        if (query == null)
            return false;
        query.executeUpdate();
        return true;
    }
}
