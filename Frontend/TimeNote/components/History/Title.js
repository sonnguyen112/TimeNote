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

const Title = (props) => {
  return (
    <View style={{width:"70%"}}>
        <TouchableOpacity style={{ paddingTop: "5%"}} onPress={() => {props.navigation.goBack()}}>
          <Ionicons name="arrow-back-outline" size={30} color="#F3CE03" />
        </TouchableOpacity>
      <View
        style={{ alignItems: "center", paddingTop: "5%", paddingBottom: "5%"}}
      >
        <Text style={{ fontSize: 28, lineHeight: 35, color: "#464545" }}>
          {props.name}
        </Text>
      </View>
    </View>
  );
};

export default Title;
