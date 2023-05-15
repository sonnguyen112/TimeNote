import React, { useState, useEffect, useRef } from "react";
import {
    View,
    Text,
    StyleSheet,
    Animated,
    ImageBackground,
} from "react-native";
import { LinearGradient } from "expo-linear-gradient";
import { FontAwesome5 } from "@expo/vector-icons";
import { ScrollView } from "react-native-gesture-handler";
import { Modalize } from "react-native-modalize";
import CourseInfo from "../components/CourseInfo";

const getCurrentDate = () => {
    var date = new Date().getDate();
    var month = new Date().getMonth() + 1;
    var year = new Date().getFullYear();

    //Alert.alert(date + '-' + month + '-' + year);
    // You can turn it in to your desired format
    return date;
};

const ViewCourse = (props) => {
    return (
        <LinearGradient colors={["#8e2de2", "#4a00e0"]} style={styles.gradient}>
            <View style={styles.headerContainer}>
                <Text style={styles.header}>Hi, Bao</Text>
                <FontAwesome5 name="react" size={50} color="#2B65EC" />
            </View>
            <View style={styles.card}>
                <FontAwesome5 name="react" size={50} color="black" />
                <FontAwesome5 name="react" size={50} color="black" />
            </View>
            <ScrollView
                horizontal
                style={styles.proContainer}
                showsHorizontalScrollIndicator={false}>
                <Animated.View style={styles.card}></Animated.View>
            </ScrollView>
            <Modalize
                handleStyle={{
                    marginTop: 30,
                    backgroundColor: "#e9e9e9",
                    width: 80,
                }}
                modalStyle={{
                    borderTopLeftRadius: 15,
                    borderTopRightRadius: 15,
                }}
                alwaysOpen={500}
                scrollViewProps={{ showsVerticalScrollIndicator: false }}>
                <View style={{ marginTop: 40 }}>
                    <CourseInfo
                        courseName="Algorithm and complexity"
                        courseID="CS100"
                        courseClass="20CTT"
                    />
                    <CourseInfo
                        courseName="Algorithm and complexity"
                        courseID="CS100"
                        courseClass="20CTT"
                    />
                    <CourseInfo
                        courseName="Algorithm and complexity"
                        courseID="CS100"
                        courseClass="20CTT"
                    />
                    <CourseInfo
                        courseName="Algorithm and complexity"
                        courseID="CS100"
                        courseClass="20CTT"
                    />
                    <CourseInfo
                        courseName="Algorithm and complexity"
                        courseID="CS100"
                        courseClass="20CTT"
                    />
                    <CourseInfo
                        courseName="Algorithm and complexity"
                        courseID="CS100"
                        courseClass="20CTT"
                    />
                    <CourseInfo
                        courseName="Algorithm and complexity"
                        courseID="CS100"
                        courseClass="20CTT"
                    />
                    <CourseInfo
                        courseName="Algorithm and complexity"
                        courseID="CS100"
                        courseClass="20CTT"
                    />
                    <CourseInfo
                        courseName="Algorithm and complexity"
                        courseID="CS100"
                        courseClass="20CTT"
                    />
                    <CourseInfo
                        courseName="Algorithm and complexity"
                        courseID="CS100"
                        courseClass="20CTT"
                    />
                    <CourseInfo
                        courseName="Algorithm and complexity"
                        courseID="CS100"
                        courseClass="20CTT"
                    />
                </View>
            </Modalize>
        </LinearGradient>
    );
};
export default ViewCourse;

const styles = StyleSheet.create({
    list: {
        marginTop: 30,
    },
    image: {
        flex: 1,
        justifyContent: "center",
    },
    card: {
        marginLeft: 400,
        width: 400,
        flexDirection: "row",
    },
    gradient: {
        height: "100%",
        position: "absolute",
        left: 0,
        right: 0,
        top: 0,
        paddingHorizontal: 20,
        paddingTop: 30,
    },
    headerContainer: {
        flexDirection: "row",
        alignItems: "center",
        paddingTop: 20,
    },
    header: {
        color: "#FFF",
        flex: 1,
        fontSize: 24,
    },
    proContainer: {
        marginRight: -20,
        alignSelf: "center",
    },
    ops: {
        borderTopLeftRadius: 40,
        borderTopRightRadius: 40,
        height: 500,
        backgroundColor: "#FFF",
        marginHorizontal: -20,
    },
    col: {
        flexDirection: "row",
        marginTop: 25,
        marginHorizontal: 20,
        alignItems: "center",
    },
    row: {
        marginTop: 5,
        alignSelf: "center",
        opacity: 0.7,
    },
});