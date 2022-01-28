import React, {Component} from "react";
import {Link} from 'react-router-dom'
import DataService from "./DataService";
import react from "react";

export default class RegisterPage extends React.Component{
       
    state = {
        registerVal : 0
    }

    register( obj ) {
        let name = document.getElementById("USERNAME").value;
        let pass = document.getElementById("PASSWORD").value;
        let code = document.getElementById("CODE").value;
        DataService.sendRegistration(name, pass ,code).then(r => {
            //console.log( r.status )
            if ( r.status === 200 ){
                obj.setState({ registerVal : 1 });
                DataService.setRegistration(name);
            }
        })
    }
    

    render() {
        let state;
        if (this.state.registerVal === 1){
            state = <p> Registration Successfull!! Press to login</p>;
        }
        else{
            state= <p></p>;
        }

        return (
            <div>
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
            <button onClick={() => this.register( this )  }> Register</button>
            <Link to='/login'>
                {state}
            </Link>
            

            </>
            </div>
        )
    }
}