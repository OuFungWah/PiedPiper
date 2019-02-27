package com.crazywah.piedpiper.common;

public class ResponseStateCode {
    /**
     * 操作成功
     */
    public static final int CODE_200 = 200;
    /**
     * 客户端版本不对，需升级sdk
     */
    public static final int CODE_201 = 201;
    /**
     * 操作失败
     */
    public static final int CODE_202 = 202;
    /**
     * 被封禁
     */
    public static final int CODE_301 = 301;
    /**
     * 用户名或密码错误
     */
    public static final int CODE_302 = 302;
    /**
     * IP限制
     */
    public static final int CODE_315 = 315;
    /**
     * 非法操作或没有权限
     */
    public static final int CODE_403 = 403;
    /**
     * 对象不存在
     */
    public static final int CODE_404 = 404;
    /**
     * 参数长度过长
     */
    public static final int CODE_405 = 405;
    /**
     * 对象只读
     */
    public static final int CODE_406 = 406;
    /**
     * 客户端请求超时
     */
    public static final int CODE_408 = 408;
    /**
     * 验证失败(短信服务)
     */
    public static final int CODE_413 = 413;
    /**
     * 参数错误
     */
    public static final int CODE_414 = 414;
    /**
     * 客户端网络问题
     */
    public static final int CODE_415 = 415;
    /**
     * 频率控制
     */
    public static final int CODE_416 = 416;
    /**
     * 重复操作
     */
    public static final int CODE_417 = 417;
    /**
     * 通道不可用(短信服务)
     */
    public static final int CODE_418 = 418;
    /**
     * 数量超过上限
     */
    public static final int CODE_419 = 419;
    /**
     * 账号被禁用
     */
    public static final int CODE_422 = 422;
    /**
     * HTTP重复请求
     */
    public static final int CODE_431 = 431;
    /**
     * 服务器内部错误
     */
    public static final int CODE_500 = 500;
    /**
     * 服务器繁忙
     */
    public static final int CODE_503 = 503;
    /**
     * 消息撤回时间超限
     */
    public static final int CODE_508 = 508;
    /**
     * 无效协议
     */
    public static final int CODE_509 = 509;
    /**
     * 服务不可用
     */
    public static final int CODE_514 = 514;
    /**
     * 解包错误
     */
    public static final int CODE_998 = 998;
    /**
     * 打包错误
     */
    public static final int CODE_999 = 999;

    /**
     * 群人数达到上限
     */
    public static final int CODE_801 = 801;
    /**
     * 没有权限
     */
    public static final int CODE_802 = 802;
    /**
     * 群不存在
     */
    public static final int CODE_803 = 803;
    /**
     * 用户不在群
     */
    public static final int CODE_804 = 804;
    /**
     * 群类型不匹配
     */
    public static final int CODE_805 = 805;
    /**
     * 创建群数量达到限制
     */
    public static final int CODE_806 = 806;
    /**
     * 群成员状态错误
     */
    public static final int CODE_807 = 807;
    /**
     * 申请成功
     */
    public static final int CODE_808 = 808;
    /**
     * 已经在群内
     */
    public static final int CODE_809 = 809;
    /**
     * 邀请成功
     */
    public static final int CODE_810 = 810;
    /**
     * 通道失效
     */
    public static final int CODE_9102 = 9102;
    /**
     * 已经在他端对这个呼叫响应过了
     */
    public static final int CODE_9103 = 9103;
    /**
     * 通话不可达，对方离线状态
     */
    public static final int CODE_11001 = 11001;
    /**
     * IM主连接状态异常
     */
    public static final int CODE_13001 = 13001;
    /**
     * 聊天室状态异常
     */
    public static final int CODE_13002 = 13002;
    /**
     * 账号在黑名单中,不允许进入聊天室
     */
    public static final int CODE_13003 = 13003;
    /**
     * 在禁言列表中,不允许发言
     */
    public static final int CODE_13004 = 13004;
    /**
     * 输入email不是邮箱
     */
    public static final int CODE_10431 = 10431;
    /**
     * 输入mobile不是手机号码
     */
    public static final int CODE_10432 = 10432;
    /**
     * 注册输入的两次密码不相同
     */
    public static final int CODE_10433 = 10433;
    /**
     * 企业不存在
     */
    public static final int CODE_10434 = 10434;
    /**
     * 登陆密码或帐号不对
     */
    public static final int CODE_10435 = 10435;
    /**
     * app不存在
     */
    public static final int CODE_10436 = 10436;
    /**
     * email已注册
     */
    public static final int CODE_10437 = 10437;
    /**
     * 手机号已注册
     */
    public static final int CODE_10438 = 10438;
    /**
     * app名字已经存在
     */
    public static final int CODE_10441 = 10441;

    /**
     * 数据库操作失败
     */
    public static final int CODE_14001 = 14001;

    public static String getMessageByCode(int code) {
        switch (code) {
            case CODE_200:
                return "操作成功";
            case CODE_201:
                return "客户端版本不对，需升级sdk";
            case CODE_202:
                return "操作失败";
            case CODE_301:
                return "被封禁";
            case CODE_302:
                return "用户名或密码错误";
            case CODE_315:
                return "IP限制";
            case CODE_403:
                return "非法操作或没有权限";
            case CODE_404:
                return "对象不存在";
            case CODE_405:
                return "参数长度过长";
            case CODE_406:
                return "对象只读";
            case CODE_408:
                return "客户端请求超时";
            case CODE_413:
                return "验证失败(短信服务)";
            case CODE_414:
                return "参数错误";
            case CODE_415:
                return "客户端网络问题";
            case CODE_416:
                return "频率控制";
            case CODE_417:
                return "重复操作";
            case CODE_418:
                return "通道不可用(短信服务)";
            case CODE_419:
                return "数量超过上限";
            case CODE_422:
                return "账号被禁用";
            case CODE_431:
                return "HTTP重复请求";
            case CODE_500:
                return "服务器内部错误";
            case CODE_503:
                return "服务器繁忙";
            case CODE_508:
                return "消息撤回时间超限";
            case CODE_509:
                return "无效协议";
            case CODE_514:
                return "服务不可用";
            case CODE_801:
                return "群人数达到上限";
            case CODE_802:
                return "没有权限";
            case CODE_803:
                return "群不存在";
            case CODE_804:
                return "用户不在群";
            case CODE_805:
                return "群类型不匹配";
            case CODE_806:
                return "创建群数量达到限制";
            case CODE_807:
                return "群成员状态错误";
            case CODE_808:
                return "申请成功";
            case CODE_809:
                return "已经在群内";
            case CODE_810:
                return "邀请成功";
            case CODE_998:
                return "解包错误";
            case CODE_999:
                return "打包错误";
            case CODE_9102:
                return "通道失效";
            case CODE_9103:
                return "已经在他端对这个呼叫响应过了";
            case CODE_10431:
                return "输入email不是邮箱";
            case CODE_10432:
                return "输入mobile不是手机号码";
            case CODE_10433:
                return "注册输入的两次密码不相同";
            case CODE_10434:
                return "企业不存在";
            case CODE_10435:
                return "登陆密码或帐号不对";
            case CODE_10436:
                return "app不存在";
            case CODE_10437:
                return "email已注册";
            case CODE_10438:
                return "手机号已注册";
            case CODE_10441:
                return "app名字已经存在";
            case CODE_11001:
                return "通话不可达，对方离线状态";
            case CODE_13001:
                return "IM主连接状态异常";
            case CODE_13002:
                return "聊天室状态异常";
            case CODE_13003:
                return "账号在黑名单中,不允许进入聊天室";
            case CODE_13004:
                return "在禁言列表中,不允许发言";
            case CODE_14001:
                return "数据库操作失败";
            default:
                return "";
        }
    }
}
