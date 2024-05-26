package bpos.server;

import bpos.common.model.Coupon;

import java.util.List;

public class PersonCouponResponse {
    private List<Coupon> couponList;
    private String email;

    public PersonCouponResponse(List<Coupon> couponList, String email) {
        this.couponList = couponList;
        this.email = email;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
