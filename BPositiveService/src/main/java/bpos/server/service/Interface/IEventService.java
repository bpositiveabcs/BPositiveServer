package bpos.server.service.Interface;

import bpos.common.model.Coupon;
import bpos.common.model.Event;
import bpos.common.model.RetrievedCoupons;
import bpos.server.service.ServicesExceptions;

import java.time.LocalDate;
import java.util.Optional;

public interface IEventService {
    Iterable<Event> findByNameEvent(String nume) throws ServicesExceptions;
    Iterable<Event> findByAnnouncementDateEvent(LocalDate data) throws ServicesExceptions;
    Iterable<Event> findByCenterIdEvent(Integer centruId) throws ServicesExceptions;
    Iterable<Event> findByDataInceputEvent(LocalDate data) throws ServicesExceptions;

    Optional<Event> findOneEvent(Integer integer) throws ServicesExceptions;
    Iterable<Event> findAllEvents() throws ServicesExceptions;
    Optional<Event> saveEvent(Event entity) throws ServicesExceptions;
    Optional<Event> deleteEvent(Event entity) throws ServicesExceptions;
    Optional<Event> updateEvent(Event entity) throws ServicesExceptions;
    Iterable<Coupon> findByCodeCoupon(String code) throws ServicesExceptions;
    Iterable<Coupon> findByProviderCoupon(String provider) throws ServicesExceptions;
    Iterable<Coupon> findByNameCoupon(String name) throws ServicesExceptions;
    Iterable<Coupon> findByEndDateCoupon(LocalDate endDate) throws ServicesExceptions;
    Optional<Coupon> findOneCoupon(Integer integer) throws ServicesExceptions;
    Iterable<Coupon> findAllCoupons() throws ServicesExceptions;
    Optional<Coupon> saveCoupon(Coupon entity) throws ServicesExceptions;
    Optional<Coupon> deleteCoupon(Coupon entity) throws ServicesExceptions;
    Optional<Coupon> updateCoupon(Coupon entity) throws ServicesExceptions;
    Optional<RetrievedCoupons> findOneRetrieved(Integer integer) throws ServicesExceptions;
    Iterable<RetrievedCoupons> findAllRetrieved() throws ServicesExceptions;
    Optional<RetrievedCoupons> saveRetrieved(RetrievedCoupons entity) throws ServicesExceptions;
    Optional<RetrievedCoupons> deleteRetrieved(RetrievedCoupons entity) throws ServicesExceptions;
    Optional<RetrievedCoupons> updateRetrieved(RetrievedCoupons entity) throws ServicesExceptions;
    Iterable<RetrievedCoupons> findByCouponIdRetrieved(Integer couponId) throws ServicesExceptions;
    Iterable<RetrievedCoupons> findByPersonIdRetrieved(Integer personId) throws ServicesExceptions;
    Iterable<RetrievedCoupons> findByDateRetrieved(String date) throws ServicesExceptions;
}
