import React from "react";
import {
  LogBox,
  StyleSheet,
  Text,
  TextInput,
  View,
  SafeAreaView,
  Platform,
} from "react-native";
import Title from "../components/Student/Title";
import ButtonView from "../components/Student/ButtonView";
import StudentList from "../components/Student/StudentList";

const Student = () => {
  return (
    <SafeAreaView
      style={{
        alignItems: "center",
        paddingTop: Platform.OS === "android" ? 25 : 0,
        paddingBottom: Platform.OS === "android" ? 42 : 0,
        flex: 1,
      }}
    >
      <Title />
      <ButtonView />
      <StudentList />
    </SafeAreaView>
  );
};

export default Student;
