import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min";
import "./App.css";
import "./Stars.css";
import Home from "./Home/Home";
import Search from "./Home/Search";
import AllByGenre from "./Home/AllByGenre";
import Header from "./Layout/Header";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import AddBook from "./Book/AddBook";
import { Provider } from "react-redux";
import store from "../store";
import BookDetails from "./Book/BookDetails";
import EditBook from "./Book/EditBook";
import LoanBook from "./StatusManager/LoanBook";
import { SET_CURRENT_USER } from "../actions/types";
import { logout } from "../actions/securityActions";
import jwt_decode from "jwt-decode";
import setJWTToken from "../securityUtils/setJWTToken";
import Register from "./UserManagment/Register";
import Login from "./UserManagment/Login";
import SecuredRoute from "../securityUtils/SecuredRoute";
import starRating from "./Book/StarRating";
import UserDetails from "./UserManagment/UserDetails";
import Landing from "./Home/Landing";

const jwtToken = localStorage.jwtToken;
const userRole = localStorage.userRole;

if (jwtToken) {
  setJWTToken(jwtToken);
  const decoded_jwtToken = jwt_decode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: {
      user: decoded_jwtToken,
      role: userRole
    }
  });

  const currentTime = Date.now() / 1000;

  console.log("cureetnTIME:" + currentTime + " ext: " + decoded_jwtToken.ext);

  if (decoded_jwtToken.ext < currentTime) {
    store.dispatch(logout());
    window.location.href = "/";
  }
}

const roleUser = "ROLE_USER";
const roleAdmin = "ROLE_EMPLOYEE";

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />
          <hr className="hrNav" />
          <br />
          <Route exact path="/" component={Landing} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/login" component={Login} />

          <Switch>
            <SecuredRoute
              allowedRoles={[roleAdmin, roleUser]}
              exact
              path="/home"
              component={Home}
            />
            <SecuredRoute
              allowedRoles={[roleAdmin, roleUser]}
              exact
              path="/book/:id"
              component={BookDetails}
            />
            <SecuredRoute
              allowedRoles={[roleAdmin, roleUser]}
              exact
              path="/book/search/:id"
              component={Search}
            />
            <SecuredRoute
              allowedRoles={[roleAdmin, roleUser]}
              exact
              path="/book/category/:id"
              component={AllByGenre}
            />
            <SecuredRoute
              allowedRoles={[roleAdmin]}
              exact
              path="/addBook"
              component={AddBook}
            />
            <SecuredRoute
              allowedRoles={[roleAdmin]}
              exact
              path="/book/:id/edit"
              component={EditBook}
            />

            <SecuredRoute
              allowedRoles={[roleAdmin]}
              exact
              path="/statusManager"
              component={LoanBook}
            />
            <SecuredRoute
              allowedRoles={[roleAdmin, roleUser]}
              exact
              path="/userInfo"
              component={UserDetails}
            />
          </Switch>
        </div>
      </Router>
    </Provider>
  );
}

export default App;

//allowedRoles={"ROLE_USER"}
