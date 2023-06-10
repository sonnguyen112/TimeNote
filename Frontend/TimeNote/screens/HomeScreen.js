import * as React from "react";
import { Text, View } from "react-native";
import {createNativeStackNavigator} from '@react-navigation/native-stack';

import ViewCourse from "./ViewCourse";
import ViewCourseTeacher from "./ViewCourseTeacher";
import Detail from "./FaceAttendantChecking";
import History from "./History";
import LoginScreen from "./LoginScreen";


import "./global.js"


import Student from "./Student"
import SelectedHistory from "./SelectedHistory";
const Stack = createNativeStackNavigator();

const HomeScreen = () => {
    
    const [token, setToken] = React.useState("")
    const [userCode, setUserCode] = React.useState("")
    const [role, setRole] = React.useState("")
    return (
        <Stack.Navigator >
            <Stack.Screen name = "Home" options={{headerShown: false}} children={(props) => (<LoginScreen setToken={setToken} setRole={setRole} setUserCode={setUserCode} navigation={props.navigation}/>)}/>
            <Stack.Screen name = "Student" options={{headerShown: false}} component={Student}/>
            <Stack.Screen name = "History" options={{headerShown: false}} component={History}/>
            <Stack.Screen name = "SelectedHistory" options={{headerShown: false}} component={SelectedHistory}/>
            <Stack.Screen name = "CourseTeacher" options={{headerShown: false}} children={(props) => (<ViewCourseTeacher token={token} userCode={userCode} navigation={props.navigation}/>)}/>
            <Stack.Screen name = "CourseStudent" options={{headerShown: false}} children={(props) => (<ViewCourse token={token} userCode={userCode} navigation={props.navigation}/>)}/>
            <Stack.Screen name = "CourseDetailAuth" options={{headerShown: false}} children={(props) => (<Detail token={token} userCode={userCode} navigation={props.navigation}/>)}/>
        </Stack.Navigator>
    );
};

export default HomeScreen;
