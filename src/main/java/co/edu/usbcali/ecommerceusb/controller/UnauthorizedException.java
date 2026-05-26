package co.edu.usbcali.ecommerceusb.controller;

// Excepción para usuario no autenticado (401)
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
