package com.dingtalk.service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.dingtalk.constant.AppConstant;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.util.AccessTokenUtil;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Service;

/**
 * 家校管理
 */
@Service
public class HomeschoolManager {

    /**
     * 初始化家校结构
     */
    public OapiEduSchoolInitResponse initHomeschool(OapiEduSchoolInitRequest req) throws ApiException {
        // 获取access_token
        String accessToken = AccessTokenUtil.getAccessToken();

        // 发起调用初始化家校
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.HOMESCHOOL_INIT);
        OapiEduSchoolInitResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
        return rsp;
    }

    /**
     * 查询部门列表
     */
    public OapiEduDeptListResponse deptList(Long pageNo, Long pageSize, Long deptId) throws ApiException {
        String accessToken = AccessTokenUtil.getAccessToken();

        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.DEPT_LIST);
        OapiEduDeptListRequest req = new OapiEduDeptListRequest();
        req.setPageSize(pageSize);
        req.setPageNo(pageNo);
        req.setSuperId(deptId);
        OapiEduDeptListResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
        return rsp;
    }

    /**
     * 创建班级
     *
     * @throws ApiException
     */
    public OapiEduClassCreateResponse classCreate(OapiEduClassCreateRequest req) throws ApiException {
        String accessToken = AccessTokenUtil.getAccessToken();

        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.CLASS_CREATE);
        OapiEduClassCreateResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
        return rsp;
    }

    /**
     * 添加家长
     *
     * @throws ApiException
     */
    public OapiEduGuardianCreateResponse guardianCreate(OapiEduGuardianCreateRequest req) throws ApiException {
        String accessToken = AccessTokenUtil.getAccessToken();

        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.GUARDIAN_CREATE);
        OapiEduGuardianCreateResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
        return rsp;
    }

    /**
     * 获取人员列表
     *
     * @throws ApiException
     */
    public OapiEduUserListResponse eduUserList(Long pageNo, Long pageSize, String role, Long classId) throws ApiException {
        String accessToken = AccessTokenUtil.getAccessToken();

        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.EDU_USER_LIST);
        OapiEduUserListRequest req = new OapiEduUserListRequest();
        req.setPageSize(pageSize);
        req.setPageNo(pageNo);
        req.setRole(role);
        req.setClassId(classId);
        OapiEduUserListResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
        return rsp;
    }

    /**
     * 发送通知
     *
     * @throws ApiException
     */
    public OapiMessageCorpconversationAsyncsendV2Response sendNotification(String userIdList, String content) throws ApiException {
        String accessToken = AccessTokenUtil.getAccessToken();

        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.SEND_NOTI_URL);
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(AppConstant.AGENT_ID);
        request.setUseridList(userIdList);
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent(content);
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, accessToken);
        System.out.println(rsp.getBody());
        return rsp;
    }



}
