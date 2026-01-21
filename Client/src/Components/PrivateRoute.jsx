import { Navigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

function PrivateRoute({ children, role }) {
  const { user, loading } = useAuth();

  // Show nothing while loading
  if (loading) {
    return null;
  }

  // If not authenticated, redirect to login
  if (!user) {
    return <Navigate to="/" replace />;
  }

  // If role is specified and user role doesn't match, redirect to appropriate dashboard
  if (role && user.role !== role) {
    if (user.role === "admin") {
      return <Navigate to="/admin/dashboard" replace />;
    } else if (user.role === "member") {
      return <Navigate to="/member/dashboard" replace />;
    }
    return <Navigate to="/" replace />;
  }

  // If member is not approved, redirect to login
  if (user.role === "member" && user.status !== "approved") {
    return <Navigate to="/" replace />;
  }

  return children;
}

export default PrivateRoute;

