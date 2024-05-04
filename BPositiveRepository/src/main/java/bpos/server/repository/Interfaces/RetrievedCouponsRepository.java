package bpos.server.repository.Interfaces;


import bpos.common.model.RetrievedCoupons;

public interface RetrievedCouponsRepository extends IRepository<Integer, RetrievedCoupons>
{
    Iterable<RetrievedCoupons> findByPersonId(int personId);
    Iterable<RetrievedCoupons> findByCouponId(int couponId);
    Iterable<RetrievedCoupons> findByDate(String date);

}
