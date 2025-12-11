package com.gorogoro_cart.cart.infrastructure.adapter;

import com.gorogoro_cart.cart.application.port.out.CoursePort;
import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CourseAdapter implements CoursePort {
    private final WebClient webClient;

    @Override
    public boolean isCoursePurchasable(Long courseId) {
        return Boolean.TRUE.equals(webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/courses/{courseId}/purchasable").build(courseId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleWebClientError)
                .bodyToMono(Boolean.class)
                .defaultIfEmpty(false)
                .block());
    }

    @Override
    public List<CourseDetailDto> findCourseDetailsByIds(List<Long> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) {
            return List.of();
        }

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/courses")
                        .queryParam("ids", courseIds.stream().map(String::valueOf).collect(Collectors.joining(",")))
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleWebClientError)
                .bodyToFlux(CourseDetailDto.class)
                .collectList()
                .block();
    }

    private Mono<? extends Throwable> handleWebClientError(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(new RuntimeException(
                        "WebClient request failed. Status: " + clientResponse.statusCode() + ", Body: " + errorBody)));
    }
}
