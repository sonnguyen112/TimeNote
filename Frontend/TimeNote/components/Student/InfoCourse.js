import React from "react";
import { LogBox, StyleSheet, Text, TextInput, View, Image } from "react-native";
import { Ionicons, Feather } from "@expo/vector-icons";

const InfoCourse = (props) => {
  return (
    <View
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        flex: 1,
        paddingBottom: 5
      }}
    >
      <Text style={{ fontSize: 20, fontWeight: "bold" }}>{props.number}</Text>
      <Text style={{ fontSize: 18, color: "#BDBDBD" }}>{props.name}</Text>
    </View>
  );
};

export default InfoCourse;
