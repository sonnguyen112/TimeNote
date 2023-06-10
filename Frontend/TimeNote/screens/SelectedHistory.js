import React, { useState, useEffect, useRef, useCallback } from "react";
import {
  SafeAreaView,
  Platform,
  View,
  Text
} from "react-native";
import Title from "../components/History/Title";

import { IDServer } from "../common/GlobalVariable";
import HistoryList from "../components/History/HistoryList";

const SelectedHistory = (props) => {
    const [absentStudentList, setAbsentStudentList] = useState([])
    React.useEffect(() => {    
    async function fetchAbsentStudent() {
        const response = await fetch(`${IDServer}attendance_api/course_activation/getting_absent_student_history?course_id=${props.route.params.id}&datetimestr=${props.route.params.his_name.replace(" ", "-")}`,
          {
            method: 'GET',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
              'Authorization': props.route.params.token
            }
          })
          console.log(props.route.params.his_name)
        if (response.status == 200) {
          const data = await response.json()
          let indexAbsentStudentList = [];
          for (let i = 0; i < data.length; i++) {
            indexAbsentStudentList.push({index: i + 1, name: data[i]});
          }
          setAbsentStudentList(indexAbsentStudentList);
        }
      }
      fetchAbsentStudent()
    }, []);
    
    return (
    <SafeAreaView
        style={{
            alignItems: "center",
            paddingTop: Platform.OS === "android" ? 25 : 0,
            paddingBottom: Platform.OS === "android" ? 42 : 0,
            flex: 1
        }}
        >  
        <Title navigation={props.navigation} name={`History`}/>
        <View>
            <Text style={{fontWeight:"bold", marginVertical:5, fontSize:18}}>{props.route.params.his_name}</Text>
        </View>

        <HistoryList historyList={absentStudentList} navigation={null} />
    </SafeAreaView>
    )
}

export default SelectedHistory;