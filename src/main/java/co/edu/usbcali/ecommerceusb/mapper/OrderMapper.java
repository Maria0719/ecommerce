package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderResponse;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.User;

import java.sql.Timestamp;
import java.util.List;

public class OrderMapper {

    public static OrderResponse modelToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser() != null ? order.getUser().getId() : null)
                .userFullName(order.getUser() != null ? order.getUser().getFullName() : null)
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .currency(order.getCurrency())
                .build();
    }

    public static List<OrderResponse> modelToOrderResponseList(List<Order> orders) {
        return orders.stream().map(OrderMapper::modelToOrderResponse).toList();
    }

    public static Order createOrderRequestToOrder(CreateOrderRequest request, User user) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return Order.builder()
                .user(user)
                .status(request.getStatus())
                .totalAmount(request.getTotalAmount())
                .currency(request.getCurrency() != null ? request.getCurrency() : "COP")
                .createdAt(timestamp)
                .build();
    }
}
