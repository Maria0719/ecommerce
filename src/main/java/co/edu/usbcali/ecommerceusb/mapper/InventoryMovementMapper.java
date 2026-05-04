package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.model.InventoryMovement;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.sql.Timestamp;
import java.util.List;

public class InventoryMovementMapper {

    public static InventoryMovementResponse modelToInventoryMovementResponse(InventoryMovement movement) {
        return InventoryMovementResponse.builder()
                .id(movement.getId())
                .productId(movement.getProduct() != null ? movement.getProduct().getId() : null)
                .productName(movement.getProduct() != null ? movement.getProduct().getName() : null)
                .orderId(movement.getOrder() != null ? movement.getOrder().getId() : null)
                .type(movement.getType())
                .qty(movement.getQty())
                .build();
    }

    public static List<InventoryMovementResponse> modelToInventoryMovementResponseList(List<InventoryMovement> movements) {
        return movements.stream().map(InventoryMovementMapper::modelToInventoryMovementResponse).toList();
    }

    public static InventoryMovement createInventoryMovementRequestToInventoryMovement(
            CreateInventoryMovementRequest request, Product product, Order order) {
        return InventoryMovement.builder()
                .product(product)
                .order(order)
                .type(request.getType())
                .qty(request.getQty())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
