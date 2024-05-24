package bpos.server.service.Implementation;

import bpos.common.model.Center;
import bpos.common.model.LogInfo;
import bpos.server.repository.Interfaces.CenterRepository;
import bpos.server.repository.Interfaces.LogInfoRepository;
import bpos.server.service.IObserver;
import bpos.server.service.Interface.ICenterActor;
import bpos.server.service.ServicesExceptions;
import bpos.server.service.exceptions.UserNotLoggedInException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CenterActorService implements ICenterActor {
    private CenterRepository centerRepository;
    private LogInfoRepository dbLogInfo;
    private final ConcurrentHashMap<String, Boolean> loggedCenter = new ConcurrentHashMap<>();

    public CenterActorService(CenterRepository centerRepository, LogInfoRepository dbLogInfo) {
        this.centerRepository = centerRepository;
        this.dbLogInfo = dbLogInfo;

    }


    @Override
    public synchronized Optional<Center> loginCenter(LogInfo logInfo ) throws ServicesExceptions {
        if(dbLogInfo.findByUsername(logInfo.getUsername())==null)
        {
            throw new ServicesExceptions("Username does not exist");
        }
        Center center = centerRepository.findByUsername(logInfo.getUsername());
        Center center1=centerRepository.findByEmail(logInfo.getEmail());
        if(center!=null && center.equals(center1)) {
            if (loggedCenter.get(center.getLogInfo().getUsername()) != null) {
                throw new ServicesExceptions("User already logged in.");
            }
        }else{
            throw new ServicesExceptions("Authentication failed.");
        }

        loggedCenter.put(center.getLogInfo().getUsername(),true);
        return Optional.of(center);
    }




    @Override
    public void logoutCenter(Center center) throws ServicesExceptions ,UserNotLoggedInException{
        String username = center.getLogInfo().getUsername();
        // Check if the user is not logged in
        if (!loggedCenter.containsKey(username) || !loggedCenter.get(username)) {
            throw new UserNotLoggedInException("User is not logged in");
        }
        // Log out the user
        loggedCenter.put(username, false);
    }

    @Override
    public Center findByUsernameCenter(String username) throws ServicesExceptions {
        return centerRepository.findByUsername(username);
    }

    @Override
    public Center findByEmailCenter(String email) throws ServicesExceptions {
        return centerRepository.findByEmail(email);
    }

    @Override
    public Iterable<Center> findByNameCenter(String name) throws ServicesExceptions {
        return centerRepository.findByName(name);
    }

    @Override
    public Optional<Center> findOneCenter(Integer integer) throws ServicesExceptions {
        return centerRepository.findOne(integer);
    }

    @Override
    public Iterable<Center> findAllCenters() throws ServicesExceptions {
        return centerRepository.findAll();
    }

    @Override
    public Optional<Center> saveCenter(Center entity) throws ServicesExceptions {
        return centerRepository.save(entity);
    }

    @Override
    public Optional<Center> deleteCenter(Center entity) throws ServicesExceptions {
        return centerRepository.delete(entity);
    }

    @Override
    public Optional<Center> updateCenter(Center entity) throws ServicesExceptions {
        return centerRepository.update(entity);
    }
}
