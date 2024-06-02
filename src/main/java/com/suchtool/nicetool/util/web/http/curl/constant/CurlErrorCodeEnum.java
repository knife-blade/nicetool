package com.suchtool.nicetool.util.web.http.curl.constant;

import lombok.Getter;

@Getter
public enum CurlErrorCodeEnum {
    SUCCESS(0, "成功完成"),
    UNSUPPORTED_PROTOCOL(1, "不支持的协议"),
    FAILED_INIT(2, "初始化失败"),
    URL_FORMAT_ERROR(3, "URL格式错误"),
    NOT_BUILT_IN(4, "未内建"),
    CANNOT_RESOLVE_PROXY(5, "无法解析代理"),
    CANNOT_RESOLVE_HOST(6, "无法解析主机"),
    CANNOT_CONNECT(7, "无法连接"),
    FTP_WEIRD_SERVER_REPLY(8, "FTP服务器返回异常信息"),
    REMOTE_ACCESS_DENIED(9, "远程访问被拒绝"),
    FTP_ACCEPT_FAILED(10, "FTP接受请求失败"),
    FTP_WEIRD_PASS_REPLY(11, "FTP密码回复异常"),
    FTP_ACCEPT_TIMEOUT(12, "FTP接受请求超时"),
    FTP_WEIRD_PASV_REPLY(13, "FTP被动模式回复异常"),
    FTP_WEIRD_227_FORMAT(14, "FTP 227格式异常"),
    FTP_CANT_GET_HOST(15, "FTP无法获取主机"),
    FTP_CANNOT_SET_TYPE(17, "FTP无法设置传输类型"),
    PARTIAL_FILE(18, "部分文件"),
    FTP_CANNOT_RETRIEVE_FILE(19, "FTP无法检索文件"),
    QUOTE_ERROR(21, "命令错误"),
    HTTP_RETURNED_ERROR(22, "HTTP返回错误"),
    WRITE_ERROR(23, "写入错误"),
    UPLOAD_FAILED(25, "上传失败"),
    READ_ERROR(26, "读取错误"),
    OUT_OF_MEMORY(27, "内存不足"),
    REQUEST_TIMEOUT(28, "访问超时"),
    FTP_PORT_FAILED(30, "FTP端口失败"),
    FTP_CANNOT_USE_REST(31, "FTP无法使用REST命令"),
    RANGE_ERROR(33, "范围错误"),
    HTTP_POST_ERROR(34, "HTTP POST错误"),
    SSL_CONNECT_ERROR(35, "SSL连接错误"),
    BAD_DOWNLOAD_RESUME(36, "下载恢复错误"),
    FILE_CANNOT_READ_FILE(37, "文件无法读取"),
    LDAP_CANNOT_BIND(38, "LDAP无法绑定"),
    LDAP_SEARCH_FAILED(39, "LDAP搜索失败"),
    FUNCTION_NOT_FOUND(41, "未找到函数"),
    ABORTED_BY_CALLBACK(42, "被回调函数终止"),
    BAD_FUNCTION_ARGUMENT(43, "函数参数错误"),
    INTERFACE_FAILED(45, "接口失败"),
    TOO_MANY_REDIRECTS(47, "重定向次数过多"),
    UNKNOWN_OPTION(48, "未知选项"),
    TELNET_OPTION_SYNTAX(49, "Telnet选项语法错误"),
    PEER_FAILED_VERIFICATION(51, "验证失败"),
    GOT_NOTHING(52, "没有获得任何数据"),
    SSL_ENGINE_NOTFOUND(53, "SSL引擎未找到"),
    SSL_ENGINE_SET_FAILED(54, "SSL引擎设置失败"),
    SEND_ERROR(55, "发送错误"),
    RECEIVE_ERROR(56, "接收错误"),
    SSL_CERT_PROBLEM(58, "SSL证书问题"),
    SSL_CIPHER(59, "SSL密码"),
    SSL_CA_CERT(60, "SSL CA证书"),
    BAD_CONTENT_ENCODING(61, "内容编码错误"),
    LDAP_INVALID_URL(62, "LDAP URL无效"),
    FILESIZE_EXCEEDED(63, "文件大小超过限制"),
    USE_SSL_FAILED(64, "使用SSL失败"),
    SEND_FAIL_REWIND(65, "发送失败，需要重新发送"),
    SSL_ENGINE_INIT_FAILED(66, "SSL引擎初始化失败"),
    LOGIN_DENIED(67, "登录被拒绝"),
    TFTP_NOTFOUND(68, "TFTP未找到"),
    TFTP_PERM(69, "TFTP权限不足"),
    REMOTE_DISK_FULL(70, "远程磁盘已满"),
    TFTP_ILLEGAL(71, "TFTP非法操作"),
    TFTP_UNKNOWN_ID(72, "TFTP未知ID"),
    REMOTE_FILE_EXISTS(73, "远程文件已存在"),
    TFTP_NO_SUCH_USER(74, "TFTP用户不存在"),
    CONVERT_FAILED(75, "转换失败"),
    CONVERT_REQUIRED(76, "需要转换"),
    SSL_CA_CERT_BAD_FILE(77, "SSL CA证书文件错误"),
    REMOTE_FILE_NOT_FOUND(78, "远程文件未找到"),
    SSH(79, "SSH错误"),
    SSL_SHUTDOWN_FAILED(80, "SSL关闭失败"),
    AGAIN(81, "重试"),
    SSL_CRL_BAD_FILE(82, "SSL CRL文件错误"),
    SSL_ISSUER_ERROR(83, "SSL颁发者错误"),
    FTP_PRET_FAILED(84, "FTP PRET失败"),
    RTSP_CSEQ_ERROR(85, "RTSP CSeq错误"),
    RTSP_SESSION_ERROR(86, "RTSP会话错误"),
    FTP_BAD_FILE_LIST(87, "FTP文件列表错误"),
    CHUNK_FAILED(88, "分块错误");

    private final Integer code;

    private final String description;

    CurlErrorCodeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CurlErrorCodeEnum queryByCode(int code) {
        for (CurlErrorCodeEnum errorCode : CurlErrorCodeEnum.values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }

        return null;
    }
}