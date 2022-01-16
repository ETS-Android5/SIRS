import React from 'react';
import {BrowserRouter as Router, Link, Route, Routes} from 'react-router-dom'
import RegisterPage from "./RegisterPage";
import LoginPage from "./LoginPage";

function App() {
  return (
      <Router>
        <>
          <h1>Application Name Here</h1>
            <Routes>
              <Route path="/" exact element={<Welcome/> }/>
              <Route path="/register" exact element={<RegisterPage/>} />
              <Route path="/register/insertCode" exact element={null} />
              <Route path="/login" element={<LoginPage/>} />
            </Routes>
        </>
      </Router>
  )
}
function Welcome(){
    return (
        <div>
            <Link className="btn btn-success" to="/register">
                Register
            </Link>{" "}

            <Link className="btn btn-primary" to="/login">
                LogIn
            </Link>
        </div>
    )

}
/*
function RegisterPage() {
    return (
        <>
            Username: <input type={"text"} id={"USERNAME"}>
        </input>
            <p></p>
            Password: <input type={"text"} id={"PASSWORD"}>
        </input>
            <p></p>
            <button> Register</button>

        </>
    )
}
function LoginPage() {
        return (
            <>
                Username: <input type={"text"}>
            </input>
                <p></p>
                Generated Code: <input type={"text"} >
            </input>
                <p></p>
                <button onClick={register}> Log In </button>
            </>
        )
    }

function register(){

}
*/
export default App;
