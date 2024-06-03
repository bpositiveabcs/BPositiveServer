package bpos.server.service.WebSockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final NotificationService notificationService;

    public WebSocketController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/clients")
    @SendTo("/topic/clients")
    public void sendNotificationClients(String message) {
        notificationService.notifyClient(message);
    }

    @MessageMapping("/centers")
    @SendTo("/topic/centers")
    public void sendNotificationCenters(String message) {
        notificationService.notifyCenter(message);
    }

    @MessageMapping("/admins")
    @SendTo("/topic/admins")
    public void sendNotificationAdmins(String message) {
        notificationService.notifyAdmins(message);
    }
}
