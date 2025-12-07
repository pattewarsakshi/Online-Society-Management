import DashboardLayout from "../../layouts/DashboardLayout";
import { useEffect, useState } from "react";
import "./Maintenance.css";

export default function Maintenance() {
  const [records, setRecords] = useState([]);
  const [statusFilter, setStatusFilter] = useState("All");
  const [searchText, setSearchText] = useState("");

  useEffect(() => {
    const dummy = loadDummyMaintenance(true); // force reset dummy data
    setRecords(dummy);
  }, []);

  const updateStatus = (id, paid) => {
    const updated = records.map((r) =>
      r.id === id ? { ...r, paid } : r
    );
    setRecords(updated);
    localStorage.setItem("maintenance", JSON.stringify(updated));
  };

  // Filtered & searched records
  const filteredRecords = records.filter((r) => {
    const matchesStatus =
      statusFilter === "All" ? true : r.paid === (statusFilter === "Paid");
    const matchesSearch =
      r.name.toLowerCase().includes(searchText.toLowerCase()) ||
      r.apartment.toLowerCase().includes(searchText.toLowerCase());
    return matchesStatus && matchesSearch;
  });

  return (
    <DashboardLayout>
      <div className="maintenance-container">
        <h2 className="page-title">Maintenance Records</h2>
        <p className="page-subtext">Track monthly maintenance payments</p>

        {/* FILTER & SEARCH */}
        <div className="filter-row">
          <input
            type="text"
            placeholder="Search by resident or apartment..."
            value={searchText}
            onChange={(e) => setSearchText(e.target.value)}
            className="search-input"
          />
          <select
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
            className="filter-select"
          >
            <option value="All">All Records</option>
            <option value="Paid">Paid</option>
            <option value="Pending">Pending</option>
          </select>
        </div>

        <div className="table-card">
          <table className="maintenance-table">
            <thead>
              <tr>
                <th>Resident Name</th>
                <th>Apartment</th>
                <th>Month</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Change Status</th>
              </tr>
            </thead>
            <tbody>
              {[...filteredRecords].reverse().map((r) => (
                <tr key={r.id}>
                  <td>{r.name}</td>
                  <td>{r.apartment}</td>
                  <td>{r.month}</td>
                  <td>₹{r.amount}</td>
                  <td>
                    <span className={`status-tag ${r.paid ? "paid" : "pending"}`}>
                      {r.paid ? "Paid" : "Pending"}
                    </span>
                  </td>
                  <td>
                    <select
                      value={r.paid ? "Paid" : "Pending"}
                      onChange={(e) => updateStatus(r.id, e.target.value === "Paid")}
                    >
                      <option>Pending</option>
                      <option>Paid</option>
                    </select>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {filteredRecords.length === 0 && (
            <p className="no-data-msg">No maintenance records found.</p>
          )}
        </div>
      </div>
    </DashboardLayout>
  );
}

// Load dummy data with optional reset
const loadDummyMaintenance = (reset = false) => {
  if (reset || !localStorage.getItem("maintenance")) {
    const dummy = [];
    const residents = [
      { id: 1, name: "Suresh Kumar", apartment: "A-101" },
      { id: 2, name: "Priya Sharma", apartment: "B-204" },
      { id: 3, name: "Karan Patel", apartment: "C-303" },
      { id: 4, name: "Amit Verma", apartment: "D-102" },
      { id: 5, name: "Riya Singh", apartment: "E-201" },
      { id: 6, name: "Neha Joshi", apartment: "F-105" },
      { id: 7, name: "Rahul Mehta", apartment: "G-402" },
      { id: 8, name: "Anjali Rao", apartment: "H-307" },
      { id: 9, name: "Vikram Singh", apartment: "I-210" },
      { id: 10, name: "Pooja Gupta", apartment: "J-111" },
    ];

    const months = [
      "January", "February", "March", "April", "May", "June",
      "July", "August", "September", "October", "November", "December"
    ];

    let idCounter = 1;
    residents.forEach((resident) => {
      months.forEach((month) => {
        dummy.push({
          id: idCounter++,
          name: resident.name,
          apartment: resident.apartment,
          month,
          amount: 1200,
          paid: Math.random() > 0.5,
        });
      });
    });

    localStorage.setItem("maintenance", JSON.stringify(dummy));
    return dummy;
  }

  return JSON.parse(localStorage.getItem("maintenance"));
};
