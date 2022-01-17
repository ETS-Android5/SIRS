import axios from 'axios'
import * as https from "https";
import * as fs from "fs";

const COURSE_API_URL = 'https://localhost:8080'
const INSTRUCTOR_API_URL = `${COURSE_API_URL}/RegisterUser`

class DataService {

    fs = require('fs');
    https = require('https');
    axios = require('axios');


    sendRegistration( user , code ){
        console.log("a enviar");
        /*
        let options = {
            url: `https://localhost:8080`,  // <---this is  a fake ip do not bother
            method: "POST",
            httpsAgent :  new https.Agent({
                cert: fs.readFileSync('client.crt'),
                key: fs.readFileSync('client.key'),
                ca: fs.readFileSync('ca.crt'),
            }),
            headers: {
            },
            data: { var1: user , var2:code }
        };
        return axios(options);*/

        return axios.post(`${INSTRUCTOR_API_URL}` , {  var1: user , var2:code } ,
            { httpsAgent: new https.Agent({
                        cert: fs.readFileSync('client.crt'),
                        key: fs.readFileSync('client.key'),
                        ca: fs.readFileSync('ca.crt'),
                        })
                    }
        );


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
