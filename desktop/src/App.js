import React from 'react';
import {BrowserRouter as Router, Link, Route, Routes} from 'react-router-dom'
import RegisterPage from "./RegisterPage";
import LoginPage from "./LoginPage";
import AppPage from "./AppPage";

function App() {
  return (
      <Router>
        <>
          <h1>Application Name Here</h1>
            <Routes>
              <Route path="/" exact element={<Welcome/> }/>
              <Route path="/register" exact element={<RegisterPage/>}/>
              <Route path="/login" element={<LoginPage/>}/>
                <Route path="/appMainPage" element={<AppPage/>}/>
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

export default App;
