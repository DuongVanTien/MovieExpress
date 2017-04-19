package vn.framgia.controller;

import vn.framgia.service.IFilmService;

/**
 * Created by FRAMGIA\duong.van.tien on 19/04/2017.
 */
public class ThreadCloneData extends Thread{

    private IFilmService iFilmService;

    private String cityName;

    public ThreadCloneData(String cityName, IFilmService iFilmService) {
        this.cityName = cityName;
        this.iFilmService = iFilmService;
    }

    @Override
    public void run() {
        try {
            iFilmService.cloneData(cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
