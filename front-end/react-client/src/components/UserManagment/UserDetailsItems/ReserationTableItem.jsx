import React from "react";
import { Link } from "react-router-dom";
import { dateConverter } from "../../../dateFormatter";

const ReservationTableItem = props => {
  const { item, id } = props;

  return (
    <tr>
      <td className="font-weight-bolder">{id}</td>
      <td className="font-weight-bolder">
        <Link to={`/book/${item.bookISBN}`}>{item.bookISBN}</Link>
      </td>
      <td className="font-weight-bolder">{dateConverter(item.created_At)}</td>
      <td className="font-weight-bolder">{dateConverter(item.end_At)}</td>
      <td />
    </tr>
  );
};

export default ReservationTableItem;
