import { Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import SocietiesList from "./pages/super-admin/SocietiesList";
import ProtectedRoute from "./components/ProtectedRoute";
import CreateSociety from "./pages/super-admin/CreateSociety";
import CreateAdmin from "./pages/super-admin/CreateAdmin";

function App() {
  return (
    <Routes>
      {/* PUBLIC */}
      <Route path="/login" element={<Login />} />

      {/* SUPER ADMIN */}
      <Route
        path="/super-admin/societies"
        element={
          <ProtectedRoute role="SUPER_ADMIN">
            <SocietiesList />
          </ProtectedRoute>
        }
      />

      <Route
  path="/super-admin/societies/create"
  element={
    <ProtectedRoute role="SUPER_ADMIN">
      <CreateSociety />
    </ProtectedRoute>
  }
/>

<Route
  path="/super-admin/societies/:societyId/create-admin"
  element={
    <ProtectedRoute role="SUPER_ADMIN">
      <CreateAdmin />
    </ProtectedRoute>
  }
/>



      {/* DEFAULT */}
      <Route path="*" element={<Navigate to="/login" replace />} />
    </Routes>
  );
}

export default App;
