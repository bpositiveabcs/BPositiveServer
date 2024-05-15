package bpos.server.service;

public class ServicesExceptions extends  Exception{
    public ServicesExceptions() {
    }

    public ServicesExceptions(String message) {
        super(message);
    }

    public ServicesExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
