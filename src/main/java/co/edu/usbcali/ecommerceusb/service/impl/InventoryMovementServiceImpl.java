package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.mapper.InventoryMovementMapper;
import co.edu.usbcali.ecommerceusb.model.InventoryMovement;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.InventoryMovementRepository;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.InventoryMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class InventoryMovementServiceImpl implements InventoryMovementService {

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<InventoryMovementResponse> getInventoryMovements() {
        List<InventoryMovement> movements = inventoryMovementRepository.findAll();

        if (movements.isEmpty()) {
            return List.of();
        }

        return InventoryMovementMapper.modelToInventoryMovementResponseList(movements);
    }

    @Override
    public InventoryMovementResponse getInventoryMovementById(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar un id válido para buscar");
        }

        InventoryMovement movement = inventoryMovementRepository.findById(id)
                .orElseThrow(() ->
                        new Exception(
                                String.format("Movimiento de inventario no encontrado con el id: %d", id)));

        return InventoryMovementMapper.modelToInventoryMovementResponse(movement);
    }

    @Override
    public InventoryMovementResponse createInventoryMovement(
            CreateInventoryMovementRequest createInventoryMovementRequest) throws Exception {

        if (Objects.isNull(createInventoryMovementRequest)) {
            throw new Exception("El objeto createInventoryMovementRequest no puede ser nulo");
        }

        if (createInventoryMovementRequest.getProductId() == null ||
                createInventoryMovementRequest.getProductId() <= 0) {
            throw new Exception("El campo productId debe contener un valor mayor a 0");
        }

        if (Objects.isNull(createInventoryMovementRequest.getType()) ||
                createInventoryMovementRequest.getType().isBlank()) {
            throw new Exception("El campo type no puede ser nulo ni vacío");
        }

        if (createInventoryMovementRequest.getQty() == null || createInventoryMovementRequest.getQty() <= 0) {
            throw new Exception("El campo qty debe ser mayor a 0");
        }

        Product product = productRepository.findById(createInventoryMovementRequest.getProductId())
                .orElseThrow(() -> new Exception("El producto no existe"));

        Order order = null;
        if (createInventoryMovementRequest.getOrderId() != null) {
            if (createInventoryMovementRequest.getOrderId() <= 0) {
                throw new Exception("El campo orderId debe ser mayor a 0");
            }
            order = orderRepository.findById(createInventoryMovementRequest.getOrderId())
                    .orElseThrow(() -> new Exception("La orden no existe"));
        }

        InventoryMovement movement = InventoryMovementMapper.createInventoryMovementRequestToInventoryMovement(
                createInventoryMovementRequest, product, order);
        movement = inventoryMovementRepository.save(movement);

        return InventoryMovementMapper.modelToInventoryMovementResponse(movement);
    }
}
