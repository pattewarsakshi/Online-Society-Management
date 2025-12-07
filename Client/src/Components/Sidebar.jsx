import { Link, useLocation, useNavigate } from "react-router-dom";
import "./Sidebar.css";

export default function Sidebar() {
  const role = localStorage.getItem("role"); // admin or member
  const location = useLocation();
  const navigate = useNavigate();

  // Logout function
  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  // Menu items for ADMIN
  const adminMenu = [
    { path: "/admin/dashboard", label: "Dashboard" },
    { path: "/notices", label: "Notices" },
    { path: "/complaints", label: "Complaints" },
    { path: "/maintenance", label: "Maintenance" },
    { path: "/visitors", label: "Visitors" },
    { path: "/parking", label: "Parking" },
    { path: "/documents", label: "Documents" },
    { path: "/notifications", label: "Notifications" },
    { path: "/profile", label: "Profile" },
  ];

  // Menu items for MEMBER
  const memberMenu = [
    { path: "/member/dashboard", label: "Dashboard" },
    { path: "/notices", label: "Notices" },
    { path: "/complaints", label: "Complaints" },
    { path: "/maintenance", label: "Maintenance" },
    { path: "/amenities", label: "Amenities" },
    { path: "/events", label: "Events" },
    { path: "/profile", label: "Profile" },
  ];

  // Select which menu to use
  const menuItems = role === "admin" ? adminMenu : memberMenu;

  return (
    <div className="sidebar">
      <h2 className="sidebar-title">Society</h2>

      <ul className="sidebar-menu">
        {menuItems.map((item) => (
          <li
            key={item.path}
            className={location.pathname === item.path ? "active" : ""}
          >
            <Link to={item.path}>{item.label}</Link>
          </li>
        ))}
      </ul>

      {/* LOGOUT BUTTON */}
      <div className="sidebar-logout" onClick={handleLogout}>
        Logout
      </div>
    </div>
  );
}
