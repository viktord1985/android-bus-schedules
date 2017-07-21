package com.test.smartbus.dbase;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created by Viktor Derk on 12/13/16.
 */

public class TripDetails {

    /**
     * Model class for trip_details database table
     */
    private static final long serialVersionUID = -222864131214757024L;

    public static final String ID_FIELD = "trip_id";
    public static final String DATE_FROM_FIELD = "from_date";

    // Primary key
    @DatabaseField(id = true, columnName = ID_FIELD)
    private int tripId;

    @DatabaseField( canBeNull = false, foreign = true, columnName = "from_city", foreignAutoRefresh = true)
    private CityDetails fromCity;

    @DatabaseField( columnName = "from_highlight")
    private int fromCityHighlight;
    
    @DatabaseField( columnName = DATE_FROM_FIELD)
    private Date fromDate;
    
    @DatabaseField( columnName = "from_info")
    private String fromInfo;
    
    @DatabaseField( canBeNull = false, foreign = true, columnName = "to_city", foreignAutoRefresh = true)
    private CityDetails toCity;

    @DatabaseField( columnName = "to_highlight")
    private int toCityHighlight;
    
    @DatabaseField( columnName = "to_date")
    private Date toDate;
    
    @DatabaseField( columnName = "to_info")
    private String toInfo;
    
    @DatabaseField( columnName = "info")
    private String info;
    
    @DatabaseField( columnName = "price")
    private int price;

    @DatabaseField( columnName = "bus_id")
    private int busId;
    
    @DatabaseField( columnName = "reservation_count")    
    private int reservationCount;

    // Default constructor is needed for the SQLite, so make sure you also have it
    public TripDetails(){

    }

    public TripDetails(int tripId, CityDetails fromCity, int fromCityHighlight, Date fromDate, String fromInfo,
                       CityDetails toCity, int toCityHighlight, Date toDate, String toInfo,
                       String info, int price, int busId, int reservationCount) {
        this.busId = busId;
        this.fromCity = fromCity;
        this.fromCityHighlight = fromCityHighlight;
        this.fromDate = fromDate;
        this.fromInfo = fromInfo;
        this.info = info;
        this.price = price;
        this.reservationCount = reservationCount;
        this.toCity = toCity;
        this.toCityHighlight = toCityHighlight;
        this.toDate = toDate;
        this.toInfo = toInfo;
        this.tripId = tripId;
    }

    public int getTripId() {
        return tripId;
    }

    public String getInfo() {
        return info;
    }

    public int getPrice() {
        return price;
    }

    public int getReservationCount() {
        return reservationCount;
    }

    public CityDetails getToCity() {
        return toCity;
    }

    public Date getToDate() {
        return toDate;
    }

    public String getToInfo() {
        return toInfo;
    }

    public String getFromInfo() {
        return fromInfo;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public CityDetails getFromCity() {
        return fromCity;
    }

    public int getBusId() {
        return busId;
    }

    public int getFromCityHighlight() {
        return fromCityHighlight;
    }

    public int getToCityHighlight() {
        return toCityHighlight;
    }
}
