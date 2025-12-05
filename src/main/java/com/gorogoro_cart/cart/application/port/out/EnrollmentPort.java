package com.gorogoro_cart.cart.application.port.out;

public interface EnrollmentPort {

    /**
     * 특정 사용자가 주어진 courseId의 강의를 이미 수강하고 있는지 확인합니다.
     *
     * @param userId   확인할 사용자의 ID
     * @param courseId 확인할 강의의 ID
     * @return 사용자가 해당 강의를 이미 수강 중이면 true, 그렇지 않으면 false
     */
    boolean isAlreadyEnrolled(Long userId, Long courseId);
}
