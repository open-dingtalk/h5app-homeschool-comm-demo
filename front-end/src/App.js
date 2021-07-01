import * as dd from 'dingtalk-jsapi';
import axios from 'axios';
import React from 'react';
import './App.css';
import { withRouter } from 'react-router-dom';

class App extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            //内网穿透工具介绍:
            // https://developers.dingtalk.com/document/resourcedownload/http-intranet-penetration?pnamespace=app
            // 替换成后端服务域名
            domain: "http://abcdefg.vaiwan.com",
            corpId: '',
            authCode: '',
            userId: '',
            userName: '',
            deptList:[],
            deptId:0,
            classInfo:{
                classId: '',
                className: '',
                classUserList: [],
                classFlag: false
            },
            sendMessage:{
                classId: '',
                stuList:[],
                text:"放学时间为：",
                time:""
            }
        }
    }
    addUser(e){
        console.log(e.target.value);
        console.log(e.target.name);
        let msg = this.state.sendMessage;
        msg.stuList.push({
            id:e.target.value,
            name:e.target.name
        })
        this.setState({
            sendMessage: msg
        })
    }
    setTime(e){
        console.log("time:", e.target.value)
        let msg = this.state.sendMessage;
        msg.time = e.target.value;
        msg.text += msg.time;
        this.setState({
            sendMessage: msg
        })
    }
    sendMsg(e){
        let msg = this.state.sendMessage;
        msg.classId = this.state.classInfo.classId;
        this.setState({
            sendMessage: msg
        })
        axios.post(this.state.domain + "/homeschool/sendMsg", JSON.stringify(this.state.sendMessage),
            {headers:{"Content-Type":"application/json"}}
        ).then(res => {
                alert("tongzhi：" + JSON.stringify(res));
                this.setState({
                    sendMessage:{
                        classId: '',
                        stuList:[],
                        text:"放学时间为：",
                        time:""
                    },
                })
            }).catch(error => {
            alert(JSON.stringify(error))
        })
    }
    render() {
        if(this.state.userId === ''){
            this.login();
        }

        let deptOptions;
        let showClass;
        if(this.state.classInfo.classFlag){
            showClass =
                <div>
                    <h3>{this.state.classInfo.className}</h3>
                    <h4>放学通知</h4>
                    {
                        this.state.classInfo.classUserList.map((item, i) =>
                            <label>
                                <input type="checkbox" value={item.userid} name={item.name} onChange={(e) => this.addUser(e)}/>
                                <span>{item.name}</span>
                            </label>
                        )
                    }
                    <p><input type="date" value={this.sendMessage.time} onChange={(e) => this.state.setTime(e)}/></p>
                    <p><button onClick={(e) => this.sendMsg(e)}>发送放学通知</button></p>
                </div>
            deptOptions = <div></div>
        }else{
            deptOptions =
                <div>
                    <h3>选择部门：</h3>
                    {
                        this.state.deptList.map((item, i) =>
                            <div key={i}>
                                <p><u><span onClick={(e) => this.chooseDept(e, item.deptId, item.deptType, item.name)}>{item.name}</span></u></p>
                            </div>
                        )
                    }
                </div>
            showClass = <div></div>
        }
        return(
            <div>
                {deptOptions}
                {showClass}
            </div>
        )
    }
    chooseDept(e, deptId, deptType, name){
        if(deptType === 'class'){
            this.getClassUserList(deptId,name);
        }else{
            this.setState({
                deptId: deptId
            })
            this.getDeptList();
        }
    }
    getClassUserList(classId,name){
        axios.get(this.state.domain + "/homeschool/classUserList?classId=" + classId)
            .then(res => {
                alert("班级学生：" + JSON.stringify(res.data.data.result.details));
                this.setState({
                    classInfo:{
                        classId: classId,
                        className: name,
                        classUserList: res.data.data.result.details,
                        classFlag: true
                    },
                })
            }).catch(error => {
            alert(JSON.stringify(error))
        })
    }
    getDeptList(){
        axios.get(this.state.domain + "/homeschool/deptList?deptId=" + this.state.deptId)
            .then(res => {
                alert("dept list :" + JSON.stringify(res.data.data.result.details));
                this.setState({
                    deptList: res.data.data.result.details,
                })
            }).catch(error => {
            alert(JSON.stringify(error))
        })
    }

    login() {
        let _this = this;
        dd.runtime.permission.requestAuthCode({
            corpId: "ding9f50b15bccd16741",//企业 corpId
            onSuccess : function(res) {
                // 调用成功时回调
                _this.state.authCode = res.code
                axios.get(_this.state.domain + "/login?authCode=" + _this.state.authCode
                ).then(res => {
                    if (res && res.data.success) {
                        let userId = res.data.data.userId;
                        let userName = res.data.data.userName;
                        alert('登陆成功，你好，' + userName);
                        _this.setState({
                            userId:userId,
                            userName:userName
                        })
                        _this.getDeptList();
                    } else {
                        alert("httpRequest failed --->", res);
                    }
                }).catch(error => {
                    alert(JSON.stringify(error))
                })
            },
            onFail : function(err) {
                // 调用失败时回调
                alert(JSON.stringify(err))

            }
        });
    }

}

export default withRouter(App)
