import DashboardLayout from "../../layouts/DashboardLayout";
import { useEffect, useState } from "react";
import "./VisitorList.css"


export default function Visitors() {
  const [visitors, setVisitors] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [statusFilter, setStatusFilter] = useState("All"); // All / Approved / Pending

  useEffect(() => {
    const dummy = loadDummyVisitors(true); // force reset
    setVisitors(dummy);
  }, []);

  const updateStatus = (id, newStatus) => {
    const updated = visitors.map((v) =>
      v.id === id ? { ...v, status: newStatus } : v
    );
    setVisitors(updated);
    localStorage.setItem("visitors", JSON.stringify(updated));
  };

  // Filtered & searched visitors
  const filteredVisitors = visitors.filter((v) => {
    const matchesStatus =
      statusFilter === "All" ? true : v.status === statusFilter;

    const matchesSearch =
      v.name.toLowerCase().includes(searchText.toLowerCase()) ||
      v.purpose.toLowerCase().includes(searchText.toLowerCase());

    return matchesStatus && matchesSearch;
  });

  return (
    <DashboardLayout>
      <div className="visitors-container">
        <h2 className="page-title">
          Visitor Logs
        </h2>
        <p className="page-subtext">Track and manage visitors</p>

        {/* Filter & Search */}
        <div className="filter-row">
          <input
            type="text"
            placeholder="Search by visitor name or purpose..."
            value={searchText}
            onChange={(e) => setSearchText(e.target.value)}
            className="search-input"
          />
          <select
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
            className="filter-select"
          >
            <option value="All">All</option>
            <option value="Approved">Approved</option>
            <option value="Pending">Pending</option>
          </select>
        </div>

        <div className="table-card">
          <table className="visitors-table">
            <thead>
              <tr>
                <th>Visitor Name</th>
                <th>Apartment</th>
                <th>Purpose</th>
                <th>Date</th>
                <th>Status</th>
                <th>Change Status</th>
              </tr>
            </thead>
            <tbody>
              {[...filteredVisitors].reverse().map((v) => (
                <tr key={v.id}>
                  <td>{v.name}</td>
                  <td>{v.apartment}</td>
                  <td>{v.purpose}</td>
                  <td>{v.date}</td>
                  <td>
                    <span className={`status-tag ${v.status.toLowerCase()}`}>
                      {v.status}
                    </span>
                  </td>
                  <td>
                    <select
                      value={v.status}
                      onChange={(e) => updateStatus(v.id, e.target.value)}
                    >
                      <option>Pending</option>
                      <option>Approved</option>
                    </select>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {filteredVisitors.length === 0 && (
            <p className="no-data-msg">No visitors found.</p>
          )}
        </div>
      </div>
    </DashboardLayout>
  );
}

// Load big dummy visitor data
const loadDummyVisitors = (reset = false) => {
  if (reset || !localStorage.getItem("visitors")) {
    const dummy = [];
    const residents = [
      { apartment: "A-101" }, { apartment: "B-204" }, { apartment: "C-303" },
      { apartment: "D-102" }, { apartment: "E-201" }, { apartment: "F-105" },
      { apartment: "G-402" }, { apartment: "H-307" }, { apartment: "I-210" },
      { apartment: "J-111" },
    ];

    const visitorNames = [
      "Amazon Delivery", "Electrician", "Plumber", "Pizza Delivery",
      "Friend", "Courier", "Carpenter", "Housekeeping", "Gardener", "Inspector"
    ];

    const purposes = [
      "Delivery", "Repair", "Service", "Visit", "Inspection", "Maintenance"
    ];

    let idCounter = 1;
    for (let day = 1; day <= 30; day++) {
      residents.forEach((res) => {
        const visitor = visitorNames[Math.floor(Math.random() * visitorNames.length)];
        const purpose = purposes[Math.floor(Math.random() * purposes.length)];
        const status = Math.random() > 0.5 ? "Approved" : "Pending";
        dummy.push({
          id: idCounter++,
          name: visitor,
          apartment: res.apartment,
          purpose,
          date: `2025-01-${String(day).padStart(2, "0")}`,
          status,
        });
      });
    }

    localStorage.setItem("visitors", JSON.stringify(dummy));
    return dummy;
  }

  return JSON.parse(localStorage.getItem("visitors"));
};

