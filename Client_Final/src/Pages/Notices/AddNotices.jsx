import { useState } from "react";
import { useAuth } from "../../contexts/AuthContext";
import { useNavigate } from "react-router-dom";
import DashboardLayout from "../../layouts/DashboardLayout";
import Card from "../../Components/Card";
import { toast } from "react-toastify";
import "./AddNotices.css";

export default function AddNotices() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    title: "",
    description: "",
  });
  const [errors, setErrors] = useState({});

  // Only admins can access this page
  if (user?.role !== "admin") {
    navigate("/notices");
    return null;
  }

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    setErrors({});
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const newErrors = {};

    if (!formData.title.trim()) {
      newErrors.title = "Title is required";
    }

    if (!formData.description.trim()) {
      newErrors.description = "Description is required";
    }

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      toast.error("Please fill in all required fields");
      return;
    }

    // Create notice object
    const notice = {
      id: `notice-${Date.now()}`,
      title: formData.title.trim(),
      description: formData.description.trim(),
      status: "active",
      createdAt: new Date().toISOString(),
    };

    // Save to localStorage
    const existing = JSON.parse(localStorage.getItem("notices") || "[]");
    existing.push(notice);
    localStorage.setItem("notices", JSON.stringify(existing));

    toast.success("Notice created successfully!");
    navigate("/notices");
  };

  return (
    <DashboardLayout>
      <div className="add-notices-page">
        <div className="page-header">
          <h1 className="page-title">Create Notice</h1>
          <button
            className="back-button"
            onClick={() => navigate("/notices")}
          >
            ← Back to Notices
          </button>
        </div>

        <Card className="notice-form-card">
          <form onSubmit={handleSubmit} className="notice-form">
            <div className="form-group">
              <label htmlFor="title" className="form-label">
                Notice Title *
              </label>
              <input
                type="text"
                id="title"
                name="title"
                className={`form-input ${errors.title ? "error" : ""}`}
                value={formData.title}
                onChange={handleChange}
                placeholder="Enter notice title"
              />
              {errors.title && (
                <span className="error-message">{errors.title}</span>
              )}
            </div>

            <div className="form-group">
              <label htmlFor="description" className="form-label">
                Description *
              </label>
              <textarea
                id="description"
                name="description"
                className={`form-textarea ${errors.description ? "error" : ""}`}
                value={formData.description}
                onChange={handleChange}
                placeholder="Enter notice description"
                rows="6"
              />
              {errors.description && (
                <span className="error-message">{errors.description}</span>
              )}
            </div>

            <div className="form-actions">
              <button
                type="button"
                className="cancel-btn"
                onClick={() => navigate("/notices")}
              >
                Cancel
              </button>
              <button type="submit" className="submit-btn">
                Create Notice
              </button>
            </div>
          </form>
        </Card>
      </div>
    </DashboardLayout>
  );
}
