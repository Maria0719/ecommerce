package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreatePaymentRequest;
import co.edu.usbcali.ecommerceusb.dto.PaymentResponse;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Payment;

import java.sql.Timestamp;
import java.util.List;

public class PaymentMapper {

    public static PaymentResponse modelToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrder() != null ? payment.getOrder().getId() : null)
                .status(payment.getStatus())
                .providerRef(payment.getProviderRef())
                .idempotencyKey(payment.getIdempotencyKey())
                .build();
    }

    public static List<PaymentResponse> modelToPaymentResponseList(List<Payment> payments) {
        return payments.stream().map(PaymentMapper::modelToPaymentResponse).toList();
    }

    public static Payment createPaymentRequestToPayment(CreatePaymentRequest request, Order order) {
        return Payment.builder()
                .order(order)
                .status(request.getStatus())
                .providerRef(request.getProviderRef())
                .idempotencyKey(request.getIdempotencyKey())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
