package bpos.server.service.Implementation;

import bpos.common.model.LogInfo;
import bpos.server.repository.Interfaces.LogInfoRepository;
import bpos.server.service.Interface.ILogInfoService;
import bpos.server.service.ServicesExceptions;
import bpos.server.service.WebSockets.NotificationService;

import java.util.Optional;

public class LogInfoService implements ILogInfoService {
    private LogInfoRepository logInfoRepository;
    private final NotificationService notificationService;


    public LogInfoService(LogInfoRepository logInfoRepository, NotificationService notificationService) {
        this.logInfoRepository = logInfoRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Optional<LogInfo> findOneLogInfo(Integer integer) throws ServicesExceptions {
        return logInfoRepository.findOne(integer);
    }

    @Override
    public Iterable<LogInfo> findAllLogInfos() throws ServicesExceptions {
        return logInfoRepository.findAll();
    }

    @Override
    public Optional<LogInfo> saveLogInfo(LogInfo entity) throws ServicesExceptions {
        return logInfoRepository.save(entity);
    }

    @Override
    public Optional<LogInfo> deleteLogInfo(LogInfo entity) throws ServicesExceptions {
        return logInfoRepository.delete(entity);
    }

    @Override
    public Optional<LogInfo> updateLogInfo(LogInfo entity) throws ServicesExceptions {
        return logInfoRepository.update(entity);
    }

    @Override
    public LogInfo findByUsernameLogInfo(String username) throws ServicesExceptions {
        return logInfoRepository.findByUsername(username);
    }

    @Override
    public LogInfo findByEmailLogInfo(String email) throws ServicesExceptions {
        return logInfoRepository.findByEmail(email);
    }
}
