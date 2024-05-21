package com.commic.v1.services.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CommentConst {
    HIDE(1),
    UN_HIDE(0);
    private Integer value;
}
