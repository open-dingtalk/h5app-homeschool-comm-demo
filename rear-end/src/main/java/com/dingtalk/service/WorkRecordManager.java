package com.dingtalk.service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiWorkrecordAddRequest;
import com.dingtalk.api.request.OapiWorkrecordUpdateRequest;
import com.dingtalk.api.response.OapiWorkrecordAddResponse;
import com.dingtalk.api.response.OapiWorkrecordUpdateResponse;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.util.AccessTokenUtil;
import com.dingtalk.util.RandomUtil;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkRecordManager {

    /**
     * 新建学习任务待办
     *
     * @param request
     * @return
     * @throws ApiException
     */
    public OapiWorkrecordAddResponse newLearnToDo(OapiWorkrecordAddRequest request, String title, String content) throws ApiException {
        if (request == null) {
            return null;
        }
        // 获取access_token
        String accessToken = AccessTokenUtil.getAccessToken();

        // 发送请求新建待办任务
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.WORKRECORD_ADD);
        List<OapiWorkrecordAddRequest.FormItemVo> list = new ArrayList<>();
        OapiWorkrecordAddRequest.FormItemVo formItemVo = new OapiWorkrecordAddRequest.FormItemVo();
        formItemVo.setTitle(title);
        formItemVo.setContent(content);
        list.add(formItemVo);
        request.setFormItemList(list);
        request.setPcOpenType(4L);
        String bizId = RandomUtil.getRandomString(10);
        request.setBizId(bizId);
        request.setUrl(request.getUrl() + "?bizId=" + bizId);
        return client.execute(request, accessToken);
    }

    public OapiWorkrecordUpdateResponse updateLearnToDo(String userId, String bizId) throws ApiException {
        // 获取access_token
        String accessToken = AccessTokenUtil.getAccessToken();

        // 更新待办
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.WORKRECORD_UPDATE);
        OapiWorkrecordUpdateRequest req = new OapiWorkrecordUpdateRequest();
        req.setUserid(userId);
        req.setRecordId(bizId);
        OapiWorkrecordUpdateResponse rsp = client.execute(req, accessToken);
        return rsp;
    }
}
