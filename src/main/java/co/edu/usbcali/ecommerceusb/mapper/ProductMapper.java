package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateProductRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductResponse;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.sql.Timestamp;
import java.util.List;

public class ProductMapper {

    public static ProductResponse modelToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .available(product.getAvailable())
                .build();
    }

    public static List<ProductResponse> modelToProductResponseList(List<Product> products) {
        return products.stream().map(ProductMapper::modelToProductResponse).toList();
    }

    public static Product createProductRequestToProduct(CreateProductRequest request) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .available(request.getAvailable() != null ? request.getAvailable() : true)
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .build();
    }
}
