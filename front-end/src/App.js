import * as dd from "dingtalk-jsapi"
import axios from "axios"
import React from "react"
import "./App.css"
import { withRouter } from "react-router-dom"
import { message } from "antd"
import Classes from "./components/Classes"
import "antd/dist/antd.min.css"

class App extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      //内网穿透工具介绍:
      // https://developers.dingtalk.com/document/resourcedownload/http-intranet-penetration?pnamespace=app
      // 替换成后端服务域名
      domain: "",
      corpId: "",
      authCode: "",
      userId: "",
      userName: "",
      deptList: [],
      deptId: 0,
      students: [],
      classUserList: [],
      showType: 1,
      className: "",
      classId: "",
      origin: "",
      sendMessage: {
        stuList: [],
        title: "放学通知",
        text: "放学时间为",
        time: "",
        date: "",
      },
    }
  }
  addUser(e) {
    let msg = this.state.sendMessage
    msg.stuList.push({
      id: e.target.value,
      name: e.target.name,
    })
    this.setState({
      sendMessage: msg,
    })
  }
  setTime(date, dateString) {
    let msg = this.state.sendMessage
    msg.time = dateString
    msg.date = date
    this.setState({
      sendMessage: msg,
    })
  }
  sendMsg(e) {
    let msg = this.state.sendMessage
    msg.classId = this.state.classId
    msg.origin = this.state.origin
    delete msg.date
    axios
      .post(this.state.domain + "/homeschool/sendMsg", JSON.stringify(msg), {
        headers: { "Content-Type": "application/json" },
      })
      .then((res) => {
        message.success("放学通知已发出！")
        this.setState({
          sendMessage: {
            stuList: [],
            title: "放学通知",
            text: "放学时间为：",
            time: "",
            date: "",
          },
        })
      })
      .catch((error) => {
        alert("tongzhi err " + JSON.stringify(error))
      })
  }
  render() {
    if (this.state.userId === "") {
      this.login()
    }
    if (this.state.origin === "") {
      let origin = window.location.origin
      this.setState({
        origin: origin,
      })
    }
    let deptOptions
    if (this.state.showType === 0) {
      deptOptions = (
        <div>
          <h3 className="title">选择部门：</h3>
          {/* [
            { name: "111111", deptId: "deptId", deptType: "deptType" },
            { name: "22222", deptId: "deptId2222", deptType: "deptType2222" },
          ] */}
          {this.state.deptList.map((item, i) => (
            <div key={i}>
              <p>
                <a className="AppLink">
                  <span
                    onClick={(e) =>
                      this.chooseDept(e, item.deptId, item.deptType, item.name)
                    }
                  >
                    {item.name}
                  </span>
                </a>
              </p>
            </div>
          ))}
        </div>
      )
    } else {
      deptOptions = <div></div>
    }
    return (
      <div className="App">
        {deptOptions}
        <Classes
          showType={this.state.showType}
          students={this.state.students}
          className={this.state.className}
          addUser={(e) => this.addUser(e)}
          onChange={(e, v) => this.setTime(e, v)}
          date={this.state.sendMessage.date}
          onClick={(e) => this.sendMsg(e)}
        />
      </div>
    )
  }
  chooseDept(e, deptId, deptType, name) {
    if (deptType === "class") {
      this.setState({
        classId: deptId,
        className: name,
      })
      this.getClassUserList(deptId)
    } else {
      this.setState({
        deptId: deptId,
      })
      this.getDeptList(deptId)
    }
  }
  getClassUserList(classId) {
    axios
      .get(this.state.domain + "/homeschool/classUserList?classId=" + classId)
      .then((res) => {
        this.setState({
          students: res.data.data.result.details,
          showType: 1,
        })
      })
      .catch((error) => {
        alert("class err " + JSON.stringify(error))
      })
  }
  getDeptList(deptId) {
    axios
      .get(this.state.domain + "/homeschool/deptList?deptId=" + deptId)
      .then((res) => {
        this.setState({
          deptList: res.data.data.result.details,
          showType: 0,
        })
      })
      .catch((error) => {
        alert("dept err " + JSON.stringify(error))
      })
  }

  login() {
    axios
      .get(this.state.domain + "/getCorpId")
      .then((res) => {
        if (res.data) {
          this.loginAction(res.data)
        }
      })
      .catch((error) => {
        alert("corpId err, " + JSON.stringify(error))
      })
  }
  loginAction(corpId) {
    let _this = this
    dd.runtime.permission.requestAuthCode({
      corpId: corpId, //企业 corpId
      onSuccess: function (res) {
        // 调用成功时回调
        _this.state.authCode = res.code
        axios
          .get(_this.state.domain + "/login?authCode=" + _this.state.authCode)
          .then((res) => {
            if (res && res.data.success) {
              let userId = res.data.data.userId
              let userName = res.data.data.userName
              message.success("登录成功，你好" + userName)
              setTimeout(function () {
                _this.setState({
                  userId: userId,
                  userName: userName,
                })
              }, 0)
              _this.getDeptList(0)
            } else {
              alert("login failed --->" + JSON.stringify(res))
            }
          })
          .catch((error) => {
            alert("httpRequest failed --->" + JSON.stringify(error))
          })
      },
      onFail: function (err) {
        // 调用失败时回调
        alert("requestAuthCode failed --->" + JSON.stringify(err))
      },
    })
  }
}

export default withRouter(App)
