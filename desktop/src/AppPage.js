import React, {Component} from "react";
import DataService from "./DataService";
import {Link} from 'react-router-dom'

export default class AppPage extends Component{
    
    state = {
        bool : 0
    }

    logout(  ) {

        DataService.logOut( DataService.getUsername() ).then(r => {
            console.log( r.status )
            if ( r.status === 200){
                DataService.setLogout();
                  window.location.href = '/login' 
            }
        })  
    }

    buyProduct( serialNo , obj ) {
        if( DataService.getLogin !== 1462 ){
            obj.setState({ bool : 1 });
        }

    }

    render() {
        let state;
        if (this.state.registerVal === 1){
            state = <p> You are not logged in, press here to log in</p>;
        }
        else{
            state= <p></p>;
        }

        return (
            <>
                <>Product no1 </>
                <button onClick={()=> this.buyProduct( 1 , this ) }> Buy</button>
                <p></p>
                <>Product no2 </>
                <button onClick={ ()=> this.buyProduct( 2 , this) }> Buy</button>
                <p></p>
                <>Product no3 </>
                <button onClick={ ()=> this.buyProduct( 3 , this ) }> Buy</button>
                <p></p>
                <>Product no4 </>
                <button onClick={ ()=> this.buyProduct( 4 , this) }> Buy</button>
                <p></p>
                <button onClick={() => this.logout( ) }> Log Out </button>
                <p></p>
                <Link to='/login'>
                {state}
                </Link>

            </>
        )
    }
}