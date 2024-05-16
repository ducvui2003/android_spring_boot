package com.commic.v1.services.comment;

import lombok.Getter;

@Getter
public enum CommentConst {
    HIDE("1"),
    UN_HIDE("0");
    private String value;

    CommentConst(String value) {
        this.value = value;
    }
}
