package com.test.smartbus.dbase;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Viktor Derk on 2/17/17.
 */

public class CityDetails {

    // Primary key
    @DatabaseField(id = true, columnName = "_id")
    private int cityId;

    @DatabaseField( columnName = "city_name")
    private String cityName;

    public CityDetails(){}

    public CityDetails(int cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }
}
