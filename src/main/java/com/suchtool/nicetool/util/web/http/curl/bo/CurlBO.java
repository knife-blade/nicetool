package com.suchtool.nicetool.util.web.http.curl.bo;

import lombok.Data;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Data
public class CurlBO {
    @NotBlank(message = "url不能为空")
    private String url;

    @NotNull(message = "HTTP方法不能为空")
    private HttpMethod httpMethod;

    private Boolean enableFollowRedirect = true;

    private Integer redirectMaxCount = 5;

    private Duration timeout = Duration.ofSeconds(3);

    private String proxyUsername;

    private String proxyPassword;

    private String proxyHost;

    private Integer proxyPort;
}
