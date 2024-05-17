package bpos.server;

import bpos.common.model.LogInfo;
import bpos.server.service.Interface.ILogInfoService;
import bpos.server.service.ServicesExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController("/log-infos")
public class LogInfoController {
    private ILogInfoService service;

    public LogInfoController(ILogInfoService service) {
        this.service = service;
    }

    @GetMapping("/log-infos/{id}")
    public ResponseEntity<?> findOneLogInfo(@PathVariable Integer id) {
        try {
            Optional<LogInfo> logInfo = service.findOneLogInfo(id);
            return logInfo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/log-infos")
    public ResponseEntity<?> findAllLogInfos() {
        try {
            Iterable<LogInfo> logInfos = service.findAllLogInfos();
            return new ResponseEntity<>(logInfos, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/log-infos/username")
    public ResponseEntity<?> findByUsernameLogInfo(@RequestParam(value="username",required = true) String username) {
        try {
            LogInfo logInfo = service.findByUsernameLogInfo(username);
            return new ResponseEntity<>(logInfo, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/log-infos/email")
    public ResponseEntity<?> findByEmailLogInfo(@RequestParam(value="email",required = true) String email) {
        try {
            LogInfo logInfo = service.findByEmailLogInfo(email);
            return new ResponseEntity<>(logInfo, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/log-infos")
    public ResponseEntity<?> updateLogInfo(@RequestBody LogInfo entity) {
        try {
            Optional<LogInfo> updatedInfo = service.updateLogInfo(entity);
            if (updatedInfo.isPresent()) {
                return new ResponseEntity<>(updatedInfo.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/log-infos")
    public ResponseEntity<?> deleteLogInfo(LogInfo entity) {
        try {
            Optional<LogInfo> deleted = service.deleteLogInfo(entity);
            if (deleted.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/log-infos")
    public ResponseEntity<LogInfo> saveLogInfo(@RequestBody LogInfo logInfo) {
        try {
            Optional<LogInfo> savedLogInfo = service.saveLogInfo(logInfo);
            if(savedLogInfo.isPresent()) {
                return new ResponseEntity<>(savedLogInfo.get(), HttpStatus.CREATED);
            }
        }
        catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
