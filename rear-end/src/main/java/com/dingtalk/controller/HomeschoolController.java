package com.dingtalk.controller;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.request.OapiEduClassCreateRequest;
import com.dingtalk.api.request.OapiEduGuardianCreateRequest;
import com.dingtalk.api.request.OapiEduSchoolInitRequest;
import com.dingtalk.api.response.OapiEduClassCreateResponse;
import com.dingtalk.api.response.OapiEduGuardianCreateResponse;
import com.dingtalk.api.response.OapiEduSchoolInitResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.model.RpcServiceResult;
import com.dingtalk.service.HomeschoolManager;
import com.dingtalk.util.TimeUtil;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/homeschool")
@RestController
public class HomeschoolController {

    @Autowired
    HomeschoolManager homeschoolManager;

    /**
     * 初始化家校结构
     *
     * @param paramMap
     * @return
     * @throws ApiException
     */
    @PostMapping("/init")
    public RpcServiceResult initHomeschool(@RequestBody Map paramMap) throws ApiException {
        OapiEduSchoolInitRequest req = new OapiEduSchoolInitRequest();
        // 操作人
        String operator = paramMap.get("operator").toString();
        req.setOperator(operator);

        OapiEduSchoolInitRequest.OpenCampus campus = new OapiEduSchoolInitRequest.OpenCampus();
        // 校区名称
        String name = paramMap.get("name").toString();
        campus.setName(name);

        // 校区信息
        String campusStr = paramMap.get("campus").toString();
        Map campusMap = JSONObject.parseObject(campusStr, Map.class);
        // 学段列表
        String periodsStr = campusMap.get("periods").toString();
        List<Map> periodsList = JSONObject.parseArray(campusStr, Map.class);
        List<OapiEduSchoolInitRequest.OpenPeriod> periods = new ArrayList<>();
        periodsList.forEach(map -> {
            OapiEduSchoolInitRequest.OpenPeriod period = new OapiEduSchoolInitRequest.OpenPeriod();
            String step = map.get("step").toString();
            String periodCode = map.get("periodCode").toString();
            String nameMode = map.get("nameMode").toString();
            String gradesStr = map.get("grades").toString();
            List<OapiEduSchoolInitRequest.OpenGrade> grades = JSONObject.parseArray(gradesStr, OapiEduSchoolInitRequest.OpenGrade.class);
            period.setStep(step);
            period.setPeriodCode(periodCode);
            period.setNameMode(nameMode);
            period.setGrades(grades);
            periods.add(period);
        });
        campus.setPeriods(periods);
        req.setCampus(campus);
        OapiEduSchoolInitResponse rsp = homeschoolManager.initHomeschool(req);
        return RpcServiceResult.getSuccessResult(rsp);
    }

    /**
     * 创建班级
     *
     * @param paramMap
     * @return
     * @throws ApiException
     */
    @RequestMapping("/classCreate")
    public RpcServiceResult classCreate(@RequestBody Map paramMap) throws ApiException {
        OapiEduClassCreateRequest req = new OapiEduClassCreateRequest();
        // 操作人
        String operator = paramMap.get("operator").toString();
        Long superId = Long.parseLong(paramMap.get("superId").toString());
        String openClassStr = paramMap.get("operator").toString();
        OapiEduClassCreateRequest.OpenClass openClass = JSONObject.parseObject(openClassStr, OapiEduClassCreateRequest.OpenClass.class);
        req.setOpenClass(openClass);
        req.setOperator(operator);
        req.setSuperId(superId);
        OapiEduClassCreateResponse rsp = homeschoolManager.classCreate(req);
        return RpcServiceResult.getSuccessResult(rsp);
    }

    /**
     * 添加家长
     *
     * @param paramMap
     * @return
     * @throws ApiException
     */
    @RequestMapping("/guardianCreate")
    public RpcServiceResult guardianCreate(@RequestBody Map paramMap) throws ApiException {
        OapiEduGuardianCreateRequest req = new OapiEduGuardianCreateRequest();
        // 操作人
        String operator = paramMap.get("classId").toString();
        String stuId = paramMap.get("stuId").toString();
        String mobile = paramMap.get("mobile").toString();
        String bizId = paramMap.get("bizId").toString();
        String relation = paramMap.get("relation").toString();
        req.setOperator(operator);
        req.setStuId(stuId);
        req.setMobile(mobile);
        req.setBizId(bizId);
        req.setRelation(relation);
        OapiEduGuardianCreateResponse rsp = homeschoolManager.guardianCreate(req);
        return RpcServiceResult.getSuccessResult(rsp);
    }

    /**
     * 发送通知
     *
     * @param paramMap
     * @return
     * @throws ApiException
     */
    @RequestMapping("/sendNotice")
    public RpcServiceResult sendNotice(@RequestBody Map paramMap) throws ApiException {
        String userIdList = paramMap.get("userIdList").toString();
        String content = paramMap.get("content").toString();
        OapiMessageCorpconversationAsyncsendV2Response rsp = homeschoolManager.sendNotification(userIdList, content);
        return RpcServiceResult.getSuccessResult(rsp);
    }

}
