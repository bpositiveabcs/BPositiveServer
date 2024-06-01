package bpos.server.service.WebSockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ClientWebSocketHandler clientWebSocketHandler;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate, ClientWebSocketHandler clientWebSocketHandler) {
        this.messagingTemplate = messagingTemplate;
        this.clientWebSocketHandler = clientWebSocketHandler;
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

    public void notifyAllClients(String message) {
        try {
            clientWebSocketHandler.sendMessageToAll(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
