package dev.cmartin.learn.generics;

public class ServiceUnavailableException
        extends RuntimeException {

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(Exception e) {
        super(e);
    }
}
