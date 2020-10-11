import React from "react";

const BookDoesntExist = props => {
  return (
    <div className="alert alert-danger text-center" role="alert">
      {props.error}
    </div>
  );
};

export default BookDoesntExist;
