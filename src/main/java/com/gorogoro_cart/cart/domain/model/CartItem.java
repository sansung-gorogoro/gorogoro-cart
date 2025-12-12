package com.gorogoro_cart.cart.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "cart_item",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_cart_item_user_course", columnNames = {"user_id", "course_id"})
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long courseId;

    @Column(nullable = false, updatable = false)
    private Instant addedAt;

    private CartItem(Long userId, Long courseId, Instant addedAt) {
        this.userId = Objects.requireNonNull(userId, "유저 ID는 null일 수 없습니다.");
        this.courseId = Objects.requireNonNull(courseId, "강좌 ID는 null일 수 없습니다.");
        this.addedAt = Objects.requireNonNull(addedAt, "추가 시간은 null일 수 없습니다.");
    }

    public static CartItem of(Long userId, Long courseId, Instant addedAt) {
        return new CartItem(userId, courseId, addedAt);
    }
}
