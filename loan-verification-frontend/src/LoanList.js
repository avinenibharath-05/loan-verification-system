
import React, { useEffect, useState } from "react";
import axios from "axios";

function LoanList() {

  const [loans, setLoans] = useState([]);
  const [error, setError] = useState("");

  // Search
  const [searchText, setSearchText] = useState("");

  // Filters
  const [statusFilter, setStatusFilter] = useState("ALL");
  const [verificationFilter, setVerificationFilter] = useState("ALL");

  // View loan
  const [selectedLoan, setSelectedLoan] = useState(null);

  // Edit loan
  const [editingLoan, setEditingLoan] = useState(null);

  // ==============================
  // LOAD ALL LOANS
  // ==============================

  const loadLoans = () => {

    axios
      .get("http://localhost:8080/api/loans")
      .then((response) => {

        console.log("Loans Response:", response.data);

        setLoans(response.data);
        setError("");

      })
      .catch((error) => {

        console.error("Loan API Error:", error);

        setError("Unable to load loans");

      });

  };


  useEffect(() => {

    loadLoans();

  }, []);


  // ==============================
  // VIEW LOAN
  // ==============================

  const viewLoan = (id) => {

    axios
      .get(`http://localhost:8080/api/loans/${id}`)
      .then((response) => {

        setSelectedLoan(response.data);

      })
      .catch((error) => {

        console.error("View Loan Error:", error);

        alert("Unable to load loan details");

      });

  };


  // ==============================
  // EDIT LOAN
  // ==============================

  const editLoan = (loan) => {

    setEditingLoan({
      ...loan
    });

  };


  // ==============================
  // HANDLE EDIT INPUT
  // ==============================

  const handleEditChange = (e) => {

    const { name, value } = e.target;

    setEditingLoan((previousLoan) => ({
      ...previousLoan,
      [name]: value
    }));

  };


  // ==============================
  // UPDATE LOAN
  // ==============================

  const updateLoan = () => {

    if (!editingLoan) {
      return;
    }

    const updatedLoan = {
      borrowerName: editingLoan.borrowerName,
      loanAmount: Number(editingLoan.loanAmount),
      interestRate: Number(editingLoan.interestRate),
      loanDate: editingLoan.loanDate,
      status: editingLoan.status
    };

    axios
      .put(
        `http://localhost:8080/api/loans/${editingLoan.id}`,
        updatedLoan
      )
      .then((response) => {

        alert("Loan updated successfully");

        setEditingLoan(null);

        loadLoans();

      })
      .catch((error) => {

        console.error(
          "Update Loan Error:",
          error
        );

        alert(
          "Loan update failed: " +
          (
            error.response?.data?.message ||
            "Please check the loan details"
          )
        );

      });

  };


  // ==============================
  // DELETE LOAN
  // ==============================

  const deleteLoan = (id, loanId) => {

    const confirmDelete = window.confirm(
      `Are you sure you want to delete loan ${loanId}?`
    );

    if (!confirmDelete) {
      return;
    }

    axios
      .delete(
        `http://localhost:8080/api/loans/${id}`
      )
      .then((response) => {

        alert("Loan deleted successfully");

        loadLoans();

      })
      .catch((error) => {

        console.error(
          "Delete Loan Error:",
          error
        );

        alert("Loan deletion failed");

      });

  };


  // ==============================
  // VERIFY LOAN
  // ==============================

  const verifyLoan = (id) => {

    axios
      .post(
        `http://localhost:8080/api/loans/${id}/verify`
      )
      .then((response) => {

        alert(response.data.message);

        loadLoans();

      })
      .catch((error) => {

        console.error(
          "Verification Error:",
          error
        );

        alert("Loan verification failed");

      });

  };


  // ==============================
  // VERIFY HASH
  // ==============================

  const verifyHash = (id) => {

    axios
      .get(
        `http://localhost:8080/api/loans/${id}/verify-hash`
      )
      .then((response) => {

        alert(response.data.message);

        loadLoans();

      })
      .catch((error) => {

        console.error(
          "Hash Verification Error:",
          error
        );

        alert("Hash verification failed");

      });

  };


  // ==============================
  // FILTER LOANS
  // ==============================

  const filteredLoans = loans.filter((loan) => {

    const search =
      searchText.toLowerCase().trim();

    const matchesSearch =
      search === "" ||
      String(loan.loanId)
        .toLowerCase()
        .includes(search) ||
      String(loan.borrowerName || "")
        .toLowerCase()
        .includes(search);

    const matchesStatus =
      statusFilter === "ALL" ||
      String(loan.status || "")
        .toUpperCase() === statusFilter;

    const matchesVerification =
      verificationFilter === "ALL" ||
      String(loan.verificationStatus || "")
        .toUpperCase() === verificationFilter;

    return (
      matchesSearch &&
      matchesStatus &&
      matchesVerification
    );

  });


  // ==============================
  // CLEAR FILTERS
  // ==============================

  const clearFilters = () => {

    setSearchText("");
    setStatusFilter("ALL");
    setVerificationFilter("ALL");

  };


  // ==============================
  // RETURN UI
  // ==============================

  return (

    <div>

      <h2>All Loans</h2>


      {/* ==============================
          SEARCH & FILTERS
      ============================== */}

      <div className="loan-filters">

        <input
          type="text"
          placeholder="Search Loan ID or Borrower"
          value={searchText}
          onChange={(e) =>
            setSearchText(e.target.value)
          }
        />


        <select
          value={statusFilter}
          onChange={(e) =>
            setStatusFilter(e.target.value)
          }
        >

          <option value="ALL">
            All Status
          </option>

          <option value="ACTIVE">
            Active
          </option>

          <option value="CLOSED">
            Closed
          </option>

        </select>


        <select
          value={verificationFilter}
          onChange={(e) =>
            setVerificationFilter(e.target.value)
          }
        >

          <option value="ALL">
            All Verification
          </option>

          <option value="PENDING">
            Pending
          </option>

          <option value="VERIFIED">
            Verified
          </option>

          <option value="REJECTED">
            Rejected
          </option>

          <option value="MODIFIED">
            Modified
          </option>

        </select>


        <button
          onClick={clearFilters}
        >
          Clear Filters
        </button>


        <button
          onClick={loadLoans}
        >
          Refresh
        </button>

      </div>


      {/* ==============================
          ERROR
      ============================== */}

      {error && (

        <p>
          {error}
        </p>

      )}


      {/* ==============================
          RESULT COUNT
      ============================== */}

      <p>

        Showing{" "}
        <strong>
          {filteredLoans.length}
        </strong>{" "}
        of{" "}
        <strong>
          {loans.length}
        </strong>{" "}
        loans

      </p>


      {/* ==============================
          LOAN TABLE
      ============================== */}

      {filteredLoans.length === 0 ? (

        <p>
          No matching loans found.
        </p>

      ) : (

        <table>

          <thead>

            <tr>

              <th>ID</th>
              <th>Loan ID</th>
              <th>Borrower</th>
              <th>Loan Amount</th>
              <th>Interest Rate</th>
              <th>Loan Date</th>
              <th>Status</th>
              <th>Verification</th>
              <th>Actions</th>

            </tr>

          </thead>


          <tbody>

            {filteredLoans.map((loan) => (

              <tr key={loan.id}>

                <td>
                  {loan.id}
                </td>

                <td>
                  {loan.loanId}
                </td>

                <td>
                  {loan.borrowerName || "-"}
                </td>

                <td>
                  {loan.loanAmount}
                </td>

                <td>
                  {loan.interestRate}%
                </td>

                <td>
                  {loan.loanDate}
                </td>

                <td>
                  {loan.status}
                </td>

                <td>

                  <span
                    className={`status-badge ${
                      loan.verificationStatus
                        ? loan.verificationStatus.toLowerCase()
                        : ""
                    }`}
                  >
                    {loan.verificationStatus}
                  </span>

                </td>

                <td>

                  <button
                    onClick={() =>
                      viewLoan(loan.id)
                    }
                  >
                    View
                  </button>


                  <button
                    onClick={() =>
                      editLoan(loan)
                    }
                  >
                    Edit
                  </button>


                  <button
                    onClick={() =>
                      verifyLoan(loan.id)
                    }
                  >
                    Verify
                  </button>


                  <button
                    onClick={() =>
                      verifyHash(loan.id)
                    }
                  >
                    Hash
                  </button>


                  <button
                    onClick={() =>
                      deleteLoan(
                        loan.id,
                        loan.loanId
                      )
                    }
                  >
                    Delete
                  </button>

                </td>

              </tr>

            ))}

          </tbody>

        </table>

      )}


      {/* ==============================
          VIEW LOAN MODAL
      ============================== */}

      {selectedLoan && (

        <div className="modal-overlay">

          <div className="modal">

            <h2>
              Loan Details
            </h2>

            <p>
              <strong>ID:</strong>{" "}
              {selectedLoan.id}
            </p>

            <p>
              <strong>Loan ID:</strong>{" "}
              {selectedLoan.loanId}
            </p>

            <p>
              <strong>Borrower:</strong>{" "}
              {selectedLoan.borrowerName || "-"}
            </p>

            <p>
              <strong>Loan Amount:</strong>{" "}
              {selectedLoan.loanAmount}
            </p>

            <p>
              <strong>Interest Rate:</strong>{" "}
              {selectedLoan.interestRate}%
            </p>

            <p>
              <strong>Loan Date:</strong>{" "}
              {selectedLoan.loanDate}
            </p>

            <p>
              <strong>Status:</strong>{" "}
              {selectedLoan.status}
            </p>

            <p>
              <strong>Verification:</strong>{" "}
              {selectedLoan.verificationStatus}
            </p>

            <button
              onClick={() =>
                setSelectedLoan(null)
              }
            >
              Close
            </button>

          </div>

        </div>

      )}


      {/* ==============================
          EDIT LOAN MODAL
      ============================== */}

      {editingLoan && (

        <div className="modal-overlay">

          <div className="modal">

            <h2>
              Edit Loan
            </h2>


            <label>
              Loan ID
            </label>

            <input
              type="text"
              value={editingLoan.loanId}
              disabled
            />


            <label>
              Borrower Name
            </label>

            <input
              type="text"
              name="borrowerName"
              value={
                editingLoan.borrowerName || ""
              }
              onChange={handleEditChange}
            />


            <label>
              Loan Amount
            </label>

            <input
              type="number"
              name="loanAmount"
              value={
                editingLoan.loanAmount
              }
              onChange={handleEditChange}
            />


            <label>
              Interest Rate
            </label>

            <input
              type="number"
              step="0.1"
              name="interestRate"
              value={
                editingLoan.interestRate
              }
              onChange={handleEditChange}
            />


            <label>
              Loan Date
            </label>

            <input
              type="date"
              name="loanDate"
              value={
                editingLoan.loanDate || ""
              }
              onChange={handleEditChange}
            />


            <label>
              Status
            </label>

            <select
              name="status"
              value={
                editingLoan.status || ""
              }
              onChange={handleEditChange}
            >

              <option value="ACTIVE">
                ACTIVE
              </option>

              <option value="CLOSED">
                CLOSED
              </option>

            </select>


            <div className="modal-buttons">

              <button
                onClick={updateLoan}
              >
                Save Changes
              </button>

              <button
                onClick={() =>
                  setEditingLoan(null)
                }
              >
                Cancel
              </button>

            </div>

          </div>

        </div>

      )}

    </div>

  );

}

export default LoanList;

