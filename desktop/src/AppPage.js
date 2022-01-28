import React, {Component} from "react";
import DataService from "./DataService";
import {Link} from 'react-router-dom'

export default class AppPage extends Component{
    
    state = {
        bool : 0 ,
        purchase: 0,
        wait:0
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

    buyProduct( serialNo , obj, price ) {
        console.log("entrei");
        if(DataService.getLogin() !== 1462 ){
            console.log(" NOT LOGGED IN")
            obj.setState({ bool : 1 });
        }
        else{
            console.log("WHY NOT?")
            obj.setState({wait : 1 })
            DataService.buy(DataService.getUsername() ,  serialNo , price , 300000).then(r => {
                //console.log( r.status )
                if ( r.status === 200 ){
                    console.log(r);
                    obj.setState({purchase : 1 })
                }
            })
           
        }

    }

    render() {
        let state;
        let state2;
        let state3;

        if (this.state.bool === 1){
            state = <p> You are not logged in, press here to log in</p>;
        }
        else{
            state= <p></p>;
        }

        if (this.state.purchase === 1){
            state2 = <p> WELL DONE!! You've purchased successfully </p>;
        }
        else{
            state2 = <p></p>;
        }

        if (this.state.wait === 1){
            state3 = <h3> Confirm purchase in your moblie device! </h3>;
        }
        else{
            state3 = <p></p>;
        }

        return (
            <>
                <>Product no1 </>
                {state3}
                <button onClick={()=> this.buyProduct( "Product no1" , this , 34 ) }> Buy </button>
                <p>34$</p>
                <p></p>
                <>Product no2 </>
                <button onClick={ ()=> this.buyProduct( "Product no2" , this , 54) }> Buy </button>
                <p>54$</p>
                <p></p>
                <>Product no3 </>
                <button onClick={ ()=> this.buyProduct("Product no3" , this, 22 ) }> Buy</button>
                <p>22$</p>
                <p></p>
                <>Product no4 </>
                <button onClick={ ()=> this.buyProduct( "Product no4" , this , 100) }> Buy</button>
                <p>100$</p>
                <p></p>
                <button onClick={() => this.logout( ) }> Log Out </button>
                <p></p>
                <Link to='/login'>
                {state}
                </Link>
                {state2}
                

            </>
        )
    }
}