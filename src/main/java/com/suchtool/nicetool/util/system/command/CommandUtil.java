package com.suchtool.nicetool.util.system.command;



import com.suchtool.nicetool.util.system.command.vo.CommandVO;
import com.suchtool.nicetool.util.system.systemtype.SystemTypeUtil;
import com.suchtool.nicetool.util.system.systemtype.constant.SystemTypeEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandUtil {
    public static CommandVO executeCommand(String command) {
        String data = null;
        String errorData = null;
        Process process = null;
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

            data = inputStreamToString(process.getInputStream());
            errorData = inputStreamToString(process.getErrorStream());
        } catch (Exception e) {
            String msg = String.format("执行命令失败。(命令为：%s)", command);
            throw new RuntimeException(msg, e);
        }

        CommandVO commandVO = new CommandVO();
        commandVO.setExitValue(process.exitValue());
        commandVO.setData(data);
        commandVO.setErrorData(errorData);

        return commandVO;
    }

    private static String inputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder inputStreamBuilder = new StringBuilder();
        String line;

        while ((line = inputStreamReader.readLine()) != null) {
            inputStreamBuilder.append(line).append(System.lineSeparator());
        }
        return inputStreamBuilder.toString();
    }

}
