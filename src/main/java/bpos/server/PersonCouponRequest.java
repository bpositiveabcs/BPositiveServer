package bpos.server;

import bpos.common.model.Coupon;

import java.util.List;

public class PersonCouponRequest {
    private String username;
    private List<Coupon>couponList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PersonCouponRequest(String username, List<Coupon> couponList) {
        this.username = username;
        this.couponList = couponList;
    }


    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }
}
