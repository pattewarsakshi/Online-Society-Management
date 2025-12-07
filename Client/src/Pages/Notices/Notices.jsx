import DashboardLayout from "../../layouts/DashboardLayout";
import { useEffect, useState } from "react";
import "./Notices.css";

export default function Notices() {
  const [notices, setNotices] = useState([]);

  useEffect(() => {
    // Load dummy notices
    const dummy = [
      { id: 1, title: "Community Hall Booking Closed", date: "2025-01-05", status: "Active" },
      { id: 2, title: "Electricity maintenance on Sunday", date: "2025-01-06", status: "Active" },
      { id: 3, title: "Fire Drill Scheduled", date: "2025-01-08", status: "Inactive" },
      { id: 4, title: "New Security Guidelines", date: "2025-01-09", status: "Active" },
      { id: 5, title: "Parking Policy Update", date: "2025-01-10", status: "Active" },
    ];
    localStorage.setItem("notices", JSON.stringify(dummy));
    setNotices(dummy);
  }, []);

  return (
    <DashboardLayout>
      <div className="notices-container">
        <h2 className="page-title">Notices</h2>
        <p className="page-subtext">View and manage society notices</p>

        <div className="table-card">
          <table className="notices-table">
            <thead>
              <tr>
                <th>Title</th>
                <th>Date</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {[...notices].reverse().map((n) => (
                <tr key={n.id}>
                  <td>{n.title}</td>
                  <td style={{ textAlign: "center" }}>{n.date}</td>
                  <td style={{ textAlign: "center" }}>
                    <span className={`status-tag ${n.status.toLowerCase()}`}>
                      {n.status}
                    </span>
                  </td>
                </tr>
              ))}
              {notices.length === 0 && (
                <tr>
                  <td colSpan="3" style={{ textAlign: "center", padding: "15px", color: "#6b7280" }}>
                    No notices found.
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
