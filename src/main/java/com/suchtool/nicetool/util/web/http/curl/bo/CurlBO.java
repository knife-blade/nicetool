package com.suchtool.nicetool.util.web.http.curl.bo;

import lombok.Data;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotBlank;
import java.time.Duration;

@Data
public class CurlBO {
    @NotBlank(message = "url不能为空")
    private String url;

    private HttpMethod httpMethod = HttpMethod.GET;

    private Boolean enableFollowRedirect = true;

    private Boolean enableResponseHeader = false;

    private Boolean parseResponseBody = false;

    private Integer redirectMaxCount = 5;

    private Duration timeout = Duration.ofSeconds(5);

    private String proxyUsername;

    private String proxyPassword;

    private String proxyHost;

    private Integer proxyPort;
}
