import react from "react"
import { Checkbox, DatePicker, Button } from "antd"

const Classes = (props) => {
  const onChange = (date, dateString) => {
    props.onChange(date, dateString)
  }
  if (props.showType === 1) {
    return (
      <div>
        <h3 className="title">{props.className}</h3>
        <h4 className="littleTitle">放学通知</h4>
        {/*[
          { name: "11111", userid: "22222" },
          { name: "33333", userid: "4444" },
        ]  */}
        {props.students.map((item, i) => (
          <div className="radioList">
            <Checkbox
              value={item.userid}
              name={item.name}
              onChange={props.addUser}
            >
              {item.name}
            </Checkbox>
          </div>
        ))}
        <p>
          放学时间：
          <DatePicker
            onChange={onChange}
            value={props.date}
            placeholder="请选择时间"
          />
        </p>
        <br />
        <p>
          <Button onClick={props.onClick} type="primary">
            发送放学通知
          </Button>
        </p>
      </div>
    )
  } else {
    return <div></div>
  }
}

export default Classes
