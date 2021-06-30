package com.dingtalk.constant;

/**
 * 钉钉开放接口网关常量
 */
public class UrlConstant {

    /**
     * 获取access_token url
     */
    public static final String GET_ACCESS_TOKEN_URL = "https://oapi.dingtalk.com/gettoken";

    /**
     * 获取角色list url
     */
    public static final String GET_ROLE_LIST = "https://oapi.dingtalk.com/topapi/role/list";

    /**
     * 获取角色详细信息 url（role_id）
     */
    public static final String GET_ROLE_SIMPLE_LIST = "https://oapi.dingtalk.com/topapi/role/simplelist";

    /**
     * 新增待办任务 url
     */
    public static final String WORKRECORD_ADD = "https://oapi.dingtalk.com/topapi/workrecord/add";
    /**
     * 更新待办任务 url
     */
    public static final String WORKRECORD_UPDATE = "https://oapi.dingtalk.com/topapi/workrecord/update";
    /**
     * 通过免登授权码获取用户信息 url
     */
    public static final String GET_USER_INFO_URL = "https://oapi.dingtalk.com/topapi/v2/user/getuserinfo";
    /**
     * 根据用户id获取用户详情 url
     */
    public static final String USER_GET_URL = "https://oapi.dingtalk.com/topapi/v2/user/get";
    /**
     * 发送群助手消息 url
     */
    public static final String SCENCEGROUP_MESSAGE_SEND_V2 = "https://oapi.dingtalk.com/topapi/im/chat/scencegroup/message/send_v2";
}
