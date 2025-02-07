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

import java.nio.file.FileSystem;

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
        cmdBuilder.append(String.format("\"%s\" ", curlBO.getUrl()));

        String cmd = cmdBuilder.toString();

        CommandVO commandVO = CommandUtil.executeCommand(cmd);

        return toCurlVO(curlBO, commandVO);
    }

    private static CurlVO toCurlVO(CurlBO curlBO, CommandVO commandVO) {
        CurlVO curlVO = new CurlVO();

        CurlErrorCodeEnum errorCodeEnum = CurlErrorCodeEnum.queryByCode(commandVO.getExitValue());

        MultiValueMap<String, String> responseHeader = null;
        Integer statusCode = null;
        String responseBody = null;
        if (CurlErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
            if (StringUtils.hasText(commandVO.getSuccessResult())) {
                String originResponse = commandVO.getSuccessResult();
                String lineSeparator = System.lineSeparator();

                // 分割每行响应头
                String[] lines = originResponse.split(lineSeparator);

                if (curlBO.getEnableResponseHeader()) {
                    responseHeader = parseResponseHeader(lines);
                }

                if (curlBO.getParseResponseBody()) {
                    responseBody = parseResponseBody(curlBO, lines);
                }

                statusCode = parseStatusCode(lines);
            }
        }

        curlVO.setErrorCode(errorCodeEnum);
        curlVO.setCommandVO(commandVO);
        curlVO.setErrorMessage(commandVO.getErrorResult());
        curlVO.setOriginResponse(commandVO.getSuccessResult());
        curlVO.setResponseHeader(responseHeader);
        curlVO.setResponseBody(responseBody);
        curlVO.setHttpStatusCode(statusCode);

        return curlVO;
    }

    private static MultiValueMap<String, String> parseResponseHeader(String[] lines) {
        MultiValueMap<String, String> headersMap = new LinkedMultiValueMap<>();

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

    private static String parseResponseBody(CurlBO curlBO,
                                            String[] lines) {
        int htmlIndex = 0;
        if (curlBO.getEnableResponseHeader()) {
            for (int i = lines.length - 1; i >= 0; i--) {
                if (lines[i].contains("<!DOCTYPE html>")) {
                    htmlIndex = i;
                    break;
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = htmlIndex; i < lines.length; i++) {
            stringBuilder.append(lines[i]);
        }

        return stringBuilder.toString();
    }

    private static Integer parseStatusCode(String[] lines) {
        String lastLine = lines[lines.length - 1];
        String statusCodeStr = lastLine.substring(lastLine.length() - 3);
        return Integer.parseInt(statusCodeStr);
    }

}
