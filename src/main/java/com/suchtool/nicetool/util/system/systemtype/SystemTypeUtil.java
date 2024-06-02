package com.suchtool.nicetool.util.system.systemtype;

import com.suchtool.nicetool.util.system.systemtype.constant.SystemTypeEnum;

public class SystemTypeUtil {
    public static SystemTypeEnum judgeSystemType() {
        String os = System.getProperty("os.name");

        if (os.startsWith("Linux")) {
            return SystemTypeEnum.LINUX;
        } else if (os.startsWith("Windows")) {
            return SystemTypeEnum.WINDOWS;
        } else if (os.startsWith("Mac")) {
            return SystemTypeEnum.MAC;
        } else {
            throw new RuntimeException("不支持此系统：" + os);
        }
    }
}
