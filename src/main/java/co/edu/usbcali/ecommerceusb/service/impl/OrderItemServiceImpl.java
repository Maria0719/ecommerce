package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderItemResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.mapper.OrderItemMapper;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.OrderItem;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.OrderItemRepository;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderItemResponse> getOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        if (orderItems.isEmpty()) return List.of();
        return OrderItemMapper.modelToOrderItemResponseList(orderItems);
    }

    @Override
    public OrderItemResponse getOrderItemById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido para buscar");
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Ítem de orden no encontrado con el id: %d", id)));
        return OrderItemMapper.modelToOrderItemResponse(orderItem);
    }

    @Override
    public OrderItemResponse createOrderItem(CreateOrderItemRequest req) throws Exception {
        if (Objects.isNull(req)) throw new Exception("El objeto createOrderItemRequest no puede ser nulo");
        if (req.getOrderId() == null || req.getOrderId() <= 0) throw new Exception("El campo orderId debe contener un valor mayor a 0");
        if (req.getProductId() == null || req.getProductId() <= 0) throw new Exception("El campo productId debe contener un valor mayor a 0");
        if (req.getQuantity() == null || req.getQuantity() <= 0) throw new Exception("El campo quantity debe ser mayor a 0");
        if (Objects.isNull(req.getUnitPriceSnapshot()) || req.getUnitPriceSnapshot().compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("El campo unitPriceSnapshot no puede ser nulo y debe ser mayor o igual a 0");
        if (Objects.isNull(req.getLineTotal()) || req.getLineTotal().compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("El campo lineTotal no puede ser nulo y debe ser mayor o igual a 0");
        Order order = orderRepository.findById(req.getOrderId()).orElseThrow(() -> new Exception("La orden no existe"));
        Product product = productRepository.findById(req.getProductId()).orElseThrow(() -> new Exception("El producto no existe"));
        OrderItem orderItem = OrderItemMapper.createOrderItemRequestToOrderItem(req, order, product);
        orderItem = orderItemRepository.save(orderItem);
        return OrderItemMapper.modelToOrderItemResponse(orderItem);
    }

    @Override
    public OrderItemResponse updateOrderItem(Integer id, UpdateOrderItemRequest req) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido para actualizar");
        if (Objects.isNull(req)) throw new Exception("El objeto updateOrderItemRequest no puede ser nulo");
        if (req.getOrderId() == null || req.getOrderId() <= 0) throw new Exception("El campo orderId debe contener un valor mayor a 0");
        if (req.getProductId() == null || req.getProductId() <= 0) throw new Exception("El campo productId debe contener un valor mayor a 0");
        if (req.getQuantity() == null || req.getQuantity() <= 0) throw new Exception("El campo quantity debe ser mayor a 0");
        if (Objects.isNull(req.getUnitPriceSnapshot()) || req.getUnitPriceSnapshot().compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("El campo unitPriceSnapshot no puede ser nulo y debe ser mayor o igual a 0");
        if (Objects.isNull(req.getLineTotal()) || req.getLineTotal().compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("El campo lineTotal no puede ser nulo y debe ser mayor o igual a 0");

        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Ítem de orden no encontrado con el id: %d", id)));
        Order order = orderRepository.findById(req.getOrderId()).orElseThrow(() -> new Exception("La orden no existe"));
        Product product = productRepository.findById(req.getProductId()).orElseThrow(() -> new Exception("El producto no existe"));

        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(req.getQuantity());
        orderItem.setUnitPriceSnapshot(req.getUnitPriceSnapshot());
        orderItem.setLineTotal(req.getLineTotal());
        orderItem = orderItemRepository.save(orderItem);
        return OrderItemMapper.modelToOrderItemResponse(orderItem);
    }
}
