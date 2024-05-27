package bpos.server;

import bpos.server.service.WebSockets.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final NotificationService notificationService;

    @Autowired
    public WebSocketController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/clients")
    public void sendNotificationClients(@Payload String message) {
        notificationService.notifyClient(message);
    }

    @MessageMapping("/centers")
    public void sendNotificationCenters(@Payload String message) {
        notificationService.notifyCenter(message);
    }

    @MessageMapping("/admins")
    public void sendNotificationAdmins(@Payload String message) {
        notificationService.notifyAdmins(message);
    }
}
