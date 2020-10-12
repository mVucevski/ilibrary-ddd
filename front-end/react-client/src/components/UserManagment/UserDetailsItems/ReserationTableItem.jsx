import React from "react";
import { Link } from "react-router-dom";
import { dateConverter } from "../../../dateFormatter";

const ReservationTableItem = props => {
  const { item, id } = props;

  return (
    <tr>
      <td className="font-weight-bolder">{id}</td>
      <td className="font-weight-bolder">
        <Link to={`/book/${item.bookId}`}>{item.bookId}</Link>
      </td>
      <td className="font-weight-bolder">{dateConverter(item.createdAt)}</td>
      <td className="font-weight-bolder">{dateConverter(item.endsAt)}</td>
      <td />
    </tr>
  );
};

export default ReservationTableItem;
