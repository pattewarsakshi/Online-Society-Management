import { Navigate } from "react-router-dom";

export default function ProtectedRoute({ allowedRoles, children }) {
  const token = localStorage.getItem("token");
  let role = localStorage.getItem("role");

  // 🔥 normalize role if backend ever sends ROLE_*
  if (role && role.startsWith("ROLE_")) {
    role = role.replace("ROLE_", "");
  }

  // not logged in
  if (!token) {
    return <Navigate to="/" replace />;
  }

  // role not allowed
  if (!role || !allowedRoles.includes(role)) {
    return <Navigate to="/" replace />;
  }

  return children;
}
