import React from "react";
import { Link } from "react-router-dom";
import { dateConverter } from "../../../dateFormatter";

const LoanTableItem = props => {
  const { item, id } = props;

  let returned = <i className="fas fa-minus-square" />;
  let trColor = "table-warning";

  if (item.returnedAt) {
    trColor = "table-success";
    returned = <i className="fas fa-check-square" />;
  }

  return (
    <tr className={trColor}>
      <td className="font-weight-bolder">{id}</td>
      <td className="font-weight-bolder">
        <Link to={`/book/${item.bookId}`}>{item.bookId}</Link>
      </td>
      <td className="font-weight-bolder">{dateConverter(item.createdAt)}</td>
      <td className="font-weight-bolder">{dateConverter(item.dueDate)}</td>
      <td className="font-weight-bolder">$ {item.fee}</td>
      <td className="font-weight-bolder ">{returned}</td>
      <td />
    </tr>
  );
};

export default LoanTableItem;
