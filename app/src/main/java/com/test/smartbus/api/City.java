
package com.test.smartbus.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("highlight")
    @Expose
    private Integer highlight;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getHighlight() {
        return highlight;
    }

    public void setHighlight(Integer highlight) {
        this.highlight = highlight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public City(int id, int highlight, String name){
        setId(id);
        setHighlight(highlight);
        setName(name);
    }*/

}
