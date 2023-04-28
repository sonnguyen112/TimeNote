import { View, Text, FlatList, ScrollView } from "react-native";
import React from "react";
import axios from "axios";
import { useState, useEffect } from "react";

import StudentItem from "./StudentItem";

import { IDServer } from "../../common/GlobalVariable";

const StudentList = () => {
  const [studentList, setStudentList] = useState([]);

  useEffect(() => {
    axios
      .get(`${IDServer}/api/student/all`)
      .then((response) => {
        let indexStudentList = [...response.data];
        for (let i = 0; i < indexStudentList.length; i++) {
          indexStudentList[i].index = i + 1;
        }
        setStudentList(indexStudentList);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const DATA = [
    {
      id: "bd7acbea-c1b1-46c2-aed5-3ad53abb28ba",
      name: "Nguyễn Hồ Trường Sơn",
      code: "20125112",
      index: 1,
    },
    {
      id: "3ac68afc-c605-48d3-a4f8-fbd91aa97f63",
      name: "Hồ Bảo Trọng",
      code: "20125112",
      index: 2,
    },
    {
      id: "58694a0f-3da1-471f-bd96-145571e29d72",
      name: "Phạm Đình Khôi",
      code: "20125112",
      index: 3,
    },
    {
      id: "58694a0f-3da1-471f-bd96-145571e29d73",
      name: "Hà Thiên Lộc",
      code: "20125113",
      index: 4,
    },
    {
      id: "58694a0f-3da1-471f-bd96-145571e29d74",
      name: "Phạm Đình Khôi Ngô",
      code: "20125112",
      index: 5,
    },
    {
      id: "58694a0f-3da1-471f-bd96-145571e29d78",
      name: "Phạm Đình Khôi AAA",
      code: "20125112",
      index: 6,
    },
  ];

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
          data={studentList}
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

export default StudentList;
