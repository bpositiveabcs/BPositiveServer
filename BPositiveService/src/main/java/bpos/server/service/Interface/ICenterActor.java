package bpos.server.service.Interface;

import bpos.common.model.Center;
import bpos.common.model.LogInfo;
import bpos.server.service.IObserver;
import bpos.server.service.ServicesExceptions;
import bpos.server.service.exceptions.UserNotLoggedInException;

import java.util.Optional;

public interface ICenterActor {
    Optional<Center> loginCenter(LogInfo logInfo) throws ServicesExceptions;

    void  logoutCenter(Center center) throws ServicesExceptions, UserNotLoggedInException;

    Center findByUsernameCenter(String username) throws ServicesExceptions;
    Center findByEmailCenter(String email) throws ServicesExceptions;
    Iterable<Center> findByNameCenter(String name) throws ServicesExceptions;
    Optional<Center> findOneCenter(Integer integer) throws ServicesExceptions;
    Iterable<Center> findAllCenters() throws ServicesExceptions;
    Optional<Center> saveCenter(Center entity) throws ServicesExceptions;
    Optional<Center> deleteCenter(Center entity) throws ServicesExceptions;
    Optional<Center> updateCenter(Center entity) throws ServicesExceptions;

}
