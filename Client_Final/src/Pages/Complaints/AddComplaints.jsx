import { useState } from "react";
import { useAuth } from "../../contexts/AuthContext";
import { useNavigate } from "react-router-dom";
import DashboardLayout from "../../layouts/DashboardLayout";
import Card from "../../Components/Card";
import { toast } from "react-toastify";
import "./AddComplaints.css";

export default function AddComplaints() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    title: "",
    description: "",
  });
  const [errors, setErrors] = useState({});

  // Only members can access this page
  if (user?.role === "admin") {
    navigate("/complaints");
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

    if (formData.description.trim().length < 10) {
      newErrors.description = "Description must be at least 10 characters";
    }

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      toast.error("Please fill in all required fields");
      return;
    }

    // Create complaint object
    const complaint = {
      id: `complaint-${Date.now()}`,
      memberId: user?.id,
      memberEmail: user?.email,
      title: formData.title.trim(),
      description: formData.description.trim(),
      status: "pending",
      createdAt: new Date().toISOString(),
    };

    // Save to localStorage
    const existing = JSON.parse(localStorage.getItem("complaints") || "[]");
    existing.push(complaint);
    localStorage.setItem("complaints", JSON.stringify(existing));

    toast.success("Complaint raised successfully!");
    navigate("/complaints");
  };

  return (
    <DashboardLayout>
      <div className="add-complaints-page">
        <div className="page-header">
          <h1 className="page-title">Raise a Complaint</h1>
          <button
            className="back-button"
            onClick={() => navigate("/complaints")}
          >
            ← Back to Complaints
          </button>
        </div>

        <Card className="complaint-form-card">
          <form onSubmit={handleSubmit} className="complaint-form">
            <div className="form-group">
              <label htmlFor="title" className="form-label">
                Complaint Title *
              </label>
              <input
                type="text"
                id="title"
                name="title"
                className={`form-input ${errors.title ? "error" : ""}`}
                value={formData.title}
                onChange={handleChange}
                placeholder="Enter complaint title"
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
                placeholder="Describe your complaint in detail (minimum 10 characters)"
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
                onClick={() => navigate("/complaints")}
              >
                Cancel
              </button>
              <button type="submit" className="submit-btn">
                Submit Complaint
              </button>
            </div>
          </form>
        </Card>
      </div>
    </DashboardLayout>
  );
}
