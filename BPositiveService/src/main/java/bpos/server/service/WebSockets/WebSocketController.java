package bpos.server.service.WebSockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class WebSocketController {

    @Autowired
    private NotificationService notificationService;

//    @MessageMapping("/message")
//    @SendTo("/topic/client")
//    public String handleMessageClient(String message) {
//        return "Server received: " + message;
//    }

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
