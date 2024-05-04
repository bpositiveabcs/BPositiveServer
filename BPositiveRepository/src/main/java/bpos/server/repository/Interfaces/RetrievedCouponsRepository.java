package bpos.repository.Interfaces;

import bpos.model.RetrievedCoupons;

public interface RetrievedCouponsRepository extends IRepository<Integer, RetrievedCoupons>
{
    Iterable<RetrievedCoupons> findByPersonId(int personId);
    Iterable<RetrievedCoupons> findByCouponId(int couponId);
    Iterable<RetrievedCoupons> findByDate(String date);

}
