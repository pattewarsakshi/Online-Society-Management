import { useState, useEffect } from "react";
import { useAuth } from "../../contexts/AuthContext";
import { Link } from "react-router-dom";
import DashboardLayout from "../../layouts/DashboardLayout";
import Card from "../../Components/Card";
import { toast } from "react-toastify";
import "./Complaints.css";

export default function Complaints() {
  const { user } = useAuth();
  const [complaints, setComplaints] = useState([]);
  const [filterStatus, setFilterStatus] = useState("all");

  const isAdmin = user?.role === "admin";

  useEffect(() => {
    loadComplaints();
  }, [user]);

  const loadComplaints = () => {
    const stored = localStorage.getItem("complaints");
    if (stored) {
      try {
        const parsed = JSON.parse(stored);
        const allComplaints = Array.isArray(parsed) ? parsed : [];

        // Filter: Members see only their own, Admin sees all
        const filtered = isAdmin
          ? allComplaints
          : allComplaints.filter(
              (complaint) =>
                complaint.memberId === user?.id ||
                complaint.memberEmail === user?.email
            );

        setComplaints(filtered);
      } catch (error) {
        console.error("Error loading complaints:", error);
        setComplaints([]);
      }
    } else {
      setComplaints([]);
    }
  };

  const handleStatusChange = (complaintId, newStatus) => {
    if (!isAdmin) {
      toast.error("Only admins can change complaint status");
      return;
    }

    const allComplaints = JSON.parse(
      localStorage.getItem("complaints") || "[]"
    );
    const updated = allComplaints.map((complaint) =>
      complaint.id === complaintId
        ? { ...complaint, status: newStatus }
        : complaint
    );

    localStorage.setItem("complaints", JSON.stringify(updated));
    loadComplaints();
    toast.success("Complaint status updated");
  };

  const getPendingCount = () => {
    return complaints.filter((c) => c.status === "pending").length;
  };

  const getStatusColor = (status) => {
    switch (status) {
      case "pending":
        return "#f59e0b";
      case "resolved":
        return "#10b981";
      default:
        return "#6b7280";
    }
  };

  const getStatusBgColor = (status) => {
    switch (status) {
      case "pending":
        return "#fef3c7";
      case "resolved":
        return "#d1fae5";
      default:
        return "#f3f4f6";
    }
  };

  const filteredComplaints =
    filterStatus === "all"
      ? complaints
      : complaints.filter((c) => c.status === filterStatus);

  const formatDate = (dateString) => {
    if (!dateString) return "Unknown date";
    const date = new Date(dateString);
    return date.toLocaleDateString("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  const pendingCount = getPendingCount();

  return (
    <DashboardLayout>
      <div className="complaints-page">
        <div className="complaints-header">
          <div>
            <h1 className="page-title">Complaints</h1>
            {!isAdmin && pendingCount > 0 && (
              <span className="pending-badge">{pendingCount} Pending</span>
            )}
          </div>
          {!isAdmin && (
            <Link to="/complaints/add" className="add-button">
              + Raise Complaint
            </Link>
          )}
        </div>

        {/* Filter Tabs (Admin only) */}
        {isAdmin && (
          <div className="filter-tabs">
            <button
              className={`filter-tab ${filterStatus === "all" ? "active" : ""}`}
              onClick={() => setFilterStatus("all")}
            >
              All ({complaints.length})
            </button>
            <button
              className={`filter-tab ${
                filterStatus === "pending" ? "active" : ""
              }`}
              onClick={() => setFilterStatus("pending")}
            >
              Pending ({complaints.filter((c) => c.status === "pending").length})
            </button>
            <button
              className={`filter-tab ${
                filterStatus === "resolved" ? "active" : ""
              }`}
              onClick={() => setFilterStatus("resolved")}
            >
              Resolved (
              {complaints.filter((c) => c.status === "resolved").length})
            </button>
          </div>
        )}

        {filteredComplaints.length === 0 ? (
          <Card className="empty-state-card">
            <p className="empty-message">
              {isAdmin
                ? "No complaints found"
                : "You haven't raised any complaints yet"}
            </p>
            {!isAdmin && (
              <Link to="/complaints/add" className="empty-link">
                Raise your first complaint
              </Link>
            )}
          </Card>
        ) : (
          <div className="complaints-list">
            {filteredComplaints.map((complaint) => (
              <Card key={complaint.id} className="complaint-card">
                <div className="complaint-header">
                  <div className="complaint-title-section">
                    <h3 className="complaint-title">{complaint.title}</h3>
                    <span
                      className="status-badge"
                      style={{
                        backgroundColor: getStatusBgColor(complaint.status),
                        color: getStatusColor(complaint.status),
                      }}
                    >
                      {complaint.status?.toUpperCase() || "PENDING"}
                    </span>
                  </div>
                  {isAdmin && complaint.status === "pending" && (
                    <button
                      className="resolve-btn"
                      onClick={() =>
                        handleStatusChange(complaint.id, "resolved")
                      }
                    >
                      Mark as Resolved
                    </button>
                  )}
                </div>

                <p className="complaint-description">
                  {complaint.description}
                </p>

                <div className="complaint-footer">
                  <div className="complaint-meta">
                    <span className="complaint-date">
                      {complaint.createdAt
                        ? `Raised: ${formatDate(complaint.createdAt)}`
                        : "Date not available"}
                    </span>
                    {isAdmin && complaint.memberEmail && (
                      <span className="complaint-member">
                        By: {complaint.memberEmail}
                      </span>
                    )}
                  </div>
                </div>
              </Card>
            ))}
          </div>
        )}
      </div>
    </DashboardLayout>
  );
}
