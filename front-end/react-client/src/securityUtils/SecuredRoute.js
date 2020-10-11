import React from "react";
import { Route, Redirect } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";

const SecuredRoute = ({ component: Component, security, ...otherProps }) => {
  console.log("OTHER PROPS:", otherProps);
  return (
    <Route
      {...otherProps}
      render={props =>
        security.validToken === true ? (
          otherProps.allowedRoles.includes(security.role) === true ? (
            <Component {...props} />
          ) : (
            <div className="alert alert-danger text-center" role="alert">
              <h3>YOU DONT HAVE THE PRIVILEGES FOR THIS PART OF THE SITE</h3>
            </div>
          )
        ) : (
          <Redirect to="/login" />
        )
      }
    />
  );
};

SecuredRoute.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(mapStateToProps)(SecuredRoute);
