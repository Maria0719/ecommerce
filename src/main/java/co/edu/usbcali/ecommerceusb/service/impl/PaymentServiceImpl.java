package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreatePaymentRequest;
import co.edu.usbcali.ecommerceusb.dto.PaymentResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdatePaymentRequest;
import co.edu.usbcali.ecommerceusb.mapper.PaymentMapper;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Payment;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.PaymentRepository;
import co.edu.usbcali.ecommerceusb.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<PaymentResponse> getPayments() {
        List<Payment> payments = paymentRepository.findAll();
        if (payments.isEmpty()) return List.of();
        return PaymentMapper.modelToPaymentResponseList(payments);
    }

    @Override
    public PaymentResponse getPaymentById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido para buscar");
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Pago no encontrado con el id: %d", id)));
        return PaymentMapper.modelToPaymentResponse(payment);
    }

    @Override
    public PaymentResponse createPayment(CreatePaymentRequest req) throws Exception {
        if (Objects.isNull(req)) throw new Exception("El objeto createPaymentRequest no puede ser nulo");
        if (req.getOrderId() == null || req.getOrderId() <= 0) throw new Exception("El campo orderId debe contener un valor mayor a 0");
        if (Objects.isNull(req.getStatus()) || req.getStatus().isBlank()) throw new Exception("El campo status no puede ser nulo ni vacío");
        if (Objects.isNull(req.getIdempotencyKey()) || req.getIdempotencyKey().isBlank()) throw new Exception("El campo idempotencyKey no puede ser nulo ni vacío");
        Order order = orderRepository.findById(req.getOrderId()).orElseThrow(() -> new Exception("La orden no existe"));
        Payment payment = PaymentMapper.createPaymentRequestToPayment(req, order);
        payment = paymentRepository.save(payment);
        return PaymentMapper.modelToPaymentResponse(payment);
    }

    @Override
    public PaymentResponse updatePayment(Integer id, UpdatePaymentRequest req) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido para actualizar");
        if (Objects.isNull(req)) throw new Exception("El objeto updatePaymentRequest no puede ser nulo");
        if (req.getOrderId() == null || req.getOrderId() <= 0) throw new Exception("El campo orderId debe contener un valor mayor a 0");
        if (Objects.isNull(req.getStatus()) || req.getStatus().isBlank()) throw new Exception("El campo status no puede ser nulo ni vacío");
        if (Objects.isNull(req.getIdempotencyKey()) || req.getIdempotencyKey().isBlank()) throw new Exception("El campo idempotencyKey no puede ser nulo ni vacío");

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Pago no encontrado con el id: %d", id)));
        Order order = orderRepository.findById(req.getOrderId()).orElseThrow(() -> new Exception("La orden no existe"));

        payment.setOrder(order);
        payment.setStatus(req.getStatus());
        payment.setProviderRef(req.getProviderRef());
        payment.setIdempotencyKey(req.getIdempotencyKey());
        payment = paymentRepository.save(payment);
        return PaymentMapper.modelToPaymentResponse(payment);
    }
}
