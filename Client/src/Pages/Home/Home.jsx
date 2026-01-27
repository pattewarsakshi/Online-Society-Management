import { useNavigate } from "react-router-dom";
import "./Home.css";

export default function Home() {
  const navigate = useNavigate();

  return (
    <div className="home">

      {/* HERO */}
      <section className="hero">
        <h1>
          Smart <span>Housing Society</span> Management
        </h1>

        <p>
          A complete system to manage residents, maintenance, facilities,
          security, and daily operations — all in one place.
        </p>

        <div className="hero-actions">
          <button className="btn primary" onClick={() => navigate("/register-society")}>
            Register Your Society
          </button>

          <button className="btn secondary" onClick={() => navigate("/resident/register")}>
            Resident Register
          </button>

          <button className="btn outline" onClick={() => navigate("/login")}>
            Login
          </button>
        </div>
      </section>

      {/* FEATURES */}
      <section className="features">
        <h2>Powerful Features</h2>

        <div className="feature-grid">
          <div className="feature-card">
            <h3>👥 Resident Management</h3>
            <p>Manage residents, flats, roles and profiles easily.</p>
          </div>

          <div className="feature-card">
            <h3>💰 Maintenance Tracking</h3>
            <p>Create bills, upload proofs, approve payments.</p>
          </div>

          <div className="feature-card">
            <h3>🏢 Facility Booking</h3>
            <p>Book society facilities with date availability.</p>
          </div>

          <div className="feature-card">
            <h3>🛡 Security Management</h3>
            <p>Visitor logs, guard dashboards, gate control.</p>
          </div>
        </div>
      </section>

      {/* HOW IT WORKS */}
      <section className="workflow">
        <h2>How It Works</h2>
        <ul>
          <li>Society is registered by Admin</li>
          <li>Residents self-register</li>
          <li>Admin assigns flats & approvals</li>
          <li>Daily society operations managed digitally</li>
        </ul>
      </section>

      {/* FOOTER */}
      <footer className="footer">
        © 2026 Smart Housing Society Management System
      </footer>

    </div>
  );
}
