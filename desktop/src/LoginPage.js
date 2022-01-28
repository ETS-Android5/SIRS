import React, {Component} from "react";
import {Link} from 'react-router-dom';
import DataService from "./DataService";

export default class LoginPage extends Component{

    state = {
        LoginVal : 0
    }

    login( obj ) {

        let name = document.getElementById("USERNAME").value;
        let code = document.getElementById("CODE").value;
        let password = document.getElementById("PASSWORD").value;

        console.log(DataService.getLogin() );

        DataService.logIn(name, password , code).then(r => {
            console.log( r.status )
            if ( r.status === 200){
                obj.setState({ LoginVal : 1 });
                DataService.setLogin();
                DataService.setRegistration(name);
            }
        })

        
    }

    render(){
        let state;
        if (this.state.LoginVal === 1){
            state = <p> Login Successfull!! Press go to Main Page</p>;
        }
        else{
            state= <p></p>;
        }

        return (
            <>
                Username: <input type={"text"} id={"USERNAME"}>
                </input>
                <p> </p>
                Password: <input type={"text"} id={"PASSWORD"}>
                </input>
                <p> </p>
                Generated Code: <input type={"text"} id={"CODE"}>
                </input>
                <p> </p>
                <button onClick={() => this.login(this) }> Log In </button>
                <Link to='/appMainPage'>
                {state}
                </Link>
            </>
        )
    }
}