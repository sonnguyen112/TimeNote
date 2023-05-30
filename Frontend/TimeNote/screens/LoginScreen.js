import * as React from "react";
import { Text, TextInput, View, StyleSheet,TouchableOpacity } from "react-native";



const Login = (props) => {
    const [userCode, setUserCode] = React.useState("")
    const [password, setPassword] = React.useState("")
    const [error, setError] = React.useState("")

    const handleLogin = () => {
        async function fetchLogin()
        {
            const response = await fetch("https://4c2c-2001-ee0-4fcb-e0f0-e668-5cdb-5dcc-a11b.ngrok-free.app/auth_api/user/login",
            {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                  },
                  body: JSON.stringify({
                    userCode: userCode,
                    password: password,
                  })
            })

            if (response.status === 200)
            {
                const json = await response.json()
                props.setToken(json.token)
            }
            else{
                setError("Usercode or password is incorrect")
            }

        }
    }
    return (
        //{(error !=="") && <View></View>}
        <View style={styles.container}>
            <View style={styles.inputView}>
            <TextInput
            style={styles.TextInput}
            placeholder="Usercode"
            placeholderTextColor="#003f5c"
            onChangeText={(userCode) => setUserCode(userCode)}
            /> 
        </View> 
        <View style={styles.inputView}>
            <TextInput
            style={styles.TextInput}
            placeholder="Password"
            placeholderTextColor="#003f5c"
            secureTextEntry={true}
            onChangeText={(password) => setPassword(password)}
            /> 
        </View>

        <TouchableOpacity>
            <Text style={styles.forgot_button}>Forgot Password?</Text> 
        </TouchableOpacity> 
        <TouchableOpacity style={styles.loginBtn} onPress={handleLogin}>
            <Text style={styles.loginText}>LOGIN</Text> 
        </TouchableOpacity> 
      </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: "center",
        justifyContent: "center",
        //backgroundColor: "#F2EAFC"
    },
    inputView: {
        backgroundColor: "#F2EAFC",
        borderRadius: 30,
        width: "70%",
        height: 45,
        marginBottom: 20,
        alignItems: "center",
      },

    TextInput: {
    height: 50,
    flex: 1,
    padding: 10,
    //marginLeft: 20,
    },
    forgot_button: {
        height: 30,
        marginBottom: 30,
        fontWeight: 'bold'
    },
    loginText: {
        fontWeight: 'bold'
    },
    loginBtn: {
    width: "80%",
    borderRadius: 25,
    height: 50,
    alignItems: "center",
    justifyContent: "center",
    marginTop: 40,
    backgroundColor: "#F3CE03",
    }

})

export default Login