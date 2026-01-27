import { NavLink, useNavigate } from "react-router-dom";
import api from "../api/api";
import "./ResidentLayout.css";

export default function ResidentLayout({ children }) {
  const navigate = useNavigate();

  const logout = async () => {
    try {
      await api.post("/api/auth/logout");
    } catch (e) {}
    navigate("/login");
  };

  return (
    <div className="resident-layout">
      {/* SIDEBAR */}
      <aside className="resident-sidebar">
        <h2>Society</h2>

        <NavLink to="/resident">Dashboard</NavLink>
        <NavLink to="/resident/book-facility">Book Facility</NavLink>
        <NavLink to="/resident/maintenance">Maintenance</NavLink>
        <NavLink to="/resident/profile">Profile</NavLink>
      </aside>

      {/* MAIN */}
      <div className="resident-main">
        <header className="resident-header">
          <span>Resident Panel</span>
          <button onClick={logout}>Logout</button>
        </header>

        <main className="resident-content">
          {children}
        </main>
      </div>
    </div>
  );
}
