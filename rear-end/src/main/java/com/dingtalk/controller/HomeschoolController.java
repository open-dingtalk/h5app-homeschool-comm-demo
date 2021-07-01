package com.dingtalk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.request.OapiEduClassCreateRequest;
import com.dingtalk.api.request.OapiEduDeptListRequest;
import com.dingtalk.api.request.OapiEduGuardianCreateRequest;
import com.dingtalk.api.request.OapiEduSchoolInitRequest;
import com.dingtalk.api.response.*;
import com.dingtalk.model.RpcServiceResult;
import com.dingtalk.service.HomeschoolManager;
import com.dingtalk.util.TimeUtil;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/homeschool")
@RestController
public class HomeschoolController {

    @Autowired
    HomeschoolManager homeschoolManager;

    /**
     * 获取部门列表
     *
     * @param deptId
     * @return
     * @throws ApiException
     */
    @GetMapping("/deptList")
    public RpcServiceResult deptList(@RequestParam("deptId") Long deptId) throws ApiException {
        if(deptId == 0L){
            deptId = null;
        }
        OapiEduDeptListResponse rsp = homeschoolManager.deptList(1L, 30L, deptId);
        return RpcServiceResult.getSuccessResult(rsp);
    }

    /**
     * 获取班级学生
     *
     * @param classId
     * @return
     * @throws ApiException
     */
    @GetMapping("/classUserList")
    public RpcServiceResult getClassUserList(@RequestParam("classId") Long classId) throws ApiException {
        OapiEduUserListResponse rsp = homeschoolManager.eduUserList(1L, 30L, "student" , classId);
        System.out.println(JSON.toJSONString(rsp));
        return RpcServiceResult.getSuccessResult(rsp);
    }

    /**
     * 发送通知
     *
     * @param paramMap
     * @return
     * @throws ApiException
     */
    @PostMapping("/sendMsg")
    public RpcServiceResult sendMsg(@RequestBody Map paramMap) {
        String userList = paramMap.get("stuList").toString();
        String content = paramMap.get("text").toString();
        Long classId = Long.parseLong(paramMap.get("classId").toString());
        List<Map> list = JSONArray.parseArray(userList, Map.class);
        list.forEach(e -> {
            String id = e.get("id").toString();
            String name = e.get("name").toString();
            try {
                OapiEduClassStudentinfoGetResponse rsp = homeschoolManager.getStudentInfo(classId, id);
                List<OapiEduClassStudentinfoGetResponse.OpenPatriarchSelectDto> guardians = rsp.getResult().getGuardians();
                List<String> ids = guardians.stream().map(OapiEduClassStudentinfoGetResponse.OpenPatriarchSelectDto::getUserid).collect(Collectors.toList());
                String useridStr = ids.stream().collect(Collectors.joining(","));
                homeschoolManager.sendNotification(useridStr, name + content);
            } catch (ApiException apiException) {
                apiException.printStackTrace();
            }
        });
        return RpcServiceResult.getSuccessResult(null);
    }

}
