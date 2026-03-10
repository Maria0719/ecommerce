package co.edu.usbcali.ecommerceusb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "payments",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_payments_idempotency_key", columnNames = {"idempotency_key"})
        },
        indexes = {
                @Index(name = "idx_payments_order", columnList = "order_id")
                // El índice único parcial (uq_one_success_payment_per_order) no puede expresarse directamente en JPA,
                // se maneja a nivel de base de datos o con validaciones en la aplicación.
        }
)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_payments_order"),
            referencedColumnName = "id"
    )
    private Order order;

    @Column(nullable = false)
    private String status;

    @Column(name = "provider_ref")
    private String providerRef;

    @Column(name = "idempotency_key", nullable = false)
    private String idempotencyKey;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now()")
    private Timestamp createdAt;
}
