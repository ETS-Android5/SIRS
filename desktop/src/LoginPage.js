import React, {Component} from "react";
import DataService from "./DataService";

export default class LoginPage extends Component{

    login( ) {

        let name = document.getElementById("USERNAME").value;
        let code = document.getElementById("CODE").value;

        DataService.logIn(name, code).then(r => {
            console.log( r.status )
            if ( r.status === 200){
                window.location.href = '/appMainPage'
            }
        })
    }

    render(){

        return (
            <>
                Username: <input type={"text"} id={"USERNAME"}>
                </input>
                <p> </p>
                Generated Code: <input type={"text"} id={"CODE"}>
                </input>
                <p> </p>
                <button onClick={ this.login }> Log In </button>
            </>
        )
    }
}