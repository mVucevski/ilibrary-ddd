import React from "react";
import LoanTableItem from "./LoanTableItem";

const LoansTable = props => {
  const { loans } = props;
  let counter = 1;

  const loansTable = loans.map(l => (
    <LoanTableItem key={counter} item={l} id={counter++} />
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
            <th>Fee</th>
            <th>Returned</th>
            <th />
          </tr>
        </thead>
        <tbody>{loansTable}</tbody>
      </table>
    </div>
  );
};

export default LoansTable;
