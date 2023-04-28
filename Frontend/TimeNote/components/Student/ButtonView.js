import React from "react";
import {
  LogBox,
  StyleSheet,
  Text,
  TextInput,
  View,
  Image,
  Button,
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
          flex: 1,
          backgroundColor: "#F3CE03",
          borderRadius: 15,
          marginRight: "3%",
          height: "80%",
        }}
      >
        <Text style={{ fontSize: 18, lineHeight: 22.5, fontWeight: 400 }}>
          Attendance
        </Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={{
          alignItems: "center",
          justifyContent: "center",
          flex: 1,
          backgroundColor: "#F3CE03",
          borderRadius: 15,
          height: "80%",
          marginLeft: "3%",
        }}
      >
        <Text style={{ fontSize: 18, lineHeight: 22.5, fontWeight: 400 }}>
          Report
        </Text>
      </TouchableOpacity>
    </View>
  );
};

export default ButtonView;
