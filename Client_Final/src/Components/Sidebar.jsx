import { Link, useLocation } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import { 
  FaThLarge, 
  FaBell, 
  FaExclamationCircle, 
  FaCreditCard, 
  FaUsers, 
  FaUser, 
  FaSignOutAlt,
  FaFileAlt,
  FaCar,
  FaSwimmingPool,
  FaCalendarAlt
} from "react-icons/fa";
import "./Sidebar.css";

export default function Sidebar({ isOpen = true }) {
  const { user } = useAuth();
  const location = useLocation();

  if (!user) return null;

  // Icon mapping function
  const getIcon = (iconName) => {
    const iconMap = {
      dashboard: <FaThLarge />,
      notices: <FaBell />,
      complaints: <FaExclamationCircle />,
      maintenance: <FaCreditCard />,
      directory: <FaUsers />,
      profile: <FaUser />,
      logout: <FaSignOutAlt />,
      visitors: <FaUsers />,
      parking: <FaCar />,
      documents: <FaFileAlt />,
      notifications: <FaBell />,
      amenities: <FaSwimmingPool />,
      events: <FaCalendarAlt />
    };
    return iconMap[iconName] || null;
  };

  // Menu items for ADMIN
  const adminMenu = [
    { path: "/admin/dashboard", label: "Dashboard", iconName: "dashboard" },
    { path: "/notices", label: "Notices", iconName: "notices" },
    { path: "/complaints", label: "Complaints", iconName: "complaints" },
    { path: "/maintenance", label: "Maintenance", iconName: "maintenance" },
    { path: "/visitors", label: "Visitors", iconName: "visitors" },
    { path: "/parking", label: "Parking", iconName: "parking" },
    { path: "/documents", label: "Documents", iconName: "documents" },
    { path: "/notifications", label: "Notifications", iconName: "notifications" },
    { path: "/profile", label: "Profile", iconName: "profile" },
  ];

  // Menu items for MEMBER
  const memberMenu = [
    { path: "/member/dashboard", label: "Dashboard", iconName: "dashboard" },
    { path: "/directory", label: "Member Directory", iconName: "directory" },
    { path: "/notices", label: "Notices", iconName: "notices" },
    { path: "/complaints", label: "Complaints", iconName: "complaints" },
    { path: "/maintenance", label: "Maintenance", iconName: "maintenance" },
    { path: "/amenities", label: "Amenities", iconName: "amenities" },
    { path: "/events", label: "Events", iconName: "events" },
    { path: "/profile", label: "Profile", iconName: "profile" },
  ];

  const menuItems = user?.role === "admin" ? adminMenu : memberMenu;

  return (
    <aside className={`sidebar ${isOpen ? "open" : "closed"}`}>
      <div className="sidebar-content">
        <h2 className="sidebar-title">UrbanNest</h2>

        <ul className="sidebar-menu">
          {menuItems.map((item) => {
            const isActive = location.pathname === item.path;
            return (
              <li key={item.path} className={isActive ? "active" : ""}>
                <Link to={item.path} className="sidebar-link">
                  {item.iconName && <span className="sidebar-icon">{getIcon(item.iconName)}</span>}
                  {isOpen && <span className="sidebar-label">{item.label}</span>}
                </Link>
              </li>
            );
          })}
        </ul>
      </div>
    </aside>
  );
}
