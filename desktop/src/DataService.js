import axios from 'axios'

const SERVER_URL = 'https://127.0.0.1:8443'
const REGISTER_URL = `${SERVER_URL}/RegisterUser`
const LOGIN_URL = `${SERVER_URL}/LogIn`

class DataService {

    async sendRegistration(user, pass ,code) {
        let aux;
        aux = await axios.post(`${REGISTER_URL}`, {var1: user, var2: pass , var3: code});
        console.log("Registration Successfully sent");
        console.log(aux);
        return aux ;
    }

    async logIn( username , code ) {
        let aux;
        aux = await axios.post(`${LOGIN_URL}`, {var1: username, var2: code});
        console.log("Login Successfully sent");
        console.log(aux);
        return aux
    }

}

export default new DataService()
