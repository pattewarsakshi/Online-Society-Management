import DashboardLayout from "../../layouts/DashboardLayout";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import "./Profile.css";

export default function Profile() {
  const [profile, setProfile] = useState({
    name: "",
    email: "",
    phone: "",
    apartment: "",
  });

  useEffect(() => {
    // Load profile from localStorage or set default
    const storedProfile = JSON.parse(localStorage.getItem("profile"));
    if (storedProfile) setProfile(storedProfile);
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProfile((prev) => ({ ...prev, [name]: value }));
  };

  const handleSave = (e) => {
    e.preventDefault();

    // Simple validation
    if (!profile.name || !profile.email || !profile.phone || !profile.apartment) {
      toast.error("All fields are required");
      return;
    }

    // Save to localStorage
    localStorage.setItem("profile", JSON.stringify(profile));
    toast.success("Profile updated successfully!");
  };

  return (
    <DashboardLayout>
      <div className="profile-container">
        <h2 className="page-title">My Profile</h2>
        <p className="page-subtext">Update your personal information</p>

        <div className="profile-card">
          <form onSubmit={handleSave} className="profile-form">

            <label>Name *</label>
            <input
              type="text"
              name="name"
              value={profile.name}
              onChange={handleChange}
              placeholder="Enter your name"
            />

            <label>Email *</label>
            <input
              type="email"
              name="email"
              value={profile.email}
              onChange={handleChange}
              placeholder="Enter your email"
            />

            <label>Phone *</label>
            <input
              type="text"
              name="phone"
              value={profile.phone}
              onChange={handleChange}
              placeholder="Enter phone number"
            />

            <label>Apartment / Flat *</label>
            <input
              type="text"
              name="apartment"
              value={profile.apartment}
              onChange={handleChange}
              placeholder="Enter your apartment"
            />

            <button type="submit" className="save-button">Save Changes</button>
          </form>
        </div>
      </div>
    </DashboardLayout>
  );
}

