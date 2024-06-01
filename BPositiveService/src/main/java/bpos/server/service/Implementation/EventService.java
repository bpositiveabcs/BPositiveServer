package bpos.server.service.Implementation;

import bpos.common.model.Coupon;
import bpos.common.model.Event;
import bpos.common.model.RetrievedCoupons;
import bpos.other.NotificationRest;
import bpos.server.repository.Interfaces.CouponRepository;
import bpos.server.repository.Interfaces.EventRepository;
import bpos.server.repository.Interfaces.RetrievedCouponsRepository;
import bpos.server.service.IObserver;
import bpos.server.service.Interface.IEventService;
import bpos.server.service.ServicesExceptions;
import bpos.server.service.WebSockets.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class EventService implements IEventService {
    private final NotificationService notificationService;

    private EventRepository eventRepository;
    private CouponRepository couponRepository;
    private RetrievedCouponsRepository retrievedCouponsRepository;


    //    private  UserDetailsService userDetailsService;
//    private  JwtTokenUtil jwtTokenUtil;
    private final ConcurrentHashMap<String, Boolean> loggedInUsers = new ConcurrentHashMap<>();
    private final Map<Integer, IObserver> loggedCenter = new ConcurrentHashMap<>();



    private ObjectMapper objectMapper;

    public EventService( EventRepository eventRepository, CouponRepository couponRepository, RetrievedCouponsRepository retrievedCouponsRepository, NotificationService notificationService, ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.couponRepository = couponRepository;
        this.retrievedCouponsRepository = retrievedCouponsRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Iterable<Event> findByNameEvent(String nume) throws ServicesExceptions {
        return eventRepository.findByNume(nume);
    }

    @Override
    public Iterable<Event> findByAnnouncementDateEvent(LocalDate data) throws ServicesExceptions {
        return eventRepository.findByDataAnunt(data);
    }

    @Override
    public Iterable<Event> findByCenterIdEvent(Integer centruId) throws ServicesExceptions {
        return eventRepository.findByCentruId(centruId);
    }

    @Override
    public Iterable<Event> findByDataInceputEvent(LocalDate data) throws ServicesExceptions {
        return eventRepository.findByDataInceput(data);
    }

    @Override
    public Optional<Event> findOneEvent(Integer integer) throws ServicesExceptions {
        return eventRepository.findOne(integer);
    }

    @Override
    public Iterable<Event> findAllEvents() throws ServicesExceptions {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> saveEvent(Event entity) throws ServicesExceptions {
        Optional<Event> event= eventRepository.save(entity);
        notificationService.notifyCenter(String.valueOf(NotificationRest.NEW_EVENT));
        return event;
    }

    @Override
    public Optional<Event> deleteEvent(Event entity) throws ServicesExceptions {
        Optional<Event> event= eventRepository.delete(entity);
        notificationService.notifyCenter(String.valueOf(NotificationRest.DENY_EVENT));
        return event;
    }

    @Override
    public Optional<Event> updateEvent(Event entity) throws ServicesExceptions {
        Optional<Event> updateEvent= eventRepository.update(entity);
//        notificationService.notifyCenter(String.valueOf(NotificationRest.NEW_EVENT));
        return updateEvent;
    }

    @Override
    public Iterable<Coupon> findByCodeCoupon(String code) throws ServicesExceptions {
        return couponRepository.findByCodeCoupon(code) ;
    }

    @Override
    public Iterable<Coupon> findByProviderCoupon(String provider) throws ServicesExceptions {
        return couponRepository.findByProvider(provider);
    }

    @Override
    public Iterable<Coupon> findByNameCoupon(String name) throws ServicesExceptions {
        return couponRepository.findByNume(name);
    }

    @Override
    public Iterable<Coupon> findByEndDateCoupon(LocalDate endDate) throws ServicesExceptions {
        return couponRepository.findByEndDate(endDate);
    }

    @Override
    public Optional<Coupon> findOneCoupon(Integer integer) throws ServicesExceptions {
        return couponRepository.findOne(integer);
    }

    @Override
    public Iterable<Coupon> findAllCoupons() throws ServicesExceptions {
        return couponRepository.findAll();
    }

    @Override
    public Optional<Coupon> saveCoupon(Coupon entity) throws ServicesExceptions {
        return couponRepository.save(entity);
    }

    @Override
    public Optional<Coupon> deleteCoupon(Coupon entity) throws ServicesExceptions {
        return couponRepository.delete(entity);
    }

    @Override
    public Optional<Coupon> updateCoupon(Coupon entity) throws ServicesExceptions {
        return couponRepository.update(entity);
    }

    @Override
    public Optional<RetrievedCoupons> findOneRetrieved(Integer integer) throws ServicesExceptions {
        return retrievedCouponsRepository.findOne(integer) ;
    }

    @Override
    public Iterable<RetrievedCoupons> findAllRetrieved() throws ServicesExceptions {
        return retrievedCouponsRepository.findAll();
    }

    @Override
    public Optional<RetrievedCoupons> saveRetrieved(RetrievedCoupons entity) throws ServicesExceptions {
        return retrievedCouponsRepository.save(entity);
    }

    @Override
    public Optional<RetrievedCoupons> deleteRetrieved(RetrievedCoupons entity) throws ServicesExceptions {
        return retrievedCouponsRepository.delete(entity);
    }

    @Override
    public Optional<RetrievedCoupons> updateRetrieved(RetrievedCoupons entity) throws ServicesExceptions {
        return retrievedCouponsRepository.update(entity);
    }

    @Override
    public Iterable<RetrievedCoupons> findByCouponIdRetrieved(Integer couponId) throws ServicesExceptions {
        return retrievedCouponsRepository.findByCouponId(couponId);
    }

    @Override
    public Iterable<RetrievedCoupons> findByPersonIdRetrieved(Integer personId) throws ServicesExceptions {
        return retrievedCouponsRepository.findByPersonId(personId);
    }

    @Override
    public Iterable<RetrievedCoupons> findByDateRetrieved(String date) throws ServicesExceptions {
        return retrievedCouponsRepository.findByDate(date);
    }
}
