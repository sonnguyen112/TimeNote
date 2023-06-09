import React from "react";
import {
  LogBox,
  StyleSheet,
  Text,
  TextInput,
  View,
  Button,
  TouchableOpacity,
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import InfoCourse from "./InfoCourse";

const Title = (props) => {
  return (
    <View>
      <View>
        <TouchableOpacity style={{ paddingTop: "5%" }}>
          <Ionicons name="arrow-back-outline" size={30} color="#F3CE03" />
        </TouchableOpacity>
      </View>
      <View
        style={{ alignItems: "center", paddingTop: "5%", paddingBottom: "5%"}}
      >
        <Text style={{ fontSize: 28, lineHeight: 35, color: "#464545" }}>
          {props.name}
        </Text>
      </View>
      <View style={{ display: "flex", justifyContent:"space-evenly", flexDirection:"row", width:"70%"}}>
        <InfoCourse image={"people"} number={"0/45"} name={"Student"} />
        <InfoCourse image={"book"} number={props.code} name={"Course"} />
        <InfoCourse image={"book-open"} number={props.class} name={"Class"} />
      </View>
    </View>
  );
};

export default Title;
