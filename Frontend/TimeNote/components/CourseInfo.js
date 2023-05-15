import React from "react";
import { View, Text, TouchableOpacity, StyleSheet } from "react-native";

const CourseInfo = ({ courseName, courseID, courseClass }) => {
    return (
        <TouchableOpacity style={styles.container}>
            <View style={{marginBottom: 20}}>
                <Text style={styles.coursename}>{courseName}</Text>
            </View>
            <View style={styles.bottomInfo}>
                <View style={styles.textBound}>
                    <Text style={styles.courseid}>{courseID}</Text>
                </View>
                <View style={styles.textBound}>
                    <Text style={styles.courseclass}>{courseClass}</Text>
                </View>
            </View>
        </TouchableOpacity>
    );
};
export default CourseInfo;

const styles = StyleSheet.create({
    container: {
        flexDirection: "column",
        marginTop: 10,
        marginBottom: 10,
        marginRight: 15,
        marginLeft: 15,
        backgroundColor: "#F2EAFC",
        borderRadius: 15,
    },
    text: {
        color: "#b6b6b6",
        fontSize: 11,

    },
    bottomInfo: {
        flexDirection: "row",
        justifyContent: "flex-end",
        marginRight: 10,
        marginBottom: 10,
    },
    textBound: {
        borderRadius: 15,
        marginRight: 5,
        padding: 5,
        backgroundColor: "#fffafa",
    },
    coursename: {
        marginTop: 10,
        marginLeft: 10,
        fontSize: 22
    },
    courseid: {
        fontSize: 15
    },
    courseclass: {
        fontSize: 15
    }
});
