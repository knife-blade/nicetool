package com.suchtool.nicetool.util.web.http.curl;

import com.suchtool.nicetool.util.base.ValidateUtil;
import com.suchtool.nicetool.util.system.command.CommandUtil;
import com.suchtool.nicetool.util.system.command.vo.CommandVO;
import com.suchtool.nicetool.util.web.http.curl.bo.CurlBO;
import com.suchtool.nicetool.util.web.http.curl.constant.CurlErrorCodeEnum;
import com.suchtool.nicetool.util.web.http.curl.vo.CurlVO;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

public class CurlUtil {
    public static CurlVO request(CurlBO curlBO) {
        ValidateUtil.validate(curlBO);

        StringBuilder cmdBuilder = new StringBuilder("curl -sS ");
        if (curlBO.getEnableResponseHeader()) {
            cmdBuilder.append("-i ");
        }
        cmdBuilder.append(String.format("-X %s ", curlBO.getHttpMethod().name()));
        if (HttpMethod.HEAD.equals(curlBO.getHttpMethod())) {
            cmdBuilder.append("--head ");
        }
        if (curlBO.getEnableFollowRedirect()) {
            cmdBuilder.append(String.format("-L --max-redirs %d ", curlBO.getRedirectMaxCount()));
        }
        cmdBuilder.append(String.format("--max-time %d ", curlBO.getTimeout().getSeconds()));
        cmdBuilder.append("-w %{http_code} ");
        if (StringUtils.hasText(curlBO.getProxyHost())) {
            cmdBuilder.append(String.format("-x %s:%d ", curlBO.getProxyHost(), curlBO.getProxyPort()));
        }
        if (StringUtils.hasText(curlBO.getProxyUsername())) {
            cmdBuilder.append(String.format("-U %s:%s ", curlBO.getProxyUsername(), curlBO.getProxyPassword()));
        }
        cmdBuilder.append(String.format("%s", curlBO.getUrl()));

        String cmd = cmdBuilder.toString();

        CommandVO commandVO = CommandUtil.executeCommand(cmd);

        return toCurlVO(curlBO, commandVO);
    }

    private static CurlVO toCurlVO(CurlBO curlBO, CommandVO commandVO) {
        CurlVO curlVO = new CurlVO();

        CurlErrorCodeEnum errorCodeEnum = CurlErrorCodeEnum.queryByCode(commandVO.getExitValue());

        String lineSeparator = System.lineSeparator();

        Integer statusCode = null;
        StringBuilder httpResponseBuilder = new StringBuilder();
        if (CurlErrorCodeEnum.SUCCESS.equals(errorCodeEnum)
                && StringUtils.hasText(commandVO.getData())) {
            String[] httpResponses = commandVO.getData().split(lineSeparator);

            // 最后一个是状态码
            for (int i = 0; i < httpResponses.length - 1; i++) {
                httpResponseBuilder.append(httpResponses[i]);
            }
            statusCode = Integer.parseInt(httpResponses[httpResponses.length - 1]);
        }

        StringBuilder httpErrorResponseBuilder = new StringBuilder();
        if (StringUtils.hasText(commandVO.getErrorData())) {
            String[] httpErrorResponses = commandVO.getErrorData().split(lineSeparator);
            for (String httpErrorResponse : httpErrorResponses) {
                httpErrorResponseBuilder.append(httpErrorResponse);
            }
        }

        if (curlBO.getEnableResponseHeader()) {
            curlVO.setHeader(parseHeader(commandVO.getData(), lineSeparator));
        }

        curlVO.setErrorCode(errorCodeEnum);
        curlVO.setCommand(commandVO.getCommand());
        curlVO.setOriginResult(commandVO.getData());
        curlVO.setHttpStatusCode(statusCode);
        curlVO.setHttpResponse(httpResponseBuilder.toString());
        curlVO.setErrorMessage(httpErrorResponseBuilder.toString());

        return curlVO;
    }

    private static MultiValueMap<String, String> parseHeader(String responseData,
                                                             String lineSeparator) {
        MultiValueMap<String, String> headersMap = new LinkedMultiValueMap<>();

        // 分割每行响应头
        String[] lines = responseData.split(lineSeparator);

        for (String line : lines) {
            // 空行则退出（结束）
            if (!StringUtils.hasText(line)) {
                break;
            }

            // 找到冒号的位置
            int colonIndex = line.indexOf(":");
            if (colonIndex != -1) {
                // 提取键和值
                String key = line.substring(0, colonIndex).trim();
                String value = line.substring(colonIndex + 1).trim();

                // 将键值对存入 Map
                headersMap.add(key, value);
            }
        }

        return headersMap;
    }

}
