import React from "react";
import { dateConverter } from "../../../dateFormatter";

const BookStatusItem = props => {
  const { item, id } = props;

  let type = "Reservation";
  let trColor = "table-warning";

  let endDate = item.end_At;

  if (item.due_date) {
    type = "Loan";
    if (item.returned_At) {
      trColor = "table-success";
      endDate = item.returned_At;
    } else {
      trColor = "table-danger";
      endDate = item.due_date;
    }
  }

  return (
    <tr className={trColor}>
      <td>{id}</td>
      <td>{dateConverter(item.created_At)}</td>
      <td>{dateConverter(endDate)}</td>
      <td>{type}</td>
    </tr>
  );
};

export default BookStatusItem;
