package vn.framgia.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by FRAMGIA\duong.van.tien on 10/04/2017.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tblSchedule")
public class Schedule implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "day")
    private Date day;

    @Column(name = "time")
    private String time;

    @Column(name = "filmId")
    private String filmId;

    public Schedule(Date day, String time, String filmId) {
        this.day = day;
        this.time = time;
        this.filmId = filmId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }
}
