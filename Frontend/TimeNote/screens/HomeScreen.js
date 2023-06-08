import * as React from "react";
import { Text, View } from "react-native";
import {createNativeStackNavigator} from '@react-navigation/native-stack';

import ViewCourse from "./ViewCourse";
import FaceCollectionScreen from "./FaceCollectionScreen";
import LoginScreen from "./LoginScreen"


import Student from "./Student"
const Stack = createNativeStackNavigator();

const HomeScreen = () => {
    
    const [token, setToken] = React.useState("")
    const [role, setRole] = React.useState("")
    return (
        <Stack.Navigator >
            <Stack.Screen name = "Home" options={{headerBackVisible: false}} children={(props) => (<LoginScreen setToken={setToken} setRole={setRole} navigation={props.navigation}/>)}>
                
            </Stack.Screen>
            <Stack.Screen name = "Student" options={{headerBackVisible: false}} component={Student}>
                
            </Stack.Screen>
            <Stack.Screen name = "FaceCollection" component={FaceCollectionScreen}>
            </Stack.Screen>
        </Stack.Navigator>
    );
};

export default HomeScreen;
