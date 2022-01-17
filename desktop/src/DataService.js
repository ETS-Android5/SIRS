import axios from 'axios'

const COURSE_API_URL = 'http://localhost:8080'
const INSTRUCTOR_API_URL = `${COURSE_API_URL}/RegisterUser`

class DataService {

    sendRegistration( user , code ){
        console.log("a enviar");
        return axios.post(`${INSTRUCTOR_API_URL}`, {var1: user , var2: code });
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
