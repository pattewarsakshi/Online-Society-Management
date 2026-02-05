import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import SuperAdminDashboard from "./pages/SuperAdminDashboard";
import AdminDashboard from "./pages/AdminDashboard";
import GuardDashboard from "./pages/GuardDashboard";
import TenantDashboard from "./pages/TenantDashboard";

import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Public */}
        <Route path="/" element={<Login />} />

        {/* SUPER ADMIN */}
        <Route
          path="/super-admin"
          element={
            <ProtectedRoute allowedRoles={["SUPER_ADMIN"]}>
              <SuperAdminDashboard />
            </ProtectedRoute>
          }
        />

        {/* ADMIN */}
        <Route
          path="/admin"
          element={
            <ProtectedRoute allowedRoles={["ADMIN", "SUPER_ADMIN"]}>
              <AdminDashboard />
            </ProtectedRoute>
          }
        />

        {/* GUARD */}
        <Route
          path="/guard"
          element={
            <ProtectedRoute allowedRoles={["GUARD"]}>
              <GuardDashboard />
            </ProtectedRoute>
          }
        />

        {/* TENANT */}
        <Route
          path="/tenant"
          element={
            <ProtectedRoute allowedRoles={["TENANT"]}>
              <TenantDashboard />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
