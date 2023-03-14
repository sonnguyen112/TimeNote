import { StatusBar } from "expo-status-bar";
import { LogBox, StyleSheet, Text, TextInput, View } from "react-native";

export default function App() {
  return (
    <View style={styles.container}>
      <View
        style={{ backgroundColor: "#000000", width: 100, height: 100 }}
      ></View>
      <TextInput>aaaaa</TextInput>
      <Text>Hello</Text>
      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
});
