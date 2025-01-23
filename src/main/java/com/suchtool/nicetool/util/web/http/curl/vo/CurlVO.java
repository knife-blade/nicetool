package com.suchtool.nicetool.util.web.http.curl.vo;

import com.suchtool.nicetool.util.system.command.vo.CommandVO;
import com.suchtool.nicetool.util.web.http.curl.constant.CurlErrorCodeEnum;
import lombok.Data;
import org.springframework.util.MultiValueMap;

@Data
public class CurlVO {
    private CurlErrorCodeEnum errorCode;

    private CommandVO commandVO;

    private String errorMessage;

    private String originResponse;

    private MultiValueMap<String, String> responseHeader;

    private String responseBody;

    private Integer httpStatusCode;
}
