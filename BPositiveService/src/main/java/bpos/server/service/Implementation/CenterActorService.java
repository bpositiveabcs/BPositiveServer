package bpos.server.service.Implementation;

import bpos.common.model.Center;
import bpos.common.model.LogInfo;
import bpos.server.repository.Interfaces.CenterRepository;
import bpos.server.repository.Interfaces.LogInfoRepository;
import bpos.server.service.IObserver;
import bpos.server.service.Interface.ICenterActor;
import bpos.server.service.ServicesExceptions;

import java.util.Map;
import java.util.Optional;

public class CenterActorService implements ICenterActor {
    private CenterRepository centerRepository;
    private LogInfoRepository dbLogInfo;
    private Map<Integer,IObserver> loggedCenter;

    public CenterActorService(CenterRepository centerRepository, LogInfoRepository dbLogInfo) {
        this.centerRepository = centerRepository;
        this.dbLogInfo = dbLogInfo;
        this.loggedCenter = loggedCenter;
    }


    @Override
    public synchronized Optional<Center> loginCenter(LogInfo logInfo , IObserver observer) throws ServicesExceptions {
        if(dbLogInfo.findByUsername(logInfo.getUsername())==null)
        {
            throw new ServicesExceptions("Username does not exist");
        }
        Center center = centerRepository.findByUsername(logInfo.getUsername());
        Center center1=centerRepository.findByEmail(logInfo.getEmail());
        if(center!=null && center.equals(center1)) {
            if (loggedCenter.get(center.getId()) != null) {
                throw new ServicesExceptions("User already logged in.");
            }
        }else{
            throw new ServicesExceptions("Authentication failed.");
        }

        loggedCenter.put(center.getId(),observer);
        notifyLogInCenter(center);
        return Optional.of(center);
    }
    private void notifyLogInCenter(Center center) {
        Iterable<Center> centerIterable=centerRepository.findAll();
        centerIterable.forEach(center1 -> {
            IObserver client=loggedCenter.get(center1.getId());
            if(client!=null){
                try {
                    client.loginCenterEvent(center);
                } catch (ServicesExceptions e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void logoutCenter(Center center, IObserver observer) throws ServicesExceptions {
        IObserver localClient=loggedCenter.remove(center.getId());
        if (localClient==null)
            throw new ServicesExceptions("User "+center+" is not logged in.");
        notifyLogOutCenter(center);
    }
    private void notifyLogOutCenter(Center center) {
        Iterable<Center> centerIterable=centerRepository.findAll();
        centerIterable.forEach(center1 -> {
            IObserver client=loggedCenter.get(center1.getId());
            if(client!=null){
                try {
                    client.logoutCenterEvent(center);
                } catch (ServicesExceptions e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
