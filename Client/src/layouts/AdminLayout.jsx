import { Link, useLocation, useNavigate } from "react-router-dom";
import api from "../api/api";
import "./AdminLayout.css";

export default function AdminLayout({ children }) {
  const location = useLocation();
  const navigate = useNavigate();

  const isActive = (path) => location.pathname.startsWith(path);

  const handleLogout = async () => {
    try {
      await api.post("/api/auth/logout"); // 🔥 invalidate session
    } catch (err) {
      console.warn("Logout failed");
    } finally {
      navigate("/login", { replace: true });
    }
  };

  return (
    <div className="admin-layout">
      <aside className="admin-sidebar">
        <h2 className="logo">Admin</h2>

        <nav>
          <Link to="/admin" className={isActive("/admin") ? "active" : ""}>
            Dashboard
          </Link>

          <Link
            to="/admin/notices"
            className={isActive("/admin/notices") ? "active" : ""}
          >
            Notices
          </Link>

          <Link
            to="/admin/facilities"
            className={isActive("/admin/facilities") ? "active" : ""}
          >
            Facilities
          </Link>

          {/* ✅ REAL LOGOUT */}
          <button className="logout" onClick={handleLogout}>
            Logout
          </button>
        </nav>
      </aside>

      <div className="admin-main">
        <header className="admin-header">
          Society Management
        </header>

        <main className="admin-content">
          {children}
        </main>
      </div>
    </div>
  );
}
