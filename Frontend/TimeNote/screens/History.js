import React, { useState, useEffect, useRef, useCallback } from "react";
import {
  SafeAreaView,
  Platform
} from "react-native";
import axios from "axios";
import Title from "../components/History/Title";
import HistoryList from "../components/History/HistoryList"

import { IDServer } from "../common/GlobalVariable";

const History = (props) => {
    const [historyList, setHistoryList] = useState([])

    
  React.useEffect(() => {
    async function fetchHistory() {
      const response = await fetch(`${IDServer}attendance_api/course_activation/getting_course_history?course_id=${props.route.params.id}`,
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
        let indexHistoryList = [];
        for (let i = 0; i < data.length; i++) {
          indexHistoryList.push({index: i + 1, name: data[i]});
        }
        setHistoryList(indexHistoryList);
      }
    }
    fetchHistory()
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
        <Title navigation={props.navigation} name="History"/>
        <HistoryList historyList={historyList} navigation={props.navigation} id={props.route.params.id} token={props.route.params.token}/>
    </SafeAreaView>
    )
}

export default History;