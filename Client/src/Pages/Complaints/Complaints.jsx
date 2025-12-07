import DashboardLayout from "../../layouts/DashboardLayout";
import { useEffect, useState } from "react";
import "./Complaints.css";

export default function Complaints() {
  const [complaints, setComplaints] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [statusFilter, setStatusFilter] = useState("All");

  useEffect(() => {
    // Always load dummy data
    const dummy = [
      { id: 1, name: "Suresh Kumar", type: "Plumbing", description: "Water leak in kitchen area.", date: "2025-01-11", status: "Pending" },
      { id: 2, name: "Priya Sharma", type: "Lift Issue", description: "Lift stuck between 3rd & 4th floor.", date: "2025-01-10", status: "In Progress" },
      { id: 3, name: "Karan Patel", type: "Security", description: "Unauthorized person entered main gate.", date: "2025-01-09", status: "Resolved" },
      { id: 4, name: "Amit Verma", type: "Parking", description: "Car parked in wrong slot.", date: "2025-01-10", status: "Pending" },
      { id: 5, name: "Riya Singh", type: "Cleanliness", description: "Garbage not picked up on 2nd floor.", date: "2025-01-12", status: "Pending" },
    ];
    localStorage.setItem("complaints", JSON.stringify(dummy));
    setComplaints(dummy);
  }, []);

  const updateStatus = (id, newStatus) => {
    const updated = complaints.map((c) =>
      c.id === id ? { ...c, status: newStatus } : c
    );
    setComplaints(updated);
    localStorage.setItem("complaints", JSON.stringify(updated));
  };

  // Filter complaints based on search text and status
  const filteredComplaints = complaints.filter((c) => {
    const matchesStatus = statusFilter === "All" || c.status === statusFilter;
    const matchesSearch =
      c.name.toLowerCase().includes(searchText.toLowerCase()) ||
      c.type.toLowerCase().includes(searchText.toLowerCase()) ||
      c.description.toLowerCase().includes(searchText.toLowerCase());
    return matchesStatus && matchesSearch;
  });

  return (
    <DashboardLayout>
      <div className="complaints-container">
        <h2 className="page-title">Complaints Management</h2>
        <p className="page-subtext">Track and manage user complaints</p>

        {/* Filter Section */}
        <div className="filter-row">
          <input
            type="text"
            placeholder="Search by name, type, description..."
            value={searchText}
            onChange={(e) => setSearchText(e.target.value)}
            className="search-input"
          />
          <select
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
            className="filter-select"
          >
            <option value="All">All Status</option>
            <option value="Pending">Pending</option>
            <option value="In Progress">In Progress</option>
            <option value="Resolved">Resolved</option>
          </select>
        </div>

        <div className="table-card">
          <table className="complaints-table">
            <thead>
              <tr>
                <th>Resident Name</th>
                <th>Type</th>
                <th>Description</th>
                <th>Date Filed</th>
                <th>Status</th>
                <th>Change Status</th>
              </tr>
            </thead>
            <tbody>
              {[...filteredComplaints].reverse().map((c) => (
                <tr key={c.id}>
                  <td>{c.name}</td>
                  <td>{c.type}</td>
                  <td>{c.description}</td>
                  <td>{c.date}</td>
                  <td>
                    <span
                      className={`status-tag ${c.status.replace(" ", "").toLowerCase()}`}
                    >
                      {c.status}
                    </span>
                  </td>
                  <td>
                    <select
                      value={c.status}
                      onChange={(e) => updateStatus(c.id, e.target.value)}
                    >
                      <option>Pending</option>
                      <option>In Progress</option>
                      <option>Resolved</option>
                    </select>
                  </td>
                </tr>
              ))}
              {filteredComplaints.length === 0 && (
                <tr>
                  <td colSpan="6" style={{ textAlign: "center", padding: "15px", color: "#6b7280" }}>
                    No complaints found.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </DashboardLayout>
  );
}
