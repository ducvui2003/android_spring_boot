package com.commic.v1.services.attendance;

public enum ExchangeStatus {
    EXIST_IN_REPO(1), POINT_NOT_ENOUGH(2), EXCHANGE_SUCCESS(3), EXCHANGE_FAIL(4);
    private Integer status;

    ExchangeStatus(Integer status) {
        this.status = status;
    }
}
