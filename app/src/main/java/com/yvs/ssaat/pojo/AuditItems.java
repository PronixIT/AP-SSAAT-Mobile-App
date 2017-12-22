package com.yvs.ssaat.pojo;

/**
 * Created by surya on 9/11/2017.
 */

public class AuditItems {
    private String id;
    private String loc_name;
    private String loc_country;
    private String loc_state;
    private String loc_area;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoc_name() {
        return loc_name;
    }

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }

    public String getLoc_country() {
        return loc_country;
    }

    public void setLoc_country(String loc_country) {
        this.loc_country = loc_country;
    }

    public String getLoc_state() {
        return loc_state;
    }

    public void setLoc_state(String loc_state) {
        this.loc_state = loc_state;
    }

    public String getLoc_area() {
        return loc_area;
    }

    public void setLoc_area(String loc_area) {
        this.loc_area = loc_area;
    }

    public String getLoc_pincode() {
        return loc_pincode;
    }

    public void setLoc_pincode(String loc_pincode) {
        this.loc_pincode = loc_pincode;
    }

    private String loc_pincode;
}
