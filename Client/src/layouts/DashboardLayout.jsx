import Sidebar from "../components/Sidebar";
import Navbar from "../Components/Navbar";
import "./DashboardLayout.css";

export default function DashboardLayout({ children }) {
  return (
    <div className="layout-container">

      {/* LEFT SIDE — SIDEBAR */}
      <Sidebar />

      {/* RIGHT SIDE — NAVBAR + PAGE CONTENT */}
      <div className="main-area">
        <Navbar />

        <div className="page-content">
          {children}
        </div>
      </div>
    </div>
  );
}
