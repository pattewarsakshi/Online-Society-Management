import { useEffect, useState } from "react";
import ResidentLayout from "../../layouts/ResidentLayout";
import api from "../../api/api";
import "./ResidentProfile.css";

export default function ResidentProfile() {
  const [profile, setProfile] = useState(null);

  useEffect(() => {
    api.get("/api/profile").then((res) => setProfile(res.data));
  }, []);

  if (!profile) return null;

  return (
    <ResidentLayout>
      <div className="profile-card">
        <h2>My Profile</h2>

        <div className="profile-row">
          <span>Name</span>
          <strong>{profile.firstName} {profile.lastName}</strong>
        </div>

        <div className="profile-row">
          <span>Email</span>
          <strong>{profile.email}</strong>
        </div>

        <div className="profile-row">
          <span>Phone</span>
          <strong>{profile.phone}</strong>
        </div>

        <div className="profile-row">
          <span>Role</span>
          <strong>{profile.role}</strong>
        </div>
      </div>
    </ResidentLayout>
  );
}
