import { useState, useEffect } from "react";
import { useAuth } from "../../contexts/AuthContext";
import DashboardLayout from "../../layouts/DashboardLayout";
import Card from "../../Components/Card";
import { toast } from "react-toastify";
import "./Profile.css";

export default function Profile() {
  const { user, updateUser } = useAuth();
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    name: "",
    phone: "",
    email: "",
  });
  const [errors, setErrors] = useState({});

  const isAdmin = user?.role === "admin";

  useEffect(() => {
    if (user) {
      loadUserData();
    }
  }, [user]);

  const loadUserData = () => {
    if (isAdmin) {
      // Admin profile - use loggedUser data
      setFormData({
        name: user.name || "",
        phone: "", // Admin might not have phone
        email: user.email || "",
      });
    } else {
      // Member profile - load from members array
      const members = JSON.parse(localStorage.getItem("members") || "[]");
      const memberData = members.find(
        (m) => m.id === user.id || m.email === user.email
      );

      if (memberData) {
        setFormData({
          name: memberData.name || "",
          phone: memberData.phone || "",
          email: memberData.email || "",
        });
      } else {
        // Fallback to loggedUser data
        setFormData({
          name: user.name || "",
          phone: "",
          email: user.email || "",
        });
      }
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    setErrors({});
  };

  const handleEdit = () => {
    setIsEditing(true);
  };

  const handleCancel = () => {
    setIsEditing(false);
    loadUserData();
    setErrors({});
  };

  const handleSave = () => {
    const newErrors = {};

    if (!formData.name.trim()) {
      newErrors.name = "Name is required";
    }

    if (!isAdmin && !formData.phone.trim()) {
      newErrors.phone = "Phone number is required";
    }

    if (!isAdmin && formData.phone && !/^[0-9]{10}$/.test(formData.phone)) {
      newErrors.phone = "Phone number must be exactly 10 digits";
    }

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      toast.error("Please fix the errors before saving");
      return;
    }

    if (isAdmin) {
      // Update admin profile in loggedUser
      const updatedUser = {
        ...user,
        name: formData.name.trim(),
      };
      updateUser(updatedUser);
      toast.success("Profile updated successfully");
    } else {
      // Update member profile in both loggedUser and members array
      const members = JSON.parse(localStorage.getItem("members") || "[]");
      const updatedMembers = members.map((member) =>
        member.id === user.id || member.email === user.email
          ? {
              ...member,
              name: formData.name.trim(),
              phone: formData.phone.trim(),
            }
          : member
      );

      localStorage.setItem("members", JSON.stringify(updatedMembers));

      // Update loggedUser
      const updatedUser = {
        ...user,
        name: formData.name.trim(),
      };
      updateUser(updatedUser);

      toast.success("Profile updated successfully");
    }

    setIsEditing(false);
  };

  return (
    <DashboardLayout>
      <div className="profile-page">
        <div className="profile-header">
          <h1 className="page-title">
            {isAdmin ? "Admin Profile" : "My Profile"}
          </h1>
        </div>

        <Card className="profile-card">
          <div className="profile-content">
            <div className="profile-avatar">
              <div className="avatar-circle">
                {formData.name
                  ? formData.name.charAt(0).toUpperCase()
                  : "U"}
              </div>
            </div>

            <div className="profile-form">
              <div className="form-group">
                <label className="form-label">Name</label>
                {isEditing ? (
                  <>
                    <input
                      type="text"
                      name="name"
                      className={`form-input ${errors.name ? "error" : ""}`}
                      value={formData.name}
                      onChange={handleChange}
                      placeholder="Enter your name"
                    />
                    {errors.name && (
                      <span className="error-message">{errors.name}</span>
                    )}
                  </>
                ) : (
                  <div className="form-value">{formData.name || "N/A"}</div>
                )}
              </div>

              <div className="form-group">
                <label className="form-label">Email</label>
                <div className="form-value read-only">
                  {formData.email || "N/A"}
                  <span className="read-only-badge">Read Only</span>
                </div>
              </div>

              {!isAdmin && (
                <div className="form-group">
                  <label className="form-label">Phone Number</label>
                  {isEditing ? (
                    <>
                      <input
                        type="text"
                        name="phone"
                        className={`form-input ${errors.phone ? "error" : ""}`}
                        value={formData.phone}
                        onChange={handleChange}
                        placeholder="Enter 10-digit phone number"
                        maxLength="10"
                      />
                      {errors.phone && (
                        <span className="error-message">{errors.phone}</span>
                      )}
                    </>
                  ) : (
                    <div className="form-value">{formData.phone || "N/A"}</div>
                  )}
                </div>
              )}

              {isAdmin && (
                <div className="form-group">
                  <label className="form-label">Role</label>
                  <div className="form-value">
                    <span className="role-badge admin">Admin</span>
                  </div>
                </div>
              )}

              <div className="form-actions">
                {isEditing ? (
                  <>
                    <button
                      type="button"
                      className="cancel-btn"
                      onClick={handleCancel}
                    >
                      Cancel
                    </button>
                    <button
                      type="button"
                      className="save-btn"
                      onClick={handleSave}
                    >
                      Save Changes
                    </button>
                  </>
                ) : (
                  <button
                    type="button"
                    className="edit-btn"
                    onClick={handleEdit}
                  >
                    Edit Profile
                  </button>
                )}
              </div>
            </div>
          </div>
        </Card>
      </div>
    </DashboardLayout>
  );
}
