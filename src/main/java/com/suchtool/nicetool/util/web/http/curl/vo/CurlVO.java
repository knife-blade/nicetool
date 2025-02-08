package com.suchtool.nicetool.util.web.http.curl.vo;

import com.suchtool.nicetool.util.system.command.vo.CommandVO;
import com.suchtool.nicetool.util.web.http.curl.constant.CurlErrorCodeEnum;
import lombok.Data;
import org.springframework.util.MultiValueMap;

@Data
public class CurlVO {
    private CurlErrorCodeEnum errorCode;

    /**
     * curl命令数据
     */
    private CommandVO commandVO;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 原始响应
     */
    private String originResponse;

    /**
     * 第一个请求的响应Header
     */
    private MultiValueMap<String, String> responseHeader;

    /**
     * HTTP状态码
     */
    private Integer httpStatusCode;
}
