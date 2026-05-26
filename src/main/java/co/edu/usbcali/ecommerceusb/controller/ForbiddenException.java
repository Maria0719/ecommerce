package co.edu.usbcali.ecommerceusb.controller;

// Excepción para acceso prohibido sin permisos (403)
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
