import React, { Component, useEffect, useState, useRef } from "react";
import { StyleSheet, Text, View, Pressable } from "react-native";
import FontAwesome from "@expo/vector-icons/FontAwesome";
import { MaterialCommunityIcons } from "@expo/vector-icons";
import Feather from "@expo/vector-icons/Feather";
import { TouchableOpacity } from "react-native-gesture-handler";
import { Camera } from "expo-camera";
import * as MediaLibrary from "expo-media-library";

// import * as FaceDetector from "expo-face-detector";
// import { Video, ResizeMode } from "expo-av";
// import { Image } from "expo-image"
// import video from "../images/facescan.mp4";
import "./global.js";

const blurhash =
    "|rF?hV%2WCj[ayj[a|j[az_NaeWBj@ayfRayfQfQM{M|azj[azf6fQfQfQIpWXofj[ayj[j[fQayWCoeoeaya}j[ayfQa{oLj?j[WVj[ayayj[fQoff7azayj[ayj[j[ayofayayayj[fQj[ayayj[ayfjj[j[ayjuayj[";

const Detail = (props) => {
    const cameraRef = useRef();

    const [photo, setPhoto] = useState(null);
    const [hasCameraPermission, setHasCameraPermission] = useState(null);
    const [hasMediaLibraryPermission, setHasMediaLibraryPermission] =
        useState();
    const [camera, setCamera] = useState(null);
    const [image, setImage] = useState(null);
    const [type, setType] = useState(Camera.Constants.Type.front);
    const [faceData, setFaceData] = useState(null);
    const [authFinish, setAuthFinish] = useState(false);
    const [startSendPicture, setStartSendPicture] = useState(2);
    /*
    startSendPicture:
        0: verified
        1: unverified -> stop -> back to home
        > 1: take picture
    */

    const [isFind, setIsFind] = useState(null);
    const [isReal, setIsReal] = useState(null);
    const [countSend, setCountSend] = useState(0);
    const [isShow, setIsShow] = useState(-1);

    const hideDialog = () => {
        setIsShow(isShow - 1);
    };

    const handleFacesDetected = ({ faces }) => {
        setFaceData(faces);
        console.log(faces);
    };

    const __take_picture = async () => {
        setPhoto(null);
        let options = {
            quality: 1,
            base64: true,
            exif: false,
            skipProcessing: false,
        };

        let newPhoto = await cameraRef.current.takePictureAsync(options);
        setPhoto(newPhoto);
    };

    const sendFaceCheck = async (_photo) => {
        let response = await fetch(
            global.NGROK + "attendance_api/face_recognize/",
            {
                method: "POST",
                headers: {
                    Authorization: props.token,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    img_base64: _photo.base64,
                    student_id: props.userCode,
                }),
            }
        );
        let json = await response.json();
        if (response.status === 200) {
            console.log(json.is_find, json.is_real);
            setIsFind(json.is_find);
            setIsReal(json.is_real);
            if (startSendPicture > 1) setStartSendPicture(startSendPicture + 1);
        }
        console.log(response.status, "hihihi");
        console.log(global.NGROK + "attendance_api/face_recognize/");
    };

    const generateExt = (student_code) => {
        return "student_code=" + student_code;
    };

    const confirmAttend = async () => {
        let response = await fetch(
            global.NGROK +
                "attendance_api/course_activation/checking_student?" +
                generateExt(props.userCode),
            {
                method: "GET",
                headers: {
                    Authorization: props.token,
                    // "Content-Type": "application/json",
                },
                // body: JSON.stringify({
                //     img_base64: _photo.base64,
                //     student_id: global.student_id,
                // }),
            }
        );
        let json = await response.json();
        if (response.status === 200) {
            print("heheee");
        }
    };

    useEffect(() => {
        (async () => {
            const cameraPermission =
                await Camera.requestCameraPermissionsAsync();
            const mediaLibraryPermission =
                await MediaLibrary.requestPermissionsAsync();
            setHasCameraPermission(cameraPermission.status === "granted");
            setHasMediaLibraryPermission(
                mediaLibraryPermission.status === "granted"
            );
        })();
    }, []);

    useEffect(() => {
        console.log("grrr: ", isReal, isFind);
        if (isReal && isFind) {
            setStartSendPicture(0);
            setAuthFinish(true);
            confirmAttend();
            
        } else {
            setCountSend(countSend + 1);
        }
    }, [isFind, isReal]);

    useEffect(() => {
        console.log("Count Send: ", countSend);
        if (countSend === 10) {
            setStartSendPicture(1);
        }
    }, [countSend]);

    useEffect(() => {
        if (photo) {
            console.log("Co Hinh!");
            sendFaceCheck(photo);
        }
    }, [photo]);

    // is_real: bool
    // is_find: bool
    useEffect(() => {
        if (!(isReal && isFind)) {
            const interval = setTimeout(() => __take_picture(), 1000);

            if (startSendPicture === 0 || startSendPicture === 1) {
                console.log("Check OK!");
                clearTimeout(interval);
            }

            return () => clearTimeout(interval);
        }
    }, [startSendPicture]);

    useEffect(() => {
        if(authFinish) {
            setIsShow(2);
        }
    }, [authFinish])

    useEffect(() => {
        const interval = setTimeout(() => hideDialog(), 500);

        if (isShow === 0) {
            clearTimeout(interval);
            props.navigation.navigate("CourseStudent");
        }

        return () => clearTimeout(interval);
    }, [isShow]);

    if (hasCameraPermission === false) {
        return <Text>No Camera Access</Text>;
    }

    return (
        <View style={styles.container}>
            <View style={styles.header}>
                <TouchableOpacity onPress={() => props.navigation.goBack()}>
                    <Feather name="chevron-left" color="#f0f0f0" size={25} />
                </TouchableOpacity>
            </View>
            {/* <View>
            {/* {photo !== null ? <Image
                    style={styles.photoCam}
                    source={{ uri: "data:image/jpg;base64," + photo.base64 }}
                    placeholder={blurhash}
                    contentFit="cover"
                />:}
                <Image
                    style={styles.photoCam}
                    source="https://cdn.dribbble.com/users/3496409/screenshots/7749099/scanner.gif"
                    placeholder={blurhash}
                    contentFit="cover"
                />
            </View> */}
            <View
                style={{
                    flex: 1,
                    width: "100%",
                    flexDirection: "column",
                    backgroundColor: "black",
                    borderRadius: 25,
                }}>
                <Camera
                    type={Camera.Constants.Type.front}
                    ref={cameraRef}
                    style={styles.camera}
                    whiteBalance={0}
                    // onFacesDetected={handleFacesDetected}
                    // faceDetectorSettings={{
                    //     mode: FaceDetector.FaceDetectorMode.fast,
                    //     detectLandmarks:
                    //         FaceDetector.FaceDetectorLandmarks.none,
                    //     runClassifications:
                    //         FaceDetector.FaceDetectorClassifications.all,
                    //     minDetectionInterval: 100,
                    //     tracking: true,}}
                    onCameraReady={__take_picture}></Camera>
            </View>
            <View style={styles.cont3}>
                <Text style={styles.title}>{props.courseName}</Text>
                <Text style={styles.subtitle}>{props.courseCode}</Text>
                <View style={{ ...styles.cont2, justifyContent: "flex-end" }}>
                    <View style={styles.selected}>
                        <Text>Asso.Prof Teacher Teacher Teacher</Text>
                    </View>
                </View>
                <Text style={styles.text}>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit
                </Text>
                <View style={styles.cont1}>
                    <FontAwesome name="heart-o" color="#000" size={25} />
                    <Pressable disabled={!authFinish}>
                        {!authFinish ? (
                            <TouchableOpacity
                                style={styles.btnWait}
                                // onPress={() => props.navigation.navigate("Home")}
                            >
                                <Text style={styles.btnText}>Waiting</Text>
                            </TouchableOpacity>
                        ) : (
                            <TouchableOpacity
                                style={styles.btnNext}
                                onPress={() =>
                                    props.navigation.navigate("CourseStudent")
                                }>
                                <Text style={styles.btnText}>Next</Text>
                            </TouchableOpacity>
                        )}
                    </Pressable>
                </View>
                {isShow > 0 ? (
                    <TouchableOpacity
                        style={
                            startSendPicture === 0
                                ? styles.popupDialogPos
                                : styles.popupDialogNeg
                        }
                        onPress={() => {
                            hideDialog();
                        }}>
                        <MaterialCommunityIcons
                            name="face-recognition"
                            size={40}
                            color="#F4F5FF"
                        />
                    </TouchableOpacity>
                ) : (
                    <View style={{ position: "absolute" }}></View>
                )}
            </View>
        </View>
    );
};

export default Detail;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#fff",
    },
    photoCam: {
        width: "100%",
        height: 600,
        position: "absolute",
        top: 50,
        left: 0,

        alignItems: "center",
        justifyContent: "center",
    },
    camera: {
        flex: 1,
        alignItems: "center",
        justifyContent: "center",
    },
    title: {
        fontSize: 25,
        marginTop: 30,
    },
    subtitle: {
        fontSize: 20,
        color: "#474747",
        marginTop: 10,
    },
    text: {
        fontSize: 18,
        paddingRight: 80,
        lineHeight: 25,
    },
    btnNext: {
        backgroundColor: "#BAB86C",
        paddingHorizontal: 60,
        paddingVertical: 12,
        borderRadius: 30,
    },
    btnWait: {
        backgroundColor: "#D3D3D3",
        paddingHorizontal: 60,
        paddingVertical: 12,
        borderRadius: 30,
    },
    btnText: {
        fontSize: 20,
        color: "#FFF",
    },
    cont1: {
        flexDirection: "row",
        alignItems: "center",
        width: "100%",
        justifyContent: "space-between",
        marginTop: 40,
    },
    selected: {
        borderColor: "#E2443B",
        height: 35,
        padding: 5,
        borderRadius: 24,
        borderWidth: 2,
        alignItems: "center",
        justifyContent: "center",
    },
    cont2: {
        flexDirection: "row",
        alignItems: "center",
        width: "100%",
        marginVertical: 25,
    },
    header: {
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "space-between",
        width: "100%",
        paddingHorizontal: 20,
        paddingTop: 50,
    },
    img: {
        height: "45%",
        width: "50%",
    },
    cont3: {
        flex: 1,
        backgroundColor: "#FFF",
        width: "100%",
        borderTopLeftRadius: 40,
        borderTopRightRadius: 40,
        paddingHorizontal: 20,
    },
    popupDialogPos: {
        width: "100%",
        position: "absolute",
        backgroundColor: "#77DD77",
        alignItems: "center",
        bottom: ".5%",

        borderRadius: 15,

        paddingTop: 13,
        paddingBottom: 13,
    },

    popupDialogNeg: {
        width: "100%",
        position: "absolute",
        backgroundColor: "#FF6961",
        alignItems: "center",
        bottom: ".5%",

        borderRadius: 15,

        paddingTop: 13,
        paddingBottom: 13,
    },
});
