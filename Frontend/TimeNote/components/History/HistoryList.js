import { View, Text, FlatList, ScrollView } from "react-native";
import React from "react";

import StudentItem from "./HistoryItem";


const HistoryList = (props) => {

  return (
    <View
      style={{
        flex: 1,
        width: "100%",
        alignItems: "center",
      }}
    >
      <View style={{ marginTop: "3%", width: "100%", minHeight: "100%" }}>
        <FlatList
          data={props.historyList}
          renderItem={({ item }) => (
            <StudentItem
              name={item.studentName}
              code={item.studentCode}
              index={item.index}
            />
          )}
          keyExtractor={(item) => item.studentID}
        />
      </View>
    </View>
  );
};

export default HistoryList;
