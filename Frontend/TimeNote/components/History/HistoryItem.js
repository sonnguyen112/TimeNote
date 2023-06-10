import React from "react";
import {
  LogBox,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from "react-native";

const HistoryItem = ({ name, code, index, navigation, course_id, token}) => {
  let background;
  let numberColor;

  if (index % 2 !== 0) {
    background = "rgba(165, 111, 233, 0.15)";
    numberColor = "white";
  } else {
    backgroundColor = "white";
    numberColor = "rgba(165, 111, 233, 0.15)";
  }

  return (
    <TouchableOpacity
      style={{
        backgroundColor: background,
        width: "100%",
        flexDirection: "row",
        height: 75,
        alignItems: "center",
      }}
      onPress={() => {
        if (navigation !== null)
            navigation.navigate("SelectedHistory", {id:course_id, his_name:name, token:token})
      }}
    >
      <View style={{ width: "20%", alignItems: "center", justifyContent: "center" }}>
        <View
          style={{
            backgroundColor: numberColor,
            alignItems: "center",
            justifyContent: "center",
            width: 50,
            height: 50,
            borderRadius: 25,
            marginTop: "5%",
            marginLeft: "5%",
          }}>
          <Text style={{ fontSize: 30, fontWeight: 400, lineHeight: 37.5 }}>
            {index}
          </Text>
        </View>
      </View>
      <View style={{display:"flex", alignItems:"center", justifyContent:"center", flexDirection:'column', height:"100%"}}>
        <View >
          <Text style={{ fontSize: 18, fontWeight: "bold", lineHeight: 22.5, marginLeft:10}}>
            {name}
          </Text>
        </View>
      </View>
    </TouchableOpacity>
  );
};

export default HistoryItem;
