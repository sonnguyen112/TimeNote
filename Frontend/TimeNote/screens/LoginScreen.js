import * as React from "react";
import { Text, TextInput, View, StyleSheet,TouchableOpacity, ActivityIndicator} from "react-native";
import { IDServer } from "../common/GlobalVariable";



const Login = (props) => {
    const [userCode, setUserCode] = React.useState("")
    const [password, setPassword] = React.useState("")
    const [error, setError] = React.useState("")
    const [isLoading,setLoading] = React.useState(false)

    const handleLogin = () => {
        async function fetchLogin()
        {
            setLoading(true)
            const response = await fetch(`${IDServer}auth_api/user/login`,
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
            console.log(userCode)
            console.log(password)

            console.log(response.status)
            if (response.status === 200)
            {
                const json = await response.json()
                console.log(json)
                props.setToken(json.token)
                props.setRole(json.role)
                props.navigation.navigate('Student')
            }
            else{
                setError("Usercode or password is incorrect")
            }

        }
        fetchLogin();
        setLoading(false)

    }
    return (
        <View style={styles.container}>
             <ActivityIndicator color={"#fff"}  animating={isLoading}/>
            {(error !== "") && <View ><Text style={styles.errorView}>{error}
                </Text></View>}
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
    padding: 0,
    marginLeft: 10,
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
    },

    errorView: {
        marginBottom: 30,
        color: "red",
        fontWeight: "bold"
    }

})

export default Login;