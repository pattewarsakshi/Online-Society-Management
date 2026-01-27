import { Link, useLocation, useNavigate } from "react-router-dom";
import { logout } from "../api/logout";
import "./GuardLayout.css";

export default function GuardLayout({ children }) {
  const location = useLocation();
  const navigate = useNavigate();

  const isActive = (path) => location.pathname === path;

  return (
    <div className="guard-layout">
      <aside className="guard-sidebar">
        <div className="guard-sidebar-header">
          <h3>Guard Panel</h3>
          <p>Security Access</p>
        </div>

        <nav className="guard-nav">
          <Link to="/guard" className={isActive("/guard") ? "active" : ""}>
            Dashboard
          </Link>

          <Link
            to="/guard/add-visitor"
            className={isActive("/guard/add-visitor") ? "active" : ""}
          >
            Add Visitor
          </Link>

          <Link
            to="/guard/inside-visitors"
            className={isActive("/guard/inside-visitors") ? "active" : ""}
          >
            Inside Visitors
          </Link>

          <Link
            to="/guard/today-visitors"
            className={isActive("/guard/today-visitors") ? "active" : ""}
          >
            Today Visitors
          </Link>

          <div className="divider" />

          {/* 🔴 PROPER LOGOUT */}
          <button
            className="logout-btn"
            onClick={() => logout(navigate)}
          >
            Logout
          </button>
        </nav>
      </aside>

      <div className="guard-main">
        <header className="guard-topbar">
          <h4>Society Guard Dashboard</h4>
          <div className="topbar-right">
            <span className="bell">🔔</span>
            <span className="profile">👮 Guard</span>
          </div>
        </header>

        <main className="guard-content">{children}</main>
      </div>
    </div>
  );
}
