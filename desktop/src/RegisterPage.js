import React, {Component} from "react";
import DataService from "./DataService";

export default class RegisterPage extends Component{

    register( ) {
        let name = document.getElementById("USERNAME").value;
        let pass = document.getElementById("PASSWORD").value;
        let code = document.getElementById("CODE").value;
        DataService.sendRegistration(name, pass ,code).then(r => {
            console.log( r.status )
            if ( r.status === "OK"){
                window.location.href = '/login'
            }
        })
    }

    render() {
        return (
            <>
            Username: <input type={"text"} id={"USERNAME"}>
            </input>
                <p></p>
            Password: <input type={"text"} id={"PASSWORD"}>
            </input>
                <p></p>
            Pairing Code: <input type={"text"} id={"CODE"}>
            </input>
                <p></p>
            <button onClick={this.register }> Register</button>

            </>
        )
    }
}