import { View, Text, FlatList, ScrollView } from "react-native";
import React from "react";

import StudentItem from "./StudentItem";


const StudentList = (props) => {

  return (
    <View
      style={{
        flex: 1,
        width: "100%",
        alignItems: "center",
      }}
    >
      <View style={{ marginTop: "3%" }}>
        <Text style={{ fontSize: 30, fontWeight: 400, lineHeight: 37.5 }}>
          Student
        </Text>
      </View>
      <View style={{ marginTop: "3%", width: "100%", minHeight: "100%" }}>
        <FlatList
          data={props.studentList}
          renderItem={({ item }) => (
            <StudentItem
              name={item.studentName}
              code={item.studentCode}
              index={item.index}
            />
          )}
          keyExtractor={(item) => item.studentID}
          ListFooterComponent={<View style={{height: 20}}/>}
        />
      </View>
    </View>
  );
};

export default StudentList;
