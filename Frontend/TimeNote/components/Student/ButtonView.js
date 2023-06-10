import React from "react";
import {
  ActivityIndicator,
  Text,
  View,
  TouchableOpacity,
} from "react-native";
import { Ionicons, Feather } from "@expo/vector-icons";
import { Touchable } from "react-native";

const ButtonView = (props) => {
  return (
    <View
      style={{
        flexDirection: "row",
        paddingTop: "5%",
        borderBottomWidth: 2,
        marginHorizontal: "10%",
        height: 100,
      }}
    >
      <TouchableOpacity
        style={{
          alignItems: "center",
          justifyContent: "center",
          flexDirection:"row",
          flex: 1,
          backgroundColor: props.attendance ? "rgba(243,206,3,0.5)" : "rgba(243,206,3,1)",
          borderRadius: 15,
          marginRight: "3%",
          height: "80%",
        }}
        onPress={() => {props.onPress();}}
      >
        <Text style={{ fontSize: 18, lineHeight: 22.5, fontWeight: 400 }}>
          Attendance
        </Text>

        <ActivityIndicator size="small" style={{position:"absolute", right:"5%"}} animating={props.attendance}/>
      </TouchableOpacity>

      <TouchableOpacity
        style={{
          alignItems: "center",
          justifyContent: "center",
          flexDirection:"row",
          flex: 1,
          backgroundColor: "rgba(243,206,3,1)",
          borderRadius: 15,
          marginRight: "3%",
          height: "80%",
        }}
      >
        <Text style={{ fontSize: 18, lineHeight: 22.5, fontWeight: 400 }}>
          History
        </Text>
      </TouchableOpacity>
    </View>
  );
};

export default ButtonView;
