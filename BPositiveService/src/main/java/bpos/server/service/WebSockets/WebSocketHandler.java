//package bpos.server.service.WebSockets;
//
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//public class WebSocketHandler extends TextWebSocketHandler {
//
//    private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        sessions.add(session);
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        sessions.remove(session);
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        for (WebSocketSession webSocketSession : sessions) {
//            webSocketSession.sendMessage(message);
//        }
//    }
//
//    public void sendMessageToAll(String message) throws Exception {
//        for (WebSocketSession session : sessions) {
//            session.sendMessage(new TextMessage(message));
//        }
//    }
//}
