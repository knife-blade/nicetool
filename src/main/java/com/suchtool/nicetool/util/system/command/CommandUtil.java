package com.suchtool.nicetool.util.system.command;



import com.suchtool.nicetool.util.system.command.vo.CommandVO;
import com.suchtool.nicetool.util.system.systemtype.SystemTypeUtil;
import com.suchtool.nicetool.util.system.systemtype.constant.SystemTypeEnum;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CommandUtil {
    public static CommandVO executeCommand(String command) {
        String data = null;
        String errorData = null;
        Process process = null;
        int exitValue;
        try {
            String[] cmd = null;

            SystemTypeEnum systemTypeEnum = SystemTypeUtil.judgeSystemType();
            switch (systemTypeEnum) {
                case LINUX:
                    cmd = new String[]{"/bin/sh", "-c", command};
                    break;
                case WINDOWS:
                    cmd = new String[]{"cmd", "/c", command};
                    break;
                default:
                    throw new RuntimeException("暂时不支持此系统：" + systemTypeEnum.getDescription());
            }

            process = Runtime.getRuntime().exec(cmd);

            // 输出结果，必须写在 waitFor 之前
            data = StreamUtils.copyToString(process.getInputStream(), StandardCharsets.UTF_8);
            // 错误结果，必须写在 waitFor 之前
            errorData = StreamUtils.copyToString(process.getErrorStream(), StandardCharsets.UTF_8);

            exitValue = process.waitFor();
        } catch (Exception e) {
            String msg = String.format("执行命令失败。(命令为：%s)", command);
            throw new RuntimeException(msg, e);
        }

        CommandVO commandVO = new CommandVO();
        commandVO.setExitValue(exitValue);
        commandVO.setCommand(command);
        commandVO.setSuccessResult(data);
        commandVO.setErrorResult(errorData);

        return commandVO;
    }
}
