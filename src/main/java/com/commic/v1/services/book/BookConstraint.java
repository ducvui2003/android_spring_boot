package com.commic.v1.services.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
public enum BookConstraint {
    FULL("FULL"),
    UPDATE("UPDATING");
    private final String value;
}
