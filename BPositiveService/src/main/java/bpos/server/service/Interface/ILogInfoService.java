package bpos.server.service.Interface;

import bpos.common.model.LogInfo;
import bpos.server.service.ServicesExceptions;

import java.util.Optional;

public interface ILogInfoService {
    Optional<LogInfo> findOneLogInfo(Integer integer) throws ServicesExceptions;
    Iterable<LogInfo> findAllLogInfos() throws ServicesExceptions;
    Optional<LogInfo> saveLogInfo(LogInfo entity) throws ServicesExceptions;
    Optional<LogInfo> deleteLogInfo(LogInfo entity) throws ServicesExceptions;
    Optional<LogInfo> updateLogInfo(LogInfo entity) throws ServicesExceptions;
    LogInfo findByUsernameLogInfo(String username) throws ServicesExceptions;
    LogInfo findByEmailLogInfo(String email) throws ServicesExceptions;
}
