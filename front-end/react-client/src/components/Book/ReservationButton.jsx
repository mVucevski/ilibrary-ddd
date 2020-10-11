import React from "react";
import classnames from "classnames";

const ReservationButton = props => {
  let buttonText;

  if (props.reserved) {
    buttonText = (
      <div>
        <i className="fa fa-minus-circle mr-1" />
        Remove Reservation
      </div>
    );
  } else {
    buttonText = "Reserve It";
  }
  return (
    <React.Fragment>
      <button
        onClick={props.onReservIt}
        className={classnames("btn btn-block", {
          "btn-info": !props.reserved,
          "btn-danger": props.reserved
        })}
      >
        {buttonText}
      </button>
    </React.Fragment>
  );
};

export default ReservationButton;
