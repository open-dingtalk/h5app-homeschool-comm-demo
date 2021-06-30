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
     * 初始化家校 url
     */
    public static final String HOMESCHOOL_INIT = "https://oapi.dingtalk.com/topapi/edu/school/init";
    /**
     * 查询部门列表 url
     */
    public static final String DEPT_LIST = "https://oapi.dingtalk.com/topapi/edu/dept/list";
    /**
     * 新建班级 url
     */
    public static final String CLASS_CREATE = "https://oapi.dingtalk.com/topapi/edu/class/create";
    /**
     * 添加家长 url
     */
    public static final String GUARDIAN_CREATE = "https://oapi.dingtalk.com/topapi/edu/guardian/create";
    /**
     * 获取人员列表 url
     */
    public static final String EDU_USER_LIST = "https://oapi.dingtalk.com/topapi/edu/user/list";
    /**
     * 发送通知 url
     */
    public static final String SEND_NOTI_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";
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
