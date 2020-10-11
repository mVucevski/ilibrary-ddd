import React, { Component } from "react";
import bookLogo from "../../assets/img/book.png";
import CreateBookButton from "./CreateBookButton";
import { Link } from "react-router-dom";
import UserManagmentButton from "./UserManagmentButton";

class Header extends Component {
  constructor() {
    super();
    this.state = {
      search: " "
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();
  }

  render() {
    return (
      <div>
        <nav className="navbar-light bg-light">
          <div className="container">
            <div className="row vertical-align">
              <div className="col-md-1 d-sm-none d-none d-md-block logo">
                <img
                  className="img"
                  style={{ height: "70px" }}
                  src={bookLogo}
                  alt=""
                />
              </div>
              <div className="col-md-2">
                <Link to="/home" style={{ textDecoration: "none" }}>
                  <h2 className="Toptitle">
                    i<strong>Library</strong>
                  </h2>
                </Link>
              </div>
              <div className="col-md-6">
                <form onSubmit={this.onSubmit} className="form-inline">
                  <input
                    className="form-control mr-sm-2"
                    style={{ width: "80%" }}
                    type="search"
                    name="search"
                    onChange={this.onChange}
                    id="search"
                    placeholder="Search for books by keyword / title / author / ISBN"
                    aria-label="Search"
                  />
                  <Link
                    style={{ textDecoration: "none" }}
                    className="btn btn-outline-success my-2 my-sm-0"
                    to={`/book/search/${this.state.search}`}
                  >
                    Search
                  </Link>
                </form>
              </div>
              <div className="col-md-3">
                <div className="float-left float-md-right float-sm-left float-xl-right float-lg-right">
                  <CreateBookButton />
                </div>
              </div>
            </div>
          </div>
          <hr className="hrNav" />
        </nav>

        <nav className="navbar navbar-expand-lg navbar-light bg-light my-0">
          <div className="container">
            <button
              className="navbar-toggler navbar-toggler-right"
              type="button"
              data-toggle="collapse"
              data-target="#navbarResponsive"
              aria-controls="navbarResponsive"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon" />
            </button>
            <div className="collapse navbar-collapse">
              <ul className="navbar-nav" style={{ fontWeight: "bold" }}>
                <li className="nav-item">
                  <Link
                    className="nav-link"
                    to="/"
                    style={{ textDecoration: "none" }}
                    alt=""
                  >
                    Latest Deals
                  </Link>
                </li>
                <li className="nav-item">
                  <Link
                    className="nav-link"
                    to="/book/category/Non-Fiction"
                    style={{ textDecoration: "none" }}
                    alt=""
                  >
                    Non-Fiction
                  </Link>
                </li>
                <li className="nav-item">
                  <Link
                    className="nav-link"
                    to="/book/category/Fantasy"
                    style={{ textDecoration: "none" }}
                    alt=""
                  >
                    Fantasy
                  </Link>
                </li>
                <li className="nav-item dropdown">
                  <a
                    className="nav-link dropdown-toggle"
                    href="/"
                    id="navbarDropdownPortfolio"
                    data-toggle="dropdown"
                    aria-haspopup="true"
                    aria-expanded="false"
                  >
                    Search by category
                  </a>
                  <div
                    className="dropdown-menu dropdown-menu-right"
                    aria-labelledby="navbarDropdownPortfolio"
                  >
                    <Link className="dropdown-item" to="/book/category/Fiction">
                      Fiction
                    </Link>
                    <Link
                      className="dropdown-item"
                      to="/book/category/Non-Fiction"
                    >
                      Non-Fiction
                    </Link>
                    <Link
                      className="dropdown-item"
                      to="/book/category/Thriller"
                    >
                      Crime & Thriller
                    </Link>
                    <Link className="dropdown-item" to="/book/category/Fantasy">
                      Fantasy
                    </Link>
                    <Link className="dropdown-item" to="/book/category/History">
                      History
                    </Link>
                    <Link className="dropdown-item" to="/book/category/Cooking">
                      Cooking
                    </Link>
                  </div>
                </li>
              </ul>
              <UserManagmentButton />
            </div>
          </div>
        </nav>
      </div>
    );
  }
}

export default Header;
