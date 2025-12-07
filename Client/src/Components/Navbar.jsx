import { useEffect, useState } from "react";
import ProfileMenu from "./ProfileMenu";
import NotificationBell from "./NotificationBell";
import "./Navbar.css";

export default function Navbar() {
  const [role, setRole] = useState("");

  useEffect(() => {
    const savedRole = localStorage.getItem("role");
    setRole(savedRole);
  }, []);

  return (
    <div className="navbar">
      <div className="navbar-left">
        <h2 className="navbar-title">
          {role === "admin" ? "UrbanNest Admininster Panel" : "Member Panel"}
        </h2>
      </div>

      <div className="navbar-right">
        <NotificationBell />
        <ProfileMenu />
      </div>
    </div>
  );
}
