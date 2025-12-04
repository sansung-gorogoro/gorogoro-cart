package com.gorogoro_cart.cart.infrastructure.persistence.entity;

import com.gorogoro_cart.cart.domain.model.vo.CartItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "cart_item",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_cart_item_user_course",
                        columnNames = {"user_id", "course_id"})
        })
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CartItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long courseId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime addedAt;

    private CartItemJpaEntity(Long userId, Long courseId, LocalDateTime addedAt) {
        this.userId = userId;
        this.courseId = courseId;
        this.addedAt = addedAt;
    }

    public static CartItemJpaEntity fromDomain(Long userId, CartItem cartItem) {
        return new CartItemJpaEntity(
                userId,
                cartItem.getCourseId(),
                cartItem.getAddedAt()
        );
    }

    public CartItem toDomain() {
        return CartItem.of(this.courseId, this.addedAt);
    }
}
