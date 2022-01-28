import axios from 'axios'

const SERVER_URL = 'https://192.168.37.6:8443'
const REGISTER_URL = `${SERVER_URL}/RegisterUser`
const LOGIN_URL = `${SERVER_URL}/LogIn`
const LOGOUT_URL = `${SERVER_URL}/LogOut`

class DataService {
    
    login = 0;
    username;

    setRegistration(username){
        this.username = username;
    }
    setLogin(){
        this.login = 1462;
    }
    setLogout(){
        this.login = 0;
        this.username = null;
    }
    getLogin(){
        return this.login;
    }
    getUsername(){
        return this.username;
    }

    async sendRegistration(user, pass ,code) {
        let aux;
        aux = await axios.post(`${REGISTER_URL}`, {var1: user, var2: pass , var3: code});
        console.log("Registration Successfully sent");
        console.log(aux);
        return aux ;
    }

    async logIn( username , password,  code ) {
        let aux;
        aux = await axios.post(`${LOGIN_URL}`, {var1: username, var2: password , var3:code });
        console.log("Login Successfully sent");
        console.log(aux);
        return aux
    }
    async logOut( username ) {
        console.log(username)
        let aux;
        aux = await axios.post(`${LOGOUT_URL}`, {var1: username});
        console.log("Logout Successfully sent");
        console.log(aux);
        return aux
    }

}

export default new DataService()
