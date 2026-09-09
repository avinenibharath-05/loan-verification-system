
import React, { useEffect, useState } from "react";
import axios from "axios";
import LoanList from "./LoanList";
import "./App.css";

function App() {

  // ==============================
  // LOAN SUMMARY
  // ==============================

  const [summary, setSummary] = useState({
    totalLoans: 0,
    pendingLoans: 0,
    verifiedLoans: 0,
    rejectedLoans: 0,
    modifiedLoans: 0
  });

  const [error, setError] = useState("");

  // ==============================
  // REFRESH TRIGGER
  // ==============================

  const [loanRefresh, setLoanRefresh] = useState(0);

  // ==============================
  // VERIFICATION
  // ==============================

  const [loanId, setLoanId] = useState("");
  const [verificationMessage, setVerificationMessage] =
    useState("");

  // ==============================
  // EXCEPTIONS
  // ==============================

  const [exceptions, setExceptions] = useState([]);

  const [showExceptions, setShowExceptions] =
    useState(false);

  const [exceptionMessage, setExceptionMessage] =
    useState("");

  // ==============================
  // EXCEPTION SUMMARY
  // ==============================

  const [exceptionSummary, setExceptionSummary] =
    useState({
      totalExceptions: 0,
      openExceptions: 0,
      resolvedExceptions: 0
    });

  // ==============================
  // EXCEPTION FILTERS
  // ==============================

  const [exceptionSearch, setExceptionSearch] =
    useState("");

  const [exceptionStatusFilter, setExceptionStatusFilter] =
    useState("ALL");

  const [exceptionSeverityFilter, setExceptionSeverityFilter] =
    useState("ALL");

  // ==============================
  // LOAD LOAN SUMMARY
  // ==============================

  const loadSummary = () => {

    axios
      .get("http://localhost:8080/api/loans/summary")
      .then((response) => {

        console.log(
          "Loan Summary:",
          response.data
        );

        setSummary({
          totalLoans: response.data.totalLoans || 0,
          pendingLoans: response.data.pendingLoans || 0,
          verifiedLoans: response.data.verifiedLoans || 0,
          rejectedLoans: response.data.rejectedLoans || 0,
          modifiedLoans: response.data.modifiedLoans || 0
        });

        setError("");

      })
      .catch((error) => {

        console.error(
          "Loan Summary Error:",
          error
        );

        setError(
          "Unable to connect to backend"
        );

      });
  };

  // ==============================
  // LOAD EXCEPTION SUMMARY
  // ==============================

  const loadExceptionSummary = () => {

    axios
      .get(
        "http://localhost:8080/api/exceptions/summary"
      )
      .then((response) => {

        console.log(
          "Exception Summary:",
          response.data
        );

        setExceptionSummary({
          totalExceptions:
            response.data.totalExceptions || 0,

          openExceptions:
            response.data.openExceptions || 0,

          resolvedExceptions:
            response.data.resolvedExceptions || 0
        });

      })
      .catch((error) => {

        console.error(
          "Exception Summary Error:",
          error
        );

      });
  };

  // ==============================
  // LOAD ALL EXCEPTIONS
  // ==============================

  const loadAllExceptions = () => {

    axios
      .get(
        "http://localhost:8080/api/exceptions"
      )
      .then((response) => {

        console.log(
          "All Exceptions:",
          response.data
        );

        setExceptions(response.data);

        setShowExceptions(true);

        setExceptionMessage("");

      })
      .catch((error) => {

        console.error(
          "Exception Error:",
          error
        );

        setExceptionMessage(
          "Unable to load exceptions"
        );

      });
  };

  // ==============================
  // INITIAL LOAD
  // ==============================

  useEffect(() => {

    loadSummary();

    loadExceptionSummary();

  }, []);

  // ==============================
  // REFRESH LOAN SUMMARY
  // ==============================

  useEffect(() => {

    if (loanRefresh > 0) {

      loadSummary();

    }

  }, [loanRefresh]);

  // ==============================
  // VERIFY LOAN
  // ==============================

  const verifyLoan = () => {

    if (!loanId.trim()) {

      setVerificationMessage(
        "Please enter a Loan ID"
      );

      return;
    }

    axios
      .get(
        `http://localhost:8080/api/loans/loan-id/${loanId}`
      )
      .then((response) => {

        const loan = response.data;

        return axios.post(
          `http://localhost:8080/api/loans/${loan.id}/verify`
        );

      })
      .then((response) => {

        setVerificationMessage(
          response.data.message ||
          "Loan verification completed"
        );

        // Refresh dashboard
        loadSummary();

        // Refresh loan table
        setLoanRefresh((value) => value + 1);

      })
      .catch((error) => {

        console.error(
          "Verification Error:",
          error
        );

        setVerificationMessage(
          "Loan verification failed"
        );

      });
  };

  // ==============================
  // VERIFY HASH
  // ==============================

  const verifyHash = () => {

    if (!loanId.trim()) {

      setVerificationMessage(
        "Please enter a Loan ID"
      );

      return;
    }

    axios
      .get(
        `http://localhost:8080/api/loans/loan-id/${loanId}`
      )
      .then((response) => {

        const loan = response.data;

        return axios.get(
          `http://localhost:8080/api/loans/${loan.id}/verify-hash`
        );

      })
      .then((response) => {

        setVerificationMessage(
          response.data.message ||
          "Hash verification completed"
        );

        // Refresh dashboard
        loadSummary();

        // Refresh loan table
        setLoanRefresh((value) => value + 1);

      })
      .catch((error) => {

        console.error(
          "Hash Verification Error:",
          error
        );

        setVerificationMessage(
          "Hash verification failed"
        );

      });
  };

  // ==============================
  // VIEW ALL EXCEPTIONS
  // ==============================

  const viewExceptions = () => {

    loadAllExceptions();

  };

  // ==============================
  // VIEW OPEN EXCEPTIONS
  // ==============================

  const viewOpenExceptions = () => {

    axios
      .get(
        "http://localhost:8080/api/exceptions/open"
      )
      .then((response) => {

        console.log(
          "Open Exceptions:",
          response.data
        );

        setExceptions(response.data);

        setShowExceptions(true);

        setExceptionMessage("");

      })
      .catch((error) => {

        console.error(
          "Open Exception Error:",
          error
        );

        setExceptionMessage(
          "Unable to load open exceptions"
        );

      });
  };

  // ==============================
  // RESOLVE EXCEPTION
  // ==============================

  const resolveException = (id) => {

    axios
      .put(
        `http://localhost:8080/api/exceptions/${id}/resolve`
      )
      .then((response) => {

        console.log(
          "Resolved Exception:",
          response.data
        );

        alert(
          "Exception resolved successfully"
        );

        // Refresh exception summary
        loadExceptionSummary();

        // Refresh exception table
        loadAllExceptions();

      })
      .catch((error) => {

        console.error(
          "Resolve Exception Error:",
          error
        );

        alert(
          "Unable to resolve exception"
        );

      });
  };

  // ==============================
  // FILTER EXCEPTIONS
  // ==============================

  const filteredExceptions =
    exceptions.filter((exception) => {

      const searchText =
        exceptionSearch.toLowerCase().trim();

      const matchesSearch =
        searchText === "" ||
        String(exception.loanId)
          .toLowerCase()
          .includes(searchText);

      const matchesStatus =
        exceptionStatusFilter === "ALL" ||
        exception.status === exceptionStatusFilter;

      const matchesSeverity =
        exceptionSeverityFilter === "ALL" ||
        exception.severity === exceptionSeverityFilter;

      return (
        matchesSearch &&
        matchesStatus &&
        matchesSeverity
      );

    });

  // ==============================
  // RETURN UI
  // ==============================

  return (

    <div className="app">

      {/* HEADER */}

      <header className="header">

        <h1>
          Loan Verification System
        </h1>

        <p>
          Loan processing and exception management
        </p>

      </header>

      <main className="dashboard">

        {/* ERROR */}

        {error && (

          <div className="error">
            {error}
          </div>

        )}

        {/* ==============================
            LOAN SUMMARY
        ============================== */}

        <div className="summary-grid">

          <div className="card total-card">

            <h3>
              Total Loans
            </h3>

            <p className="number">
              {summary.totalLoans}
            </p>

          </div>

          <div className="card pending-card">

            <h3>
              Pending
            </h3>

            <p className="number">
              {summary.pendingLoans}
            </p>

          </div>

          <div className="card verified-card">

            <h3>
              Verified
            </h3>

            <p className="number">
              {summary.verifiedLoans}
            </p>

          </div>

          <div className="card rejected-card">

            <h3>
              Rejected
            </h3>

            <p className="number">
              {summary.rejectedLoans}
            </p>

          </div>

          <div className="card modified-card">

            <h3>
              Modified
            </h3>

            <p className="number">
              {summary.modifiedLoans}
            </p>

          </div>

        </div>

        {/* ==============================
            LOAN MANAGEMENT
        ============================== */}

        <section className="section">

          <h2>
            Loan Management
          </h2>

          <LoanList
            refreshSummary={() =>
              setLoanRefresh((value) => value + 1)
            }
          />

        </section>

        {/* ==============================
            VERIFICATION
        ============================== */}

        <section className="section">

          <h2>
            Verification
          </h2>

          <div className="verification-form">

            <input
              type="text"
              placeholder="Enter Loan ID"
              value={loanId}
              onChange={(e) =>
                setLoanId(e.target.value)
              }
            />

            <button
              onClick={verifyLoan}
            >
              Verify Loan
            </button>

            <button
              onClick={verifyHash}
            >
              Verify Hash
            </button>

          </div>

          {verificationMessage && (

            <p>
              {verificationMessage}
            </p>

          )}

        </section>

        {/* ==============================
            EXCEPTIONS
        ============================== */}

        <section className="section">

          <h2>
            Exceptions
          </h2>

          {/* EXCEPTION SUMMARY */}

          <div className="exception-summary-grid">

            <div className="exception-card">

              <h3>
                Total Exceptions
              </h3>

              <p className="number">
                {exceptionSummary.totalExceptions}
              </p>

            </div>

            <div className="exception-card">

              <h3>
                Open
              </h3>

              <p className="number">
                {exceptionSummary.openExceptions}
              </p>

            </div>

            <div className="exception-card">

              <h3>
                Resolved
              </h3>

              <p className="number">
                {exceptionSummary.resolvedExceptions}
              </p>

            </div>

          </div>

          {/* EXCEPTION BUTTONS */}

          <div className="buttons">

            <button
              onClick={viewExceptions}
            >
              All Exceptions
            </button>

            <button
              onClick={viewOpenExceptions}
            >
              Open Exceptions
            </button>

          </div>

          {/* SEARCH AND FILTERS */}

          {showExceptions && (

            <div className="exception-filters">

              <input
                type="text"
                placeholder="Search by Loan ID"
                value={exceptionSearch}
                onChange={(e) =>
                  setExceptionSearch(
                    e.target.value
                  )
                }
              />

              <select
                value={exceptionStatusFilter}
                onChange={(e) =>
                  setExceptionStatusFilter(
                    e.target.value
                  )
                }
              >

                <option value="ALL">
                  All Status
                </option>

                <option value="OPEN">
                  Open
                </option>

                <option value="RESOLVED">
                  Resolved
                </option>

              </select>

              <select
                value={exceptionSeverityFilter}
                onChange={(e) =>
                  setExceptionSeverityFilter(
                    e.target.value
                  )
                }
              >

                <option value="ALL">
                  All Severity
                </option>

                <option value="HIGH">
                  High
                </option>

                <option value="MEDIUM">
                  Medium
                </option>

                <option value="LOW">
                  Low
                </option>

              </select>

              <button
                onClick={() => {

                  setExceptionSearch("");

                  setExceptionStatusFilter(
                    "ALL"
                  );

                  setExceptionSeverityFilter(
                    "ALL"
                  );

                }}
              >
                Clear Filters
              </button>

              <button
                onClick={() => {

                  loadAllExceptions();

                  loadExceptionSummary();

                }}
              >
                Refresh
              </button>

            </div>

          )}

          {exceptionMessage && (

            <p>
              {exceptionMessage}
            </p>

          )}

          {/* EXCEPTION TABLE */}

          {showExceptions && (

            <div className="exception-table">

              <h3>
                Exception Records
              </h3>

              {filteredExceptions.length === 0 ? (

                <p>
                  No matching exceptions found.
                </p>

              ) : (

                <table>

                  <thead>

                    <tr>

                      <th>ID</th>
                      <th>Loan ID</th>
                      <th>Field</th>
                      <th>Issue Type</th>
                      <th>Description</th>
                      <th>Severity</th>
                      <th>Status</th>
                      <th>Action</th>

                    </tr>

                  </thead>

                  <tbody>

                    {filteredExceptions.map(
                      (exception) => (

                        <tr
                          key={exception.id}
                        >

                          <td>
                            {exception.id}
                          </td>

                          <td>
                            {exception.loanId}
                          </td>

                          <td>
                            {exception.fieldName}
                          </td>

                          <td>
                            {exception.issueType}
                          </td>

                          <td>
                            {exception.description}
                          </td>

                          <td>

                            <span
                              className={`severity-badge ${
                                exception.severity
                                  ? exception.severity.toLowerCase()
                                  : ""
                              }`}
                            >
                              {exception.severity}
                            </span>

                          </td>

                          <td>

                            <span
                              className={`exception-status-badge ${
                                exception.status
                                  ? exception.status.toLowerCase()
                                  : ""
                              }`}
                            >
                              {exception.status}
                            </span>

                          </td>

                          <td>

                            {exception.status ===
                            "OPEN" ? (

                              <button
                                onClick={() =>
                                  resolveException(
                                    exception.id
                                  )
                                }
                              >
                                Resolve
                              </button>

                            ) : (

                              <span>
                                Resolved
                              </span>

                            )}

                          </td>

                        </tr>

                      )
                    )}

                  </tbody>

                </table>

              )}

            </div>

          )}

        </section>

      </main>

    </div>

  );

}

export default App;

