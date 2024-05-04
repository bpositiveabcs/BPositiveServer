package bpos.repository.Interfaces;

import bpos.model.LogInfo;

public interface LogInfoRepository extends IRepository<Integer, LogInfo>{
    LogInfo findByUsername(String username);
    LogInfo findByEmail(String email);
}
