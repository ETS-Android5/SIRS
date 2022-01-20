import React, {Component} from "react";
import DataService from "./DataService";

export default class LoginPage extends Component{

    login(){
        console.log( document.getElementById("USERNAME").value  )
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