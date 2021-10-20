import * as dd from 'dingtalk-jsapi';
import axios from 'axios';
import React from 'react';
import './App.css';
import { withRouter } from 'react-router-dom';

function Classes (props) {
    if (props.showType === 1) {
        return (
            <div>
                <h3>{props.className}</h3>
                <h4>放学通知</h4>
                {
                    props.students.map((item, i) =>
                        <label key={"stu" + i}>
                            <input type="checkbox" value={item.userid} name={item.name} onChange={props.addUser}/>
                            <span>{item.name}</span>
                        </label>
                    )
                }
                <p>放学时间：<input type="date" value={props.time} onChange={props.onChange}/></p>
                <p>
                    <button onClick={props.onClick}>发送放学通知</button>
                </p>
            </div>
        )
    } else {
        return <div></div>
    }
}

function DeptList(props){
    if(props.showType === 0){
        return(
            <div>
                <h3>选择部门：</h3>
                {
                    props.deptList.map((item, i) =>
                        <div key={i}>
                            <p><u><span onClick={(e) => props.chooseDept(e, item.deptId, item.deptType, item.name)}>{item.name}</span></u></p>
                        </div>
                    )
                }
            </div>
        )
    }else{
        return <div></div>
    }
}

class App extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            //内网穿透工具介绍:
            // https://developers.dingtalk.com/document/resourcedownload/http-intranet-penetration?pnamespace=app
            // 替换成后端服务域名
            domain: "",
            corpId: '',
            authCode: '',
            userId: '',
            userName: '',
            deptList:[],
            deptId: 0,
            students: [],
            classUserList: [],
            showType:0,
            className: '',
            classId: '',
            origin: '',
            sendMessage:{
                stuList:[],
                title:"放学通知",
                text:"放学时间为",
                time:"",
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
        alert("time:", e.target.value)
        let msg = this.state.sendMessage;
        msg.time = e.target.value;
        this.setState({
            sendMessage: msg
        })
    }
    sendMsg(e){
        let msg = this.state.sendMessage;
        msg.classId = this.state.classId;
        msg.origin = this.state.origin;
        axios.post(this.state.domain + "/homeschool/sendMsg", JSON.stringify(msg),
            {headers:{"Content-Type":"application/json"}}
        ).then(res => {
                alert("放学通知已发出！");
                this.setState({
                    sendMessage:{
                        stuList:[],
                        title:"放学通知",
                        text:"放学时间为：",
                        time:""
                    },
                })
            }).catch(error => {
            alert("tongzhi err " + JSON.stringify(error))
        })
    }
    render() {
        if(this.state.userId === ''){
            this.login();
        }
        if(this.state.origin === ''){
            let origin = window.location.origin;
            this.setState({
                origin: origin
            })
        }
        let deptOptions;
        if(this.state.showType === 0){
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
        }else{
            deptOptions = <div></div>
        }
        return(
            <div>
                {deptOptions}
                <Classes
                    showType={this.state.showType}
                    students={this.state.students}
                    className={this.state.className}
                    addUser={(e) => this.addUser(e)}
                    onChange={(e) => this.setTime(e)}
                    time={this.state.sendMessage.time}
                    onClick={(e) => this.sendMsg(e)}
                />
            </div>
        )
    }
    chooseDept(e, deptId, deptType, name){
        console.log("chooseDept deptId : " + deptId);
        if(deptType === 'class'){
            this.setState({
                classId: deptId,
                className: name
            });
            this.getClassUserList(deptId);
        }else{
            this.setState({
                deptId: deptId
            })
            this.getDeptList(deptId);
        }
    }
    getClassUserList(classId){
        axios.get(this.state.domain + "/homeschool/classUserList?classId=" + classId)
            .then(res => {
                // alert("class list :" + JSON.stringify(res.data.data.result.details));
                alert("students --- ")
                this.setState({
                    students: res.data.data.result.details,
                    showType:1
                });
                // alert("classUserList --- " + JSON.stringify(this.state.students))
            }).catch(error => {
            alert("class err " + JSON.stringify(error))
        })
    }
    getDeptList(deptId){
        axios.get(this.state.domain + "/homeschool/deptList?deptId=" + deptId)
            .then(res => {
                // alert("dept list :" + JSON.stringify(res.data.data.result.details));
                this.setState({
                    deptList: res.data.data.result.details,
                    showType:0
                });
            }).catch(error => {
            alert("dept err " + JSON.stringify(error))
        })
    }

    login(){
        axios.get(this.state.domain + "/getCorpId")
            .then(res => {
                if(res.data) {
                    this.loginAction(res.data);
                }
            }).catch(error => {
            alert("corpId err, " + JSON.stringify(error))
        })
    }
    loginAction(corpId) {
        // alert("corpId: " +  corpId);
        let _this = this;
        dd.runtime.permission.requestAuthCode({
            corpId: corpId,//企业 corpId
            onSuccess : function(res) {
                // 调用成功时回调
                _this.state.authCode = res.code
                axios.get(_this.state.domain + "/login?authCode=" + _this.state.authCode
                ).then(res => {
                    if (res && res.data.success) {
                        let userId = res.data.data.userId;
                        let userName = res.data.data.userName;
                        alert('登录成功，你好' + userName);
                        setTimeout(function () {
                            _this.setState({
                                userId:userId,
                                userName:userName
                            })
                        }, 0)
                        _this.getDeptList(0);
                    } else {
                        alert("login failed --->" + JSON.stringify(res));
                    }
                }).catch(error => {
                    alert("httpRequest failed --->" + JSON.stringify(error))
                })
            },
            onFail : function(err) {
                // 调用失败时回调
                alert("requestAuthCode failed --->" + JSON.stringify(err))
            }
        });
    }

}

export default withRouter(App)
