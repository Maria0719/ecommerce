package co.edu.usbcali.ecommerceusb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "products",
        schema = "public",
        indexes = {
                @Index(name = "idx_products_available", columnList = "available"),
                @Index(name = "idx_products_price", columnList = "price")
                // El índice FTS (idx_products_fts) no puede expresarse directamente en JPA,
                // se maneja a nivel de base de datos.
        }
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false, columnDefinition = "numeric(12,2) check (price >= 0)")
    private BigDecimal price;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean available;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now()")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now()")
    private Timestamp updatedAt;
}
