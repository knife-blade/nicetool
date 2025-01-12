package com.suchtool.nicetool.util.web.http.curl.vo;

import com.suchtool.nicetool.util.web.http.curl.constant.CurlErrorCodeEnum;
import lombok.Data;
import org.springframework.util.MultiValueMap;

@Data
public class CurlVO {
    private CurlErrorCodeEnum errorCode;

    private String command;

    private String errorMessage;

    private MultiValueMap<String, String> header;

    private String httpResponse;

    private Integer httpStatusCode;
}
