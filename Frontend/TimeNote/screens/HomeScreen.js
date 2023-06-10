import * as React from "react";
import { Text, View } from "react-native";
import {createNativeStackNavigator} from '@react-navigation/native-stack';

import ViewCourse from "./ViewCourse";
import ViewCourseTeacher from "./ViewCourseTeacher";
import FaceCollectionScreen from "./FaceCollectionScreen";
import LoginScreen from "./LoginScreen"


import Student from "./Student"
const Stack = createNativeStackNavigator();

const HomeScreen = () => {
    
    const [token, setToken] = React.useState("")
    const [userCode, setUserCode] = React.useState("")
    const [role, setRole] = React.useState("")
    return (
        <Stack.Navigator >
            <Stack.Screen name = "Home" options={{headerShown: false}} children={(props) => (<LoginScreen setToken={setToken} setRole={setRole} setUserCode={setUserCode} navigation={props.navigation}/>)}/>
            <Stack.Screen name = "Student" options={{headerShown: false}} component={Student}/>
            <Stack.Screen name = "CourseTeacher" options={{headerShown: false}} children={(props) => (<ViewCourseTeacher token={token} userCode={userCode} navigation={props.navigation}/>)}/>
            <Stack.Screen name = "FaceCollection" options={{headerShown: false}} component={FaceCollectionScreen}>
            </Stack.Screen>
        </Stack.Navigator>
    );
};

export default HomeScreen;
