package bpos.server.service.WebSockets;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService( SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyCenter(String message) {
        messagingTemplate.convertAndSend("/topic/centers", message);
        logger.info("Sent message to centers: {}", message);
    }

    public void notifyAdmins(String message) {
        messagingTemplate.convertAndSend("/topic/admins", message);
        logger.info("Sent message to admins: {}", message);
    }

    public void notifyClient(String message) {
        messagingTemplate.convertAndSend("/topic/clients", message);
        logger.info("Sent message to clients: {}", message);
    }

    public void notifyClientWithSessionId(String sessionId, String message) {
        MessageHeaders headers = new MessageHeaders(Collections.singletonMap("simpSessionId", sessionId));
        messagingTemplate.send("/topic/clients", MessageBuilder.createMessage(message, headers));
        logger.info("Sent message to session {}: {}", sessionId, message);
    }
}
