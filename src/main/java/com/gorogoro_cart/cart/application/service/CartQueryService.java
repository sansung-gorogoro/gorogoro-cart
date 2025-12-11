package com.gorogoro_cart.cart.application.service;

import com.gorogoro_cart.cart.application.port.in.FindCartItemsUseCase;
import com.gorogoro_cart.cart.application.port.in.dto.CartDetailsDto;
import com.gorogoro_cart.cart.application.port.in.dto.CartItemDto;
import com.gorogoro_cart.cart.application.port.in.dto.SummaryDto;
import com.gorogoro_cart.cart.application.port.out.CoursePort;
import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto;
import com.gorogoro_cart.cart.domain.model.CartItem;
import com.gorogoro_cart.cart.domain.repository.CartRepository;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartQueryService implements FindCartItemsUseCase {
    private final CartRepository cartRepository;
    private final CoursePort coursePort;

    @Override
    public CartDetailsDto findCartItems(Long userId) {
        List<CartItem> cartItems = cartRepository.findAllByUserId(userId);
        Map<Long, CourseDetailDto> courseDetailsMap = getCourseDetailsMap(cartItems);
        List<CartItemDto> itemDtos = combineCartAndCourseDetails(cartItems, courseDetailsMap);
        SummaryDto summary = SummaryDto.from(itemDtos);

        return new CartDetailsDto(userId, itemDtos, summary);
    }

    private Map<Long, CourseDetailDto> getCourseDetailsMap(List<CartItem> cartItems) {
        List<Long> courseIds = cartItems.stream()
                .map(CartItem::getCourseId)
                .toList();

        return coursePort.findCourseDetailsByIds(courseIds).stream()
                .collect(Collectors.toMap(CourseDetailDto::courseId, Function.identity()));
    }

    private List<CartItemDto> combineCartAndCourseDetails(List<CartItem> cartItems,
                                                          Map<Long, CourseDetailDto> courseDetailsMap) {
        return cartItems.stream()
                .map(item -> {
                    CourseDetailDto courseDetail = courseDetailsMap.get(item.getCourseId());
                    validateCourseDetail(item, courseDetail);
                    return CartItemDto.of(item, courseDetail);
                })
                .toList();
    }

    private void validateCourseDetail(CartItem item, CourseDetailDto courseDetail) {
        if (courseDetail == null) {
            log.warn("Course detail not found for courseId {}", item.getCourseId());
            throw new IllegalStateException("Missing course detail for courseId " + item.getCourseId());
        }
    }
}
        
