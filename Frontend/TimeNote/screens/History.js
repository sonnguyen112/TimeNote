import React, { useState, useEffect, useRef, useCallback } from "react";
import {
  SafeAreaView,
  Platform
} from "react-native";
import axios from "axios";
import Title from "../components/History/Title";
import HistoryList from "..components/History/HistoryList"

import { IDServer } from "../common/GlobalVariable";

const History = (props) => {
    const [historyList, setHistoryList] = useState([])

    return (
    <SafeAreaView
    style={{
        alignItems: "center",
        paddingTop: Platform.OS === "android" ? 25 : 0,
        paddingBottom: Platform.OS === "android" ? 42 : 0,
        flex: 1
    }}
    >  
        <Title navigation={props.navigation}/>
        <HistoryList historyList={historyList} />
    </SafeAreaView>
    )
}

export default History;