
export default class User {
    username;
    password;

    constructor( username_ , password_) {
        this.username = username_ ;
        this.password = password_ ;
    }

    getUserName(){
        return this.username;
    }

    getPassword(){
        return this.password;
    }
}