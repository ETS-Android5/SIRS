import React, {Component} from "react";
//import DataService from "./DataService";

export default class AppPage extends Component{

    buyProduct( serialNo ) {

    }

    render() {
        return (
            <>
                <>Product no1 </>
                <button onClick={ this.buyProduct( 1 ) }> Buy</button>
                <p></p>
                <>Product no2 </>
                <button onClick={ this.buyProduct( 2 ) }> Buy</button>
                <p></p>
                <>Product no3 </>
                <button onClick={ this.buyProduct( 3 ) }> Buy</button>
                <p></p>
                <>Product no4 </>
                <button onClick={ this.buyProduct( 4 ) }> Buy</button>
                <p></p>

            </>
        )
    }
}