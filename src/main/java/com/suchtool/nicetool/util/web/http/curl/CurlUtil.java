package com.suchtool.nicetool.util.web.http.curl;

import com.suchtool.nicetool.util.base.ValidateUtil;
import com.suchtool.nicetool.util.system.command.CommandUtil;
import com.suchtool.nicetool.util.system.command.vo.CommandVO;
import com.suchtool.nicetool.util.web.http.curl.bo.CurlBO;
import com.suchtool.nicetool.util.web.http.curl.constant.CurlErrorCodeEnum;
import com.suchtool.nicetool.util.web.http.curl.vo.CurlVO;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

public class CurlUtil {
    public static CurlVO request(CurlBO curlBO) {
        ValidateUtil.validate(curlBO);

        StringBuilder cmdBuilder = new StringBuilder("curl -sS ");
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
            cmdBuilder.append(String.format("-x %s:%s ", curlBO.getProxyUsername(), curlBO.getProxyPassword()));
        }
        cmdBuilder.append(String.format("%s", curlBO.getUrl()));

        String cmd = cmdBuilder.toString();

        CommandVO commandVO = CommandUtil.executeCommand(cmd);

        return toCurlVO(commandVO);
    }

    private static CurlVO toCurlVO(CommandVO commandVO) {
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

        CurlVO curlVO = new CurlVO();
        curlVO.setErrorCode(errorCodeEnum);
        curlVO.setHttpStatusCode(statusCode);
        curlVO.setHttpResponse(httpResponseBuilder.toString());
        curlVO.setErrorMessage(httpErrorResponseBuilder.toString());

        return curlVO;
    }

}
