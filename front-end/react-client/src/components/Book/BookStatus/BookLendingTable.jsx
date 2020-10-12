import React, { Component } from "react";
import BookStatusItem from "./BookStatusItem";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import {getReservationsAndLoans} from "../../../actions/reservationActions";

class BookLendingTable extends Component {

  componentDidMount() {
    console.log("Book id:", this.props.id);

    this.props.getReservationsAndLoans(this.props.id);

    console.log("THIS PROPS LENGIND MOUNT:", this.props);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  render() {
    
    console.log("PROPS LENDING:", this.props);
    const { reservations, loans } = this.props.status;
    let counter = 1;

    console.log("LOANS", loans);

    if(loans){
      const statusResTable = reservations.map(r => (
        <BookStatusItem key={counter} item={r} id={counter++} />
      ));
  
      const statusLoansTable = loans.map(l => (
        <BookStatusItem key={counter} item={l} id={counter++} />
      ));
  
      return (
        <div className="col-md-9">
          <table className="table table-sm">
            <thead>
              <tr>
                <th>#</th>
                <th>From (Date)</th>
                <th>To (Date)</th>
                <th>Type</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {statusResTable}
              {statusLoansTable}
            </tbody>
          </table>
        </div>
      );
    }else{
      return (<h2>ERROR</h2>)
    }
    
  }
}

BookLendingTable.propTypes = {
  getReservationsAndLoans: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  status: state.status,
  errors: state.errors,
  security: state.security
});

export default connect(
  mapStateToProps,
  { getReservationsAndLoans }
)(BookLendingTable);
