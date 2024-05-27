package bpos.server.service.WebSockets;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        System.out.println("NotificationService initialized with messagingTemplate: " + messagingTemplate);
    }

    public void notifyCenter(String message) {
        messagingTemplate.convertAndSend("/topic/centers", message);
    }

    public void notifyAdmins(String message) {
        messagingTemplate.convertAndSend("/topic/admins", message);
    }

    public void notifyClient(String message) {
        messagingTemplate.convertAndSend("/topic/clients", message);
    }
}
