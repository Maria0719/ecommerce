package co.edu.usbcali.ecommerceusb.controller;

// Excepción para validaciones de solicitud (400)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
