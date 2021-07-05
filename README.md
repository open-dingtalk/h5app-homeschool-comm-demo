# 家校沟通demo

> 此demo展示了家校老师或管理人员如何获取家校班级的学生列表，对个体/全体学生发送不同/相同消息的操作，此处以发送放学时间通知举例。
>
> 企业开发时可根据需求开发自己的功能，包括发送待办/消息/图片等。



### 项目结构

**rear-end**：后端模块，springboot构建，钉钉接口功能包括：查询家校部门列表、查询班级人员列表、查询班级人员关系、发送消息通知等。

**front-end**：前端模块，react构建，场景功能包括：免登操作、展示家校部门、展示班级学生、设置放学时间、选中学生、发送放学通知等。



### 开发前配置

**应用首页地址**

此应用访问的首页地址

![image-20210705174313098](https://img.alicdn.com/imgextra/i1/O1CN01rOIwSK1P89d6k2J77_!!6000000001795-2-tps-704-467.png)



**申请权限**

通讯录用户信息读取 / 消息通知 / 待办任务读写 / 新教育-家校通讯录读取

![image-20210705174609056](https://img.alicdn.com/imgextra/i4/O1CN01vSHfwc1iSHjoBIz1M_!!6000000004411-2-tps-1319-533.png)



**创建家校通讯录**

钉钉oa家校通讯录：录入学区/学段/年级/班级、老师、学生、家长等信息

![image-20210705175133732](https://img.alicdn.com/imgextra/i4/O1CN01pFVHDq1Pl5IGbIOon_!!6000000001880-2-tps-894-568.png)



### 运行

**下载项目**

```shell
git clone https://github.com/open-dingtalk/h5app-homeschool-comm-demo.git
```

**修改企业id**

![image-20210705175836260](https://img.alicdn.com/imgextra/i4/O1CN01Mw54Wz1kkGWCn5tlz_!!6000000004721-2-tps-880-395.png)



**react编译打包**

```shell
cd front-end
npm install
npm run build
```

**将编译好的静态资源文件放入后端static文件**

![image-20210705180329831](https://img.alicdn.com/imgextra/i3/O1CN01PWdn6H25d2CDFKe5Y_!!6000000007548-2-tps-331-535.png)



**修改后端配置中的app_key、app_secret、agent_id**

![image-20210705180600483](https://img.alicdn.com/imgextra/i4/O1CN01d2lNes1o7ZWDkSb5g_!!6000000005178-2-tps-860-404.png)



**启动springboot，使用钉钉访问首页地址**



### 页面展示

进入首页自动登录

![image-20210705181408098](https://img.alicdn.com/imgextra/i3/O1CN01OW7d2Y1GczkxijXAe_!!6000000000644-2-tps-333-545.png)

登陆后自动获取并展示学区列表

![image-20210705182026058](https://img.alicdn.com/imgextra/i3/O1CN01whGdoK29mtB9CXIAU_!!6000000008111-2-tps-331-187.png)

点击学区，获取学段列表

![image-20210705182105482](https://img.alicdn.com/imgextra/i2/O1CN01H75Wxo1lwSgC8wakV_!!6000000004883-2-tps-329-149.png)

点击学段，获取年级列表

![image-20210705182148560](https://img.alicdn.com/imgextra/i2/O1CN01Fgpczh1c6P7Tv1UG3_!!6000000003551-2-tps-331-216.png)

点击年级，获取班级列表

![image-20210705181612508](https://img.alicdn.com/imgextra/i1/O1CN01iHkxZC1mB7LtilZQc_!!6000000004915-2-tps-332-207.png)

点击班级，获取学生列表

![image-20210705182241175](https://img.alicdn.com/imgextra/i2/O1CN01kvlqWh1ym0eCKS2XP_!!6000000006620-2-tps-327-240.png)

选中学生，向家长发送放学通知

![image-20210705181741665](https://img.alicdn.com/imgextra/i1/O1CN01FN83xC1QlN9tSSKh1_!!6000000002016-2-tps-327-438.png)

此时家长会收到通知消息

![image-20210705181852303](https://img.alicdn.com/imgextra/i1/O1CN01P1rHhi1oBEgkEEWFq_!!6000000005186-2-tps-327-147.png)



### 参考文档

1. 获取家校通讯录部门列表，文档链接：https://developers.dingtalk.com/document/app/obtains-the-department-node-list
2. 获取家校班级人员列表（学生/老师/家长），文档链接：https://developers.dingtalk.com/document/app/obtains-a-list-of-home-school-user-identities
3. 获取家校班级人员关系列表，文档链接：https://developers.dingtalk.com/document/app/queries-the-list-of-relationships
4. 发送通知，文档链接：https://developers.dingtalk.com/document/app/asynchronous-sending-of-enterprise-session-messages

