import React from "react";

const StarRating = props => {
  const { rating } = props;

  let stars = [];
  let x = 1;
  const rating1 = Math.trunc(rating);

  for (; x <= rating1; x++) {
    stars.push(<span key={x} className="star-mine-full" />);
  }
  const decimal = rating % 1;

  if (decimal > 0) {
    if (decimal > 0.8) {
      stars.push(<span key={x} className="star-mine-full" />);
    } else if (decimal > 0.25) {
      stars.push(<span key={x} className="star-mine-halfFull" />);
    } else {
      stars.push(<span key={x} className="star-mine-empty" />);
    }
    x++;
  }

  for (; x <= 5; x++) {
    stars.push(<span key={x} className="star-mine-empty" />);
  }

  return (
    <div style={{ display: "inline-block" }}>
      <div>{stars}</div>
    </div>
  );
};

export default StarRating;
