package com.suchtool.nicetool.util.system.command.vo;

import lombok.Data;

@Data
public class CommandVO {
    /**
     * 0：成功执行
     * 非0：执行失败
     */
    private Integer exitValue;

    private String data;

    private String errorData;
}
