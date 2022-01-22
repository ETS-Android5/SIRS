import axios from 'axios'

const COURSE_API_URL = 'http://127.0.0.1:8443'
const INSTRUCTOR_API_URL = `${COURSE_API_URL}/RegisterUser`

class DataService {

    async sendRegistration(user, pass ,code) {
        let auxi;
        auxi = await axios.post(`${INSTRUCTOR_API_URL}`, {var1: user, var2: pass , var3: code});
        console.log("____________");
        console.log(auxi);
        return auxi ;
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
