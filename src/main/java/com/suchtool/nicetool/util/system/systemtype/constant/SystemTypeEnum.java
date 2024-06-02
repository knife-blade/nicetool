package com.suchtool.nicetool.util.system.systemtype.constant;

import lombok.Getter;

@Getter
public enum SystemTypeEnum {
    LINUX("Linux系统"),
    WINDOWS("Windows系统"),
    MAC("Mac OS系统"),
    ;

    private final String description;

    SystemTypeEnum(String description) {
        this.description = description;
    }
}
