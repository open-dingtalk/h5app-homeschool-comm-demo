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



### 运行前准备

 下载demo

```shell
git clone https://github.com/open-dingtalk/h5app-scene-group-demo.git
```

### 获取相应参数

获取到以下参数，修改后端application.yaml

```yaml
app:
  app_key: *****
  app_secret: *****
  agent_id: *****
  corp_id: *****
```

参数获取方法：登录开发者后台

1. 获取corpId：https://open-dev.dingtalk.com/#/index
2. 进入应用开发-企业内部开发-点击进入应用-基础信息-获取appKey、appSecret、agentId

### 修改前端页面

**打开项目，命令行中执行以下命令，编译打包生成build文件**

```shell
cd front-end
npm install
npm run build
```

**将打包好的静态资源文件放入后端**

![image-20210705180329831](https://img.alicdn.com/imgextra/i2/O1CN01QLp1Qw1TCVrPddfjZ_!!6000000002346-2-tps-322-521.png)

### 启动项目

- 启动springboot
- 移动端钉钉点击工作台，找到创建的应用，进入应用

### 页面展示

登陆后自动获取并展示学区列表

![image-20210705182026058](https://img.alicdn.com/imgextra/i2/O1CN01FI2zOp1QLj0JQqjVp_!!6000000001960-2-tps-445-364.png)

点击学区，获取学段列表

![image-20210705182105482](https://img.alicdn.com/imgextra/i3/O1CN01Glcjxi1G61GZQS8eS_!!6000000000572-2-tps-445-230.png)

点击学段，获取年级列表

![image-20210705182148560](https://img.alicdn.com/imgextra/i3/O1CN01NEZSXi28LZyM1C79c_!!6000000007916-2-tps-446-368.png)

点击年级，获取班级列表

![image-20210705181612508](https://img.alicdn.com/imgextra/i1/O1CN01JoBciI29APw7JnEBX_!!6000000008027-2-tps-449-180.png)

点击班级，选中学生发送通知

![image-20210705182241175](https://img.alicdn.com/imgextra/i4/O1CN01RWTqfB1wTZUlMXItM_!!6000000006309-2-tps-445-403.png)

家长收到通知消息

![image-20210705181852303](https://img.alicdn.com/imgextra/i1/O1CN01P1rHhi1oBEgkEEWFq_!!6000000005186-2-tps-327-147.png)



### 参考文档

1. 获取家校通讯录部门列表，文档链接：https://developers.dingtalk.com/document/app/obtains-the-department-node-list
2. 获取家校班级人员列表（学生/老师/家长），文档链接：https://developers.dingtalk.com/document/app/obtains-a-list-of-home-school-user-identities
3. 获取家校班级人员关系列表，文档链接：https://developers.dingtalk.com/document/app/queries-the-list-of-relationships
4. 发送通知，文档链接：https://developers.dingtalk.com/document/app/asynchronous-sending-of-enterprise-session-messages

