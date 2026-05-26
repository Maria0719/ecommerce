package co.edu.usbcali.ecommerceusb.controller;

// Excepción para errores internos del servidor (505)
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
