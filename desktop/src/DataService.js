import axios from 'axios'

const COURSE_API_URL = 'https://127.0.0.1:8442'
const INSTRUCTOR_API_URL = `${COURSE_API_URL}/RegisterUser`

class DataService {

    async sendRegistration(user, pass ,code) {
        let aux;
        aux = await axios.post(`${INSTRUCTOR_API_URL}`, {var1: user, var2: pass , var3: code});
        console.log("____________");
        console.log(aux);
        return aux ;
    }

    retrieveAllCourses(name) {
        return axios.get(`${INSTRUCTOR_API_URL}`);
    }

    deleteCourse(name, id) {
        console.log('executed service')
        return axios.delete(`${INSTRUCTOR_API_URL}/courses/${id}`);
    }

    retrieveCourse(name, id) {
        return axios.get(`${INSTRUCTOR_API_URL}/courses/${id}`);
    }

}

export default new DataService()
