import React, { useState, useEffect, useRef, useCallback } from "react";
import {
  SafeAreaView,
  Platform
} from "react-native";
import axios from "axios";
import Title from "../components/Student/Title";
import ButtonView from "../components/Student/ButtonView";
import StudentList from "../components/Student/StudentList";

import { IDServer } from "../common/GlobalVariable";


const Student = (props) => {
  const [studentList, setStudentList] = React.useState([]);
  const [studentAbsentList, setStudentAbsentList] = React.useState([]);
  const intervalRef = useRef();
  const [numStudent, setNumStudent] = React.useState(0)
  const [attendance, setAttendance] = React.useState(false)


  const generateExt = (courseID, longtitude, latitude) => {
    return (
      "course_id=" +
      String(courseID) +
      "&longitude=" +
      longtitude.toString() +
      "&latitude=" +
      latitude.toString()
    );
  };
  async function fetchAttendance() {
    console.log("press button")
    const response = await fetch(`${IDServer}attendance_api/course_activation/?${generateExt(props.route.params.id, props.route.params.location.coords.longitude,
      props.route.params.location.coords.latitude)}`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': props.route.params.token
        }
      })
    if (response.status == 200) {
      const json = await response.json()
      setAttendance(json.is_active)
      console.log("success atten", json.is_active)
    }
  }
  function pressAttendance() {
    fetchAttendance()
    
  }

  useEffect(()=>{
    if (attendance)
    {
    intervalRef.current = setInterval(()=> fetchAbsentStudent(), 1000)
    return () => {clearInterval(intervalRef.current)}
    }
    else
    {
      clearInterval(intervalRef.current)
    }
  },[attendance])



  async function fetchAbsentStudent() {

    const response = await fetch(`${IDServer}attendance_api/course_activation/get_student_absent?course_id=${props.route.params.id}`,
      {
        method: 'GET',
        headers: {
          // 'Accept': 'application/json',
          // 'Content-Type': 'application/json',
          'Authorization': props.route.params.token
        }
      })
    console.log(123)
    if (response.status == 200) {
      const data_id = await response.json()
      console.log(data_id)
      const newListStudent = []
      data_id.map((id) => {
        newListStudent.push(studentList.find((element) => { return element.studentCode === id.student_id }))
      })
      setStudentAbsentList(newListStudent)
      setNumStudent(newListStudent.length)
    }
    
  }

  React.useEffect(() => {
    async function fetchStudent() {
      const response = await fetch(`${IDServer}course_api/student?course_detail_id=${props.route.params.id}`,
        {
          method: 'GET',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': props.route.params.token
          }
        })

      if (response.status == 200) {
        const data = await response.json()
        let indexStudentList = [...data];
        for (let i = 0; i < indexStudentList.length; i++) {
          indexStudentList[i].index = i + 1;
        }
        setStudentList(indexStudentList);
      }
    }
    fetchStudent()
  }, []);

  return (
    <SafeAreaView
      style={{
        alignItems: "center",
        paddingTop: Platform.OS === "android" ? 25 : 0,
        paddingBottom: Platform.OS === "android" ? 42 : 0,
        flex: 1,
      }}
    >
      <Title navigation={props.navigation} name={props.route.params.name} class={props.route.params.class} code={props.route.params.code} numStu={`${attendance ? String(studentList.length - numStudent) + '/' : ''}${props.route.params.numStu}`} id={props.route.params.id} />
      <ButtonView onPress={pressAttendance} attendance={attendance} navigation={props.navigation} id={props.route.params.id} token={props.route.params.token}/>
      <StudentList studentList={attendance ? studentAbsentList : studentList} />
    </SafeAreaView>
  );
};

export default Student;
