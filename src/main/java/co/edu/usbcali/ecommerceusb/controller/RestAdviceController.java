package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.dto.ApiResponse;
import co.edu.usbcali.ecommerceusb.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

// Controlador global para manejar excepciones en toda la aplicación
@RestControllerAdvice
public class RestAdviceController {

    // 200 OK
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex, WebRequest request) {
        ErrorDetails error = ErrorDetails.builder()
                .detail(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(200)
                .message("Success")
                .error(error)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 201 Created
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        ErrorDetails error = ErrorDetails.builder()
                .detail(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(201)
                .message("Created")
                .error(error)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 400 Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequestException(
            BadRequestException ex, WebRequest request) {
        ErrorDetails error = ErrorDetails.builder()
                .detail(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(400)
                .message("Bad Request")
                .error(error)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 404 Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails error = ErrorDetails.builder()
                .detail(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(404)
                .message("Not Found")
                .error(error)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 401 Unauthorized
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(
            UnauthorizedException ex, WebRequest request) {
        ErrorDetails error = ErrorDetails.builder()
                .detail(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(401)
                .message("Unauthorized")
                .error(error)
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // 403 Forbidden
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse<Void>> handleForbiddenException(
            ForbiddenException ex, WebRequest request) {
        ErrorDetails error = ErrorDetails.builder()
                .detail(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(403)
                .message("Forbidden")
                .error(error)
                .build();

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // 409 Conflict
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<Void>> handleConflictException(
            ConflictException ex, WebRequest request) {
        ErrorDetails error = ErrorDetails.builder()
                .detail(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(409)
                .message("Conflict")
                .error(error)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 505 Internal Server Error
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ApiResponse<Void>> handleInternalServerErrorException(
            InternalServerErrorException ex, WebRequest request) {
        ErrorDetails error = ErrorDetails.builder()
                .detail(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(505)
                .message("Internal Server Error")
                .error(error)
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
