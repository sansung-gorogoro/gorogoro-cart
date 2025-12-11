package com.gorogoro_cart.cart.application.service;

import com.gorogoro_cart.cart.application.port.in.FindCartItemsUseCase;
import com.gorogoro_cart.cart.application.port.in.dto.CartDetailsDto;
import com.gorogoro_cart.cart.application.port.in.dto.CartItemDto;
import com.gorogoro_cart.cart.application.port.in.dto.SummaryDto;
import com.gorogoro_cart.cart.application.port.out.CoursePort;
import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto;
import com.gorogoro_cart.cart.domain.model.CartItem;
import com.gorogoro_cart.cart.domain.repository.CartRepository;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartQueryService implements FindCartItemsUseCase {

    private final CartRepository cartRepository;
    private final CoursePort coursePort;

    @Override
    public CartDetailsDto findCartItems(Long userId) {
        List<CartItem> cartItems = cartRepository.findAllByUserId(userId);
        if (cartItems.isEmpty()) {
            return new CartDetailsDto(userId, Collections.emptyList(), new SummaryDto(0, 0));
        }

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
                    CourseDetailDto detail = courseDetailsMap.get(item.getCourseId());
                    if (detail == null) {
                        return null;
                    }
                    return CartItemDto.of(item, detail);
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
        
