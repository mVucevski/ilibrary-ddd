import React from "react";
import LoanTableItem from "./LoanTableItem";
import ReservationTableItem from "./ReserationTableItem";

const ReseravtionsTable = props => {
  const { reservations } = props;
  let counter = 1;

  const resTable = reservations.map(l => (
    <ReservationTableItem key={counter} item={l} id={counter++} />
  ));

  return (
    <div className="col-md-12">
      <table className="table table-sm">
        <thead>
          <tr>
            <th>#</th>
            <th>Book ID</th>
            <th>From (Date)</th>
            <th>To (Date)</th>
            <th />
          </tr>
        </thead>
        <tbody className="table-striped">{resTable}</tbody>
      </table>
    </div>
  );
};

export default ReseravtionsTable;
