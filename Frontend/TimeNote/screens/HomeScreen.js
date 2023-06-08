import * as React from "react";
import { Text, View } from "react-native";
import ViewCourse from "./ViewCourse";
import FaceCollectionScreen from "./FaceCollectionScreen";
import LoginScreen from "./LoginScreen"
import Detail from "./FaceAttendantChecking";

const HomeScreen = () => {
    const [token, setToken] = React.useState("")
    return (
       // <Detail />
       <LoginScreen setToken={setToken}/>
    );
};

export default HomeScreen;
