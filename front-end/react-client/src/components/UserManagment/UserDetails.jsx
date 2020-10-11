import React, { Component } from "react";
import LoansTable from "./UserDetailsItems/LoansTable";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import { getUserInfo } from "../../actions/securityActions";
import ReseravtionsTable from "./UserDetailsItems/ReseravtionsTable";
import { dateConverter } from "../../dateFormatter";

class UserDetails extends Component {
  componentDidMount() {
    this.props.getUserInfo();
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  render() {
    const { userInfo } = this.props.security;

    let pageContent = "";

    if (userInfo) {
      pageContent = (
        <div className="container my-2">
          <div className="row">
            <div className="col-md-12">
              <div className="card border-success">
                <h5 className="card-header">User Info</h5>
                <div className="card-body">
                  <div>
                    <div className="row mt-2">
                      <div className="col-md-12">
                        <label>Username (E-Mail):</label>
                        <label className="ml-2 font-weight-bolder">
                          {userInfo.username}
                        </label>
                      </div>
                    </div>
                    <div className="row mt-2">
                      <div className="col-md-12">
                        <label>Full Name:</label>
                        <label className="ml-2 font-weight-bolder">
                          {userInfo.fullName}
                        </label>
                      </div>
                    </div>
                    <div className="row mt-2">
                      <div className="col-md-12">
                        <label>Membership:</label>
                        <label className="ml-2 font-weight-bolder">
                          {userInfo.membershipExpirationDate === null
                            ? "Not Active"
                            : "Active"}
                        </label>
                      </div>
                    </div>
                    <div className="row mt-2">
                      <div className="col-md-12">
                        <label>Membership expiration date:</label>
                        <label className="ml-2 font-weight-bolder">
                          {dateConverter(userInfo.membershipExpirationDate)}
                        </label>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div className="row mt-2">
            <div className="col-md-12">
              <div className="card border-primary text-primary">
                <h5 className="card-header">Loans</h5>
                <div className="card-body">
                  <div className="row">
                    {userInfo.loans && <LoansTable loans={userInfo.loans} />}
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div className="row mt-2">
            <div className="col-md-12">
              <div className="card border-info text-info">
                <h5 className="card-header">Reservations</h5>
                <div className="card-body">
                  <div className="row">
                    {userInfo.reservations && (
                      <ReseravtionsTable reservations={userInfo.reservations} />
                    )}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      );
    }

    return <div>{pageContent}</div>;
  }
}
UserDetails.propTypes = {
  errors: PropTypes.object.isRequired,
  getUserInfo: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors,
  security: state.security
});

export default connect(
  mapStateToProps,
  { getUserInfo }
)(UserDetails);
