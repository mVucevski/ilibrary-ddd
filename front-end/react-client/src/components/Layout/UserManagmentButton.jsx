import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { logout } from "../../actions/securityActions";

class UserManagmentButton extends Component {
  logout() {
    this.props.logout();
    window.location.href = "/";
  }

  render() {
    const { validToken, user } = this.props.security;
    const userIsNotAuthenticated = (
      <ul className="nav navbar-nav navbar-right">
        <li className="pr-2 py-1">
          <Link
            to="/register"
            className="btn btn-outline-secondary my-1 my-sm-1 create-btn"
          >
            Register
          </Link>
        </li>
        <li className="pr-2 py-1">
          <Link
            to="/login"
            className="btn btn-outline-secondary my-1 my-sm-1 create-btn"
          >
            Login
          </Link>
        </li>
      </ul>
    );

    const userIsAuthenticated = (
      <ul className="nav navbar-nav navbar-right">
        <li className="pr-2 py-1">
          <Link to="/userInfo" className="btn btn-outline-secondary">
            {user ? user.fullName : ""}
          </Link>
        </li>
        <li className="pr-2 py-1">
          <Link
            to="/logout"
            className="btn btn-outline-secondary"
            onClick={this.logout.bind(this)}
          >
            Log out
          </Link>
        </li>
      </ul>
    );

    let headerLinks;

    if (validToken && user) {
      headerLinks = userIsAuthenticated;
    } else {
      headerLinks = userIsNotAuthenticated;
    }

    return (
      <ul className="navbar-nav ml-auto" style={{ fontWeight: "bold" }}>
        {headerLinks}
      </ul>
    );
  }
}
UserManagmentButton.propTypes = {
  logout: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(
  mapStateToProps,
  { logout }
)(UserManagmentButton);
