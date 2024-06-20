package com.suchtool.nicetool.util.web.http.curl.vo;

import com.suchtool.nicetool.util.web.http.curl.constant.CurlErrorCodeEnum;
import lombok.Data;

@Data
public class CurlVO {
    private CurlErrorCodeEnum errorCode;

    private String command;

    private String errorMessage;

    private Integer httpStatusCode;

    private String httpResponse;
}
