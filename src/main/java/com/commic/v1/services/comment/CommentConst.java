package com.commic.v1.services.comment;

import lombok.Getter;

@Getter
public enum CommentConst {
    HIDE("0"),
    UN_HIDE("1");
    private String value;

    CommentConst(String value) {
        this.value = value;
    }
}
