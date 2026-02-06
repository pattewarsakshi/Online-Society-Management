import { Navigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

const ProtectedRoute = ({ children, role }) => {
  const { user } = useAuth();

  // not logged in
  if (!user) {
    return <Navigate to="/login" replace />;
  }

  // role mismatch
  if (role && user.role !== role) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default ProtectedRoute;
