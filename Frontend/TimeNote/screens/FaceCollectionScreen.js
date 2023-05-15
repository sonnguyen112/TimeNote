import React, { Component, useEffect, useState } from "react";
import { SafeAreaView, StyleSheet, View, Text, Button } from "react-native";
import { Camera } from "expo-camera";
import * as FaceDetector from "expo-face-detector";

const FaceCollectionScreen = () => {
    const [hasCameraPermission, setHasCameraPermission] = useState(null);
    const [camera, setCamera] = useState(null);
    const [image, setImage] = useState(null);
    const [type, setType] = useState(Camera.Constants.Type.front);
    const [faceData, setFaceData] = useState(null);

    function getFaceDataView() {
        if (faceData === null || faceData.length === 0) {
            return (
                <View style={styles.faces}>
                    <Text style={styles.faceDesc}>No faces :(</Text>
                </View>
            );
        } else {
            return faceData.map((face, index) => {
                const eyesShut =
                    face.rightEyeOpenProbability < 0.4 &&
                    face.leftEyeOpenProbability < 0.4;
                const winking =
                    !eyesShut &&
                    (face.rightEyeOpenProbability < 0.4 ||
                        face.leftEyeOpenProbability < 0.4);
                const smiling = face.smilingProbability > 0.7;
                const headpose = (face.yawAngle < -10.0 ? "Left" : (face.yawAngle > 10.0 ? "Right" : "Heading"));
                return (
                    <View style={styles.faces} key={index}>
                        {/* <Text style={styles.faceDesc}>
                            Eyes Shut: {eyesShut.toString()}
                        </Text>
                        <Text style={styles.faceDesc}>
                            Winking: {winking.toString()}
                        </Text>
                        <Text style={styles.faceDesc}>
                            Smiling: {smiling.toString()}
                        </Text> */}
                        <Text style={styles.faceDesc}>
                            Head Pose: {headpose.toString()}
                        </Text>
                    </View>
                );
            });
        }
    }

    console.log(type);

    useEffect(() => {
        (async () => {
            const cameraStatus = await Camera.requestCameraPermissionsAsync();
            setHasCameraPermission(cameraStatus.status === "granted");
        })();
    }, []);

    const handleFacesDetected = ({ faces }) => {
        setFaceData(faces);
        console.log(faces);
    };

    console.log(hasCameraPermission);
    if (hasCameraPermission === false) {
        return <Text>No Camera Access</Text>;
    }

    return (
        <SafeAreaView style={styles.container}>
                <Camera
                    type={Camera.Constants.Type.front}
                    style={styles.camera}
                    onFacesDetected={handleFacesDetected}
                    faceDetectorSettings={{
                        mode: FaceDetector.FaceDetectorMode.fast,
                        detectLandmarks:
                            FaceDetector.FaceDetectorLandmarks.none,
                        runClassifications:
                            FaceDetector.FaceDetectorClassifications.all,
                        minDetectionInterval: 100,
                        tracking: true,
                    }}>
                    
                </Camera>
                {getFaceDataView()}
                <Button
                    title="Flip"
                    onPress={() => {
                        setType(
                            type === Camera.Constants.Type.back
                                ? Camera.Constants.Type.front
                                : Camera.Constants.Type.back
                        );
                    }}></Button>
        </SafeAreaView>
    );
};

export default FaceCollectionScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: "column",
        backgroundColor: "black",
    },
    cameraContainer: {
        flex: 1,
        flexDirection: "row",
    },
    fixedRatio: {
        flex: 1,
        aspectRatio: 1,
    },
    camera: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
      },
      faces: {
        backgroundColor: '#ffffff',
        alignSelf: 'stretch',
        alignItems: 'center',
        justifyContent: 'center',
        margin: 16
      },
      faceDesc: {
        fontSize: 20
      }    
});
