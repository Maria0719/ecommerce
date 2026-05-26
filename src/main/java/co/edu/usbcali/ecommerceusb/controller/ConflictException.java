package co.edu.usbcali.ecommerceusb.controller;

// Excepción para conflicto de datos (409)
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
