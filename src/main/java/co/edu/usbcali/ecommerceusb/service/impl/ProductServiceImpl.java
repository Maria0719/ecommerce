package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateProductRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateProductRequest;
import co.edu.usbcali.ecommerceusb.mapper.ProductMapper;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) return List.of();
        return ProductMapper.modelToProductResponseList(products);
    }

    @Override
    public ProductResponse getProductById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido para buscar");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Producto no encontrado con el id: %d", id)));
        return ProductMapper.modelToProductResponse(product);
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest req) throws Exception {
        if (Objects.isNull(req)) throw new Exception("El objeto createProductRequest no puede ser nulo");
        if (Objects.isNull(req.getName()) || req.getName().isBlank()) throw new Exception("El campo name no puede ser nulo ni vacío");
        if (Objects.isNull(req.getPrice()) || req.getPrice().compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("El campo price no puede ser nulo y debe ser mayor o igual a 0");
        Product product = ProductMapper.createProductRequestToProduct(req);
        product = productRepository.save(product);
        return ProductMapper.modelToProductResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Integer id, UpdateProductRequest req) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido para actualizar");
        if (Objects.isNull(req)) throw new Exception("El objeto updateProductRequest no puede ser nulo");
        if (Objects.isNull(req.getName()) || req.getName().isBlank()) throw new Exception("El campo name no puede ser nulo ni vacío");
        if (Objects.isNull(req.getPrice()) || req.getPrice().compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("El campo price no puede ser nulo y debe ser mayor o igual a 0");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Producto no encontrado con el id: %d", id)));

        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setAvailable(req.getAvailable());
        product.setUpdatedAt(Timestamp.from(Instant.now()));
        product = productRepository.save(product);
        return ProductMapper.modelToProductResponse(product);
    }

    @Override
    public void deleteProduct(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido para eliminar");
        Product product = productRepository.findById(id).orElseThrow(() -> new Exception(String.format("Product no encontrado con el id: %d", id)));
        productRepository.delete(product);
    }
}
