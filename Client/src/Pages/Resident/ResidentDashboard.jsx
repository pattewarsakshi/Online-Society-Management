import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import ResidentLayout from "../../layouts/ResidentLayout";
import api from "../../api/api";
import "./ResidentDashboard.css";

export default function ResidentDashboard() {
  const [noticeCount, setNoticeCount] = useState(0);
  const [facilityCount, setFacilityCount] = useState(0);
  const [pendingMaintenance, setPendingMaintenance] = useState(0);

  useEffect(() => {
    loadDashboardCounts();
  }, []);

  const loadDashboardCounts = async () => {
    try {
      const noticesRes = await api.get("/api/notices");
      setNoticeCount(noticesRes.data.length);

      const facilityRes = await api.get("/api/facilities/active");
      setFacilityCount(facilityRes.data.length);

      const maintenanceRes = await api.get("/api/resident/maintenance");
      const pending = maintenanceRes.data.filter(
        m => m.status !== "PAID"
      ).length;
      setPendingMaintenance(pending);
    } catch (e) {
      console.error("Dashboard load failed");
    }
  };

  return (
    <ResidentLayout>
      <h2 className="page-title">Resident Dashboard</h2>
      <p className="page-subtitle">
        Welcome! Manage your society activities from here.
      </p>

      <div className="dashboard-grid">

        <Link to="/resident/notices" className="dashboard-card">
          <span className="icon">📢</span>
          <h3>Notices</h3>
          <p>{noticeCount} active notices</p>
        </Link>

        <Link to="/resident/book-facility" className="dashboard-card">
          <span className="icon">🏢</span>
          <h3>Facilities</h3>
          <p>{facilityCount} facilities available</p>
        </Link>

        <Link to="/resident/maintenance" className="dashboard-card">
          <span className="icon">💰</span>
          <h3>Maintenance</h3>
          <p>{pendingMaintenance} pending payments</p>
        </Link>

        <Link to="/resident/profile" className="dashboard-card">
          <span className="icon">👤</span>
          <h3>Profile</h3>
          <p>View & update profile</p>
        </Link>

      </div>
    </ResidentLayout>
  );
}
