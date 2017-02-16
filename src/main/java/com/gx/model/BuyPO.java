package com.gx.model;

/**
 * Created by Administrator on 2017/2/7.
 */
public class BuyPO {
    private Integer id;
    private Integer number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "BuyPO{" +
                "id=" + id +
                ", number=" + number +
                '}';
    }
}
