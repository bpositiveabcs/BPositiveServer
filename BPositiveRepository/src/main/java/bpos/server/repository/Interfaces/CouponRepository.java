package bpos.repository.Interfaces;

import bpos.model.Coupon;

import java.time.LocalDate;

public interface CouponRepository extends IRepository<Integer, Coupon>{
    Iterable<Coupon> findByCodeCoupon(String code);
    Iterable<Coupon> findByProvider(String provider);
    Iterable<Coupon> findByNume(String nume);
    Iterable<Coupon> findByEndDate(LocalDate endDate);

}
