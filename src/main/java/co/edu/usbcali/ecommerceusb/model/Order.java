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
        name = "orders",
        schema = "public",
        indexes = {
                @Index(name = "idx_orders_user_created_at", columnList = "user_id, created_at DESC"),
                @Index(name = "idx_orders_status_created_at", columnList = "status, created_at DESC")
        }
)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_orders_user"),
            referencedColumnName = "id"
    )
    private User user;

    @Column(nullable = false)
    private String status;

    @Column(name = "total_amount", nullable = false, columnDefinition = "numeric(12,2) default 0 check (total_amount >= 0)")
    private BigDecimal totalAmount;

    @Column(nullable = false, columnDefinition = "text default 'COP'")
    private String currency;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now()")
    private Timestamp createdAt;

    @Column(name = "paid_at")
    private Timestamp paidAt;

    @Column(name = "cancelled_at")
    private Timestamp cancelledAt;
}
