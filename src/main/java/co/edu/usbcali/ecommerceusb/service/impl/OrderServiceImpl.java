package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateOrderRequest;
import co.edu.usbcali.ecommerceusb.mapper.OrderMapper;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import co.edu.usbcali.ecommerceusb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<OrderResponse> getOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) return List.of();
        return OrderMapper.modelToOrderResponseList(orders);
    }

    @Override
    public OrderResponse getOrderById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido para buscar");
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Orden no encontrada con el id: %d", id)));
        return OrderMapper.modelToOrderResponse(order);
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) throws Exception {
        if (Objects.isNull(createOrderRequest)) throw new Exception("El objeto createOrderRequest no puede ser nulo");
        if (createOrderRequest.getUserId() == null || createOrderRequest.getUserId() <= 0)
            throw new Exception("El campo userId debe contener un valor mayor a 0");
        if (Objects.isNull(createOrderRequest.getStatus()) || createOrderRequest.getStatus().isBlank())
            throw new Exception("El campo status no puede ser nulo ni vacío");
        if (Objects.isNull(createOrderRequest.getTotalAmount()) || createOrderRequest.getTotalAmount().compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("El campo totalAmount no puede ser nulo y debe ser mayor o igual a 0");
        User user = userRepository.findById(createOrderRequest.getUserId()).orElseThrow(() -> new Exception("El usuario no existe"));
        Order order = OrderMapper.createOrderRequestToOrder(createOrderRequest, user);
        order = orderRepository.save(order);
        return OrderMapper.modelToOrderResponse(order);
    }

    @Override
    public OrderResponse updateOrder(Integer id, UpdateOrderRequest updateOrderRequest) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido para actualizar");
        if (Objects.isNull(updateOrderRequest)) throw new Exception("El objeto updateOrderRequest no puede ser nulo");
        if (updateOrderRequest.getUserId() == null || updateOrderRequest.getUserId() <= 0)
            throw new Exception("El campo userId debe contener un valor mayor a 0");
        if (Objects.isNull(updateOrderRequest.getStatus()) || updateOrderRequest.getStatus().isBlank())
            throw new Exception("El campo status no puede ser nulo ni vacío");
        if (Objects.isNull(updateOrderRequest.getTotalAmount()) || updateOrderRequest.getTotalAmount().compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("El campo totalAmount no puede ser nulo y debe ser mayor o igual a 0");

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Orden no encontrada con el id: %d", id)));
        User user = userRepository.findById(updateOrderRequest.getUserId()).orElseThrow(() -> new Exception("El usuario no existe"));

        order.setUser(user);
        order.setStatus(updateOrderRequest.getStatus());
        order.setTotalAmount(updateOrderRequest.getTotalAmount());
        order.setCurrency(updateOrderRequest.getCurrency());
        order = orderRepository.save(order);
        return OrderMapper.modelToOrderResponse(order);
    }
}
