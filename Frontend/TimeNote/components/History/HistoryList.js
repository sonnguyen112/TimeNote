import { View, Text, FlatList, ScrollView } from "react-native";
import React from "react";

import HistoryItem   from "./HistoryItem";


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
            <HistoryItem
              name={item.name}
              code=""
              index={item.index}
              navigation={props.navigation}
              course_id={props.id}
              token = {props.token}
            />
          )}
          keyExtractor={(item) => item.index}
        />
      </View>
    </View>
  );
};

export default HistoryList;
