import React from "react";
import { dateConverter } from "../../dateFormatter";

const LoanDetails = props => {
  const { loan } = props;

  return (
    <div>
      <div className="row mt-3">
        <div className="col-md-12">
          <div className="card text-white bg-info mb-3">
            <div className="card-header">
              <h5 className="card-title">The loan was successfully created!</h5>
            </div>
            <div className="card-body">
              <div className="card-text row">
                <div className="col-md-4" style={{ fontWeight: "bold" }}>
                  Username
                </div>
                <div className="col-md-4" style={{ fontWeight: "bold" }}>
                  Book ISBN
                </div>
                <div className="col-md-4" style={{ fontWeight: "bold" }}>
                  Due Date
                </div>
              </div>
              <div className="card-text row">
                <div className="col-md-4" style={{ fontWeight: "bold" }}>
                  {props.username}
                </div>
                <div className="col-md-4" style={{ fontWeight: "bold" }}>
                  {loan.bookId}
                </div>
                <div className="col-md-4" style={{ fontWeight: "bold" }}>
                  {dateConverter(loan.dueDate)}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoanDetails;
