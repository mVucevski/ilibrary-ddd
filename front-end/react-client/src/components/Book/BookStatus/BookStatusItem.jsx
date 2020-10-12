import React from "react";
import { dateConverter } from "../../../dateFormatter";

const BookStatusItem = props => {
  const { item, id } = props;

  let type = "Reservation";
  let trColor = "table-warning";

  let endDate = item.endsAt;

  if (item.dueDate) {
    type = "Loan";
    if (item.returnedAt) {
      trColor = "table-success";
      endDate = item.returnedAt;
    } else {
      trColor = "table-danger";
      endDate = item.dueDate;
    }
  }

  return (
    <tr className={trColor}>
      <td>{id}</td>
      <td>{dateConverter(item.createdAt)}</td>
      <td>{dateConverter(endDate)}</td>
      <td>{type}</td>
    </tr>
  );
};

export default BookStatusItem;
