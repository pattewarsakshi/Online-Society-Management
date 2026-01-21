import { useState, useEffect } from "react";
import { useAuth } from "../../contexts/AuthContext";
import { Link } from "react-router-dom";
import DashboardLayout from "../../layouts/DashboardLayout";
import Card from "../../Components/Card";
import { toast } from "react-toastify";
import { FaEdit, FaTrash } from "react-icons/fa";
import "./Notices.css";

export default function Notices() {
  const { user } = useAuth();
  const [notices, setNotices] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [editForm, setEditForm] = useState({ title: "", description: "" });

  const isAdmin = user?.role === "admin";

  // useEffect(() => {
  //   loadNotices();
  // }, []);

  // const loadNotices = () => {
  //   const stored = localStorage.getItem("notices");
  //   if (stored) {
  //     try {
  //       const parsed = JSON.parse(stored);
  //       setNotices(Array.isArray(parsed) ? parsed : []);
  //     } catch (error) {
  //       console.error("Error loading notices:", error);
  //       setNotices([]);
  //     }
  //   } else {
  //     setNotices([]);
  //   }
  // };

  useEffect(() => {
  // On first load, if there are no notices, create some sample ones
  const stored = localStorage.getItem("notices");

  if (!stored) {
    const sampleNotices = [
      {
        id: `notice-${Date.now()}`,
        title: "Welcome to UrbanNest",
        description: "This is your first sample notice. Admins can add more.",
        status: "active",
        createdAt: new Date().toISOString(),
      },
      {
        id: `notice-${Date.now() + 1}`,
        title: "Maintenance Reminder",
        description: "Please pay your monthly maintenance by the 10th.",
        status: "active",
        createdAt: new Date().toISOString(),
      },
    ];

    localStorage.setItem("notices", JSON.stringify(sampleNotices));
    setNotices(sampleNotices);
  } else {
    loadNotices();
  }
}, []);

const loadNotices = () => {
  const stored = localStorage.getItem("notices");
  if (stored) {
    try {
      const parsed = JSON.parse(stored);
      setNotices(Array.isArray(parsed) ? parsed : []);
    } catch (error) {
      console.error("Error loading notices:", error);
      setNotices([]);
    }
  } else {
    setNotices([]);
  }
};

  const handleDelete = (id) => {
    if (!isAdmin) {
      toast.error("Only admins can delete notices");
      return;
    }

    if (window.confirm("Are you sure you want to delete this notice?")) {
      const updated = notices.filter((notice) => notice.id !== id);
      localStorage.setItem("notices", JSON.stringify(updated));
      setNotices(updated);
      toast.success("Notice deleted successfully");
    }
  };

  const handleEdit = (notice) => {
    if (!isAdmin) {
      toast.error("Only admins can edit notices");
      return;
    }

    setEditingId(notice.id);
    setEditForm({
      title: notice.title || "",
      description: notice.description || "",
    });
  };

  const handleSaveEdit = () => {
    if (!isAdmin) return;

    if (!editForm.title.trim() || !editForm.description.trim()) {
      toast.error("Title and description are required");
      return;
    }

    const updated = notices.map((notice) =>
      notice.id === editingId
        ? {
            ...notice,
            title: editForm.title,
            description: editForm.description,
            updatedAt: new Date().toISOString(),
          }
        : notice
    );

    localStorage.setItem("notices", JSON.stringify(updated));
    setNotices(updated);
    setEditingId(null);
    setEditForm({ title: "", description: "" });
    toast.success("Notice updated successfully");
  };

  const handleCancelEdit = () => {
    setEditingId(null);
    setEditForm({ title: "", description: "" });
  };

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

  return (
    <DashboardLayout>
      <div className="notices-page">
        <div className="notices-header">
          <h1 className="page-title">UrbanNest Notices</h1>
          {isAdmin && (
            <Link to="/notices/add" className="add-button">
              + Create Notice
            </Link>
          )}
        </div>

        {notices.length === 0 ? (
          <Card className="empty-state-card">
            <p className="empty-message">No notices available</p>
            {isAdmin && (
              <Link to="/notices/add" className="empty-link">
                Create your first notice
              </Link>
            )}
          </Card>
        ) : (
          <div className="notices-grid">
            {notices.map((notice) => (
              <Card key={notice.id} className="notice-card">
                {editingId === notice.id && isAdmin ? (
                  <div className="edit-form">
                    <input
                      type="text"
                      className="edit-input"
                      value={editForm.title}
                      onChange={(e) =>
                        setEditForm({ ...editForm, title: e.target.value })
                      }
                      placeholder="Notice Title"
                    />
                    <textarea
                      className="edit-textarea"
                      value={editForm.description}
                      onChange={(e) =>
                        setEditForm({ ...editForm, description: e.target.value })
                      }
                      placeholder="Notice Description"
                      rows="4"
                    />
                    <div className="edit-actions">
                      <button
                        className="save-btn"
                        onClick={handleSaveEdit}
                      >
                        Save
                      </button>
                      <button
                        className="cancel-btn"
                        onClick={handleCancelEdit}
                      >
                        Cancel
                      </button>
                    </div>
                  </div>
                ) : (
                  <>
                    <div className="notice-header">
                      <h3 className="notice-title">{notice.title}</h3>
                      {isAdmin && (
                        <div className="notice-actions">
                          <button
                            className="action-btn edit-btn"
                            onClick={() => handleEdit(notice)}
                            title="Edit Notice"
                          >
                            <FaEdit />
                          </button>
                          <button
                            className="action-btn delete-btn"
                            onClick={() => handleDelete(notice.id)}
                            title="Delete Notice"
                          >
                            <FaTrash />
                          </button>
                        </div>
                      )}
                    </div>
                    <p className="notice-description">{notice.description}</p>
                    <div className="notice-footer">
                      <span className="notice-date">
                        {notice.createdAt
                          ? `Posted: ${formatDate(notice.createdAt)}`
                          : "Date not available"}
                      </span>
                      {notice.updatedAt && notice.updatedAt !== notice.createdAt && (
                        <span className="notice-updated">
                          Updated: {formatDate(notice.updatedAt)}
                        </span>
                      )}
                    </div>
                  </>
                )}
              </Card>
            ))}
          </div>
        )}
      </div>
    </DashboardLayout>
  );
}
