import React, { useState, useEffect, useRef, useCallback } from "react";
import {
    ActivityIndicator,
    RefreshControl,
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
import * as Location from "expo-location";
import CourseInfo from "../components/CourseInfo";
import "./global.js"

// DATA = [
//     {
//         courseDetailId: 1,
//         courseCode: "CS000",
//         courseName: "Name of Course 0",
//         courseClass: "20CTT",
//     },
// ];

const getCurrentDate = () => {
    var date = new Date().getDate();
    var month = new Date().getMonth() + 1;
    var year = new Date().getFullYear();

    //Alert.alert(date + '-' + month + '-' + year);
    // You can turn it in to your desired format
    return date;
};

const ViewCourse = () => {
    const renderHeader = () => (
        <View>
            <Text>Course</Text>
        </View>
    );

    const generateExt = (userCode, longtitude, latitude) => {
        return (
            "userCode=" +
            String(userCode) +
            "&longitude=" +
            longtitude.toString() +
            "&latitude=" +
            latitude.toString()
        );
    };

    const courseOnClick = (courseDetailId) => {
        
    }

    console.log(global.NGROK)
    const getCourseList = async () => {
        let response = await fetch(
            global.NGROK +
                "course_api/course_detail?" +
                generateExt(
                    20125000,
                    location.coords.longitude,
                    location.coords.latitude
                ),
            {
                method: "GET",
                headers: {
                    Authorization: global.token,
                    // 'Content-Type': 'application/x-www-form-urlencoded',
                },
            }
        );
        let json = await response.json();
        if (response.status === 200) {
            setCourseList(json);
            setLoading(false);
        }
    };

    const getLocation = async () => {
        let { status } = await Location.requestForegroundPermissionsAsync();
        if (status !== "granted") {
            setErrorMsg("Permission to access location was denied");
            return;
        }

        let location = await Location.getCurrentPositionAsync({});
        setLocation(location);
    };

    const [loading, setLoading] = useState(true);
    const [refreshing, setRefreshing] = useState(false);

    const [courseList, setCourseList] = useState([]);
    const [location, setLocation] = useState(null);
    const [errorMsg, setErrorMsg] = useState(null);

    const onRefresh = useCallback(() => {
        setRefreshing(true);
        getLocation();
    }, []);

    useEffect(() => {
        getLocation();
    }, []);

    useEffect(() => {
        console.log(location);

        if (location !== null) {
            getCourseList().catch(console.error);
        }
        if (refreshing === true) {
            setRefreshing(false);
        }
    }, [location]);
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
            {/* <Modalize
                handleStyle={{
                    marginTop: 30,
                    backgroundColor: "#e9e9e9",
                    width: 80,
                }}
                modalStyle={{
                    borderTopLeftRadius: 40,
                    borderTopRightRadius: 40,
                }}
                alwaysOpen={600}
                scrollViewProps={{ showsVerticalScrollIndicator: false }}
                adjustToContentHeight={DATA.length >= 3 ? false : true }>
                {/* <View style={{ marginTop: 40 }}>
                    {DATA.map((item, index) => (
                        <CourseInfo
                            courseClass={item.courseClass}
                            courseID={item.courseID}
                            courseName={item.courseName}
                            // onClick={() => props.onClick(index)}
                            >
                            </CourseInfo>
                    ))}
                </View>}
            </Modalize> */}
            <View style={styles.ops}>
                <View style={styles.col}>
                    <Text style={styles.courseHeader}>Course</Text>
                </View>
                <ScrollView
                    refreshControl={
                        <RefreshControl
                            refreshing={refreshing}
                            onRefresh={onRefresh}
                        />
                    }>
                    {loading ? (
                        <ActivityIndicator
                            style={styles.list}
                            size="large"
                            color="#8e2de2"
                        />
                    ) : (
                        <Animated.View style={styles.list}>
                            {courseList.map((item, index) => (
                                <CourseInfo
                                    key={item.courseDetailId}
                                    courseClass={item.courseClass}
                                    courseID={item.courseCode}
                                    courseName={item.courseName}
                                    onClick={() => courseOnClick(item.courseDetailId)}
                                ></CourseInfo>
                            ))}
                        </Animated.View>
                    )}
                </ScrollView>
            </View>
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
    courseHeader: {
        color: "#0f0f0f",
        flex: 1,
        fontSize: 24,
        marginBottom: 10,
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
        marginTop: 20,
        marginHorizontal: 20,
        alignItems: "center",
    },
    row: {
        marginTop: 5,
        alignSelf: "center",
        opacity: 0.7,
    },
});
