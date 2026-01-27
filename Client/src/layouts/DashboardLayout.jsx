import { Link } from "react-router-dom";

export default function DashboardLayout({ children }) {
  return (
    <div style={{ display: "flex", minHeight: "100vh" }}>
      
      {/* SIDEBAR */}
      <aside
        style={{
          width: "220px",
          background: "#1e293b",
          color: "white",
          padding: "20px",
        }}
      >
        <h3 style={{ marginBottom: "30px" }}>Admin</h3>

        <p>
          <Link to="/admin" style={linkStyle}>Dashboard</Link>
        </p>

        <p>
          <Link to="/login" style={linkStyle}>Logout</Link>
        </p>
      </aside>

      {/* RIGHT SIDE */}
      <div style={{ flex: 1, display: "flex", flexDirection: "column" }}>
        
        {/* TOP BAR */}
        <header
          style={{
            height: "60px",
            background: "#ffffff",
            display: "flex",
            alignItems: "center",
            justifyContent: "space-between",
            padding: "0 20px",
            borderBottom: "1px solid #e5e7eb",
          }}
        >
          <h4>Society Management</h4>

          <div style={{ display: "flex", alignItems: "center", gap: "20px" }}>
            {/* Notification Bell */}
            <span style={{ fontSize: "20px", cursor: "pointer" }}>🔔</span>

            {/* Profile */}
            <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
              <span style={{ fontSize: "20px" }}>👤</span>
              <span>Admin</span>
            </div>
          </div>
        </header>

        {/* PAGE CONTENT */}
        <main style={{ padding: "20px", background: "#f4f6f8", flex: 1 }}>
          {children}
        </main>
      </div>
    </div>
  );
}

const linkStyle = {
  color: "white",
  textDecoration: "none",
};
