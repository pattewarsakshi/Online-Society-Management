import DashboardLayout from "../../layouts/DashboardLayout";
import { useEffect, useState } from "react";
import "./AdminDashboard.css";

export default function AdminDashboard() {
  const [members, setMembers] = useState([]);
  const [notices, setNotices] = useState([]);
  const [complaints, setComplaints] = useState([]);
  const [maintenance, setMaintenance] = useState([]);
  const [visitors, setVisitors] = useState([]);
  const [parking, setParking] = useState([]);
  const [documents, setDocuments] = useState([]);
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    loadDummyData(); // load dummy data only if empty

    setMembers(JSON.parse(localStorage.getItem("members") || "[]"));
    setNotices(JSON.parse(localStorage.getItem("notices") || "[]"));
    setComplaints(JSON.parse(localStorage.getItem("complaints") || "[]"));
    setMaintenance(JSON.parse(localStorage.getItem("maintenance") || "[]"));
    setVisitors(JSON.parse(localStorage.getItem("visitors") || "[]"));
    setParking(JSON.parse(localStorage.getItem("parking") || "[]"));
    setDocuments(JSON.parse(localStorage.getItem("documents") || "[]"));
    setNotifications(JSON.parse(localStorage.getItem("notifications") || "[]"));
  }, []);

  const pendingComplaints = complaints.filter((c) => c.status === "Pending").length;
  const unpaidMaintenance = maintenance.filter((m) => !m.paid).length;
  const newNotifications = notifications.filter((n) => !n.seen).length;

  return (
    <DashboardLayout>
      <div className="admin-container">

        <h2 className="dashboard-title">Admin Dashboard</h2>
        <p className="dashboard-subtext">Overview of Society Management System</p>

        <div className="stats-grid">
          <div className="stat-card"><h3>{members.length}</h3><p>Registered Members</p></div>
          <div className="stat-card"><h3>{notices.length}</h3><p>Active Notices</p></div>
          <div className="stat-card"><h3>{complaints.length}</h3><p>Complaints</p></div>
          <div className="stat-card"><h3>{maintenance.length}</h3><p>Maintenance Records</p></div>
          <div className="stat-card"><h3>{visitors.length}</h3><p>Visitor Logs</p></div>
          <div className="stat-card"><h3>{parking.length}</h3><p>Parking Slots</p></div>
          <div className="stat-card"><h3>{documents.length}</h3><p>Documents Uploaded</p></div>
          <div className="stat-card"><h3>{notifications.length}</h3><p>Notifications Sent </p></div>
        </div>

        {/* Recent Members */}
        <div className="table-card">
          <h3>Latest Members</h3>

          <table className="admin-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Flat</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Date Joined</th>
              </tr>
            </thead>

            <tbody>
              {[...members].reverse().slice(0, 6).map((m) => (
                <tr key={m.id}>
                  <td>{m.name}</td>
                  <td>{m.apartment}</td>
                  <td>{m.email}</td>
                  <td>{m.phone}</td>
                  <td>{m.createdAt.split("T")[0]}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Recent Notices */}
        <div className="table-card">
          <h3>Recent Notices</h3>

          <table className="admin-table">
            <thead>
              <tr>
                <th>Notice Title</th>
                <th>Date</th>
              </tr>
            </thead>

            <tbody>
              {notices.slice(-4).map((n) => (
                <tr key={n.id}>
                  <td>{n.title}</td>
                  <td>{n.date}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Complaints Summary */}
        <div className="table-card">
          <h3>Complaint Status Summary</h3>

          <table className="admin-table">
            <thead>
              <tr>
                <th>Issue</th>
                <th>Status</th>
              </tr>
            </thead>

            <tbody>
              {complaints.map((c) => (
                <tr key={c.id}>
                  <td>{c.type}</td>
                  <td style={{ color: c.status === "Pending" ? "red" : "green" }}>
                    {c.status}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Visitors Table */}
        <div className="table-card">
          <h3>Recent Visitors</h3>

          <table className="admin-table">
            <thead>
              <tr>
                <th>Visitor Name</th>
                <th>Date</th>
              </tr>
            </thead>

            <tbody>
              {visitors.map((v) => (
                <tr key={v.id}>
                  <td>{v.name}</td>
                  <td>{v.date}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

      </div>
    </DashboardLayout>
  );
}

const loadDummyData = () => {
  if (!localStorage.getItem("members")) {
    localStorage.setItem(
      "members",
      JSON.stringify([
        {
          id: 1,
          name: "Suresh Kumar",
          apartment: "A-101",
          email: "suresh@example.com",
          phone: "9876543210",
          createdAt: "2025-01-02T10:00:00",
        },
        {
          id: 2,
          name: "Priya Sharma",
          apartment: "B-204",
          email: "priya@example.com",
          phone: "9900123456",
          createdAt: "2025-01-03T10:00:00",
        },
        {
          id: 3,
          name: "Karan Patel",
          apartment: "C-303",
          email: "karan@example.com",
          phone: "9999988888",
          createdAt: "2025-01-06T10:00:00",
        },
        {
          id: 3,
          name: "Karan Patel",
          apartment: "C-303",
          email: "karan@example.com",
          phone: "9999988888",
          createdAt: "2025-01-06T10:00:00",
        },
      ])
    );
  }
}

if (!localStorage.getItem("notices")) {
    localStorage.setItem(
      "notices",
      JSON.stringify([
        { id: 1, title: "Community Hall Booking Closed", date: "2025-01-05" },
        { id: 2, title: "Electricity maintenance on Sunday", date: "2025-01-06" },
      ])
    );
  }

  if (!localStorage.getItem("complaints")) {
    localStorage.setItem(
      "complaints",
      JSON.stringify([
        { id: 1, type: "Plumbing", status: "Pending" },
        { id: 2, type: "Lift Issue", status: "Resolved" },
        { id: 3, type: "Security", status: "In Progress" },
      ])
    );
  }

  if (!localStorage.getItem("maintenance")) {
    localStorage.setItem(
      "maintenance",
      JSON.stringify([
        { id: 1, month: "January", amount: 1200, paid: true },
        { id: 2, month: "February", amount: 1200, paid: false },
      ])
    );
  }

  if (!localStorage.getItem("visitors")) {
    localStorage.setItem(
      "visitors",
      JSON.stringify([
        { id: 1, name: "Amazon Delivery", date: "2025-01-06" },
        { id: 2, name: "Electrician", date: "2025-01-07" },
      ])
    );
  }

  if (!localStorage.getItem("parking")) {
    localStorage.setItem(
      "parking",
      JSON.stringify([
        { id: 1, vehicle: "MH12AB1234", slot: "P-09" },
        { id: 2, vehicle: "MH14XY8899", slot: "P-12" },
      ])
    );
  }

  if (!localStorage.getItem("documents")) {
    localStorage.setItem(
      "documents",
      JSON.stringify([
        { id: 1, name: "Society Report 2024", uploaded: "2025-01-01" },
        { id: 2, name: "Annual Budget", uploaded: "2025-01-05" },
      ])
    );
  }

  if (!localStorage.getItem("notifications")) {
    localStorage.setItem(
      "notifications",
      JSON.stringify([
        { id: 1, msg: "Meeting on Saturday", seen: false },
        { id: 2, msg: "Maintenance reminder", seen: true },
      ])
    );
  }


