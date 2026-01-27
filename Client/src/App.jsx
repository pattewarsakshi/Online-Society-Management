import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

/* ================= PUBLIC ================= */
import Home from "./Pages/Home/Home";
import Login from "./Pages/Login/Login";
import RegisterSociety from "./Pages/Auth/RegisterSociety";
import RegisterResident from "./Pages/Resident/RegisterResident";

/* ================= ADMIN ================= */
import AdminDashboard from "./Pages/Admin/AdminDashboard";
import AdminNotices from "./Pages/Admin/Notices";

/* ================= GUARD ================= */
import GuardDashboard from "./Pages/Guard/GuardDashboard";
import AddVisitor from "./Pages/Guard/AddVisitor";
import InsideVisitors from "./Pages/Guard/InsideVisitors";
import TodayVisitors from "./Pages/Guard/TodayVisitors";

/* ================= RESIDENT ================= */
import ResidentDashboard from "./Pages/Resident/ResidentDashboard";
import ResidentBookFacility from "./Pages/Resident/Facility/ResidentBookFacility";
import ResidentMaintenance from "./Pages/Resident/ResidentMaintenance";
import ResidentProfile from "./Pages/Resident/ResidentProfile";

/* ================= SECURITY ================= */
import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* ========= PUBLIC ========= */}
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register-society" element={<RegisterSociety />} />
        <Route path="/resident/register" element={<RegisterResident />} />

        {/* ========= ADMIN ========= */}
        <Route
          path="/admin"
          element={
            <ProtectedRoute role="ADMIN">
              <AdminDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/notices"
          element={
            <ProtectedRoute role="ADMIN">
              <AdminNotices />
            </ProtectedRoute>
          }
        />

        {/* ========= GUARD ========= */}
        <Route
          path="/guard"
          element={
            <ProtectedRoute role="GUARD">
              <GuardDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/guard/add-visitor"
          element={
            <ProtectedRoute role="GUARD">
              <AddVisitor />
            </ProtectedRoute>
          }
        />

        <Route
          path="/guard/inside-visitors"
          element={
            <ProtectedRoute role="GUARD">
              <InsideVisitors />
            </ProtectedRoute>
          }
        />

        <Route
          path="/guard/today-visitors"
          element={
            <ProtectedRoute role="GUARD">
              <TodayVisitors />
            </ProtectedRoute>
          }
        />

        {/* ========= RESIDENT ========= */}
        <Route
          path="/resident"
          element={
            <ProtectedRoute role="RESIDENT">
              <ResidentDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/resident/book-facility"
          element={
            <ProtectedRoute role="RESIDENT">
              <ResidentBookFacility />
            </ProtectedRoute>
          }
        />

        <Route
          path="/resident/maintenance"
          element={
            <ProtectedRoute role="RESIDENT">
              <ResidentMaintenance />
            </ProtectedRoute>
          }
        />

        <Route
          path="/resident/profile"
          element={
            <ProtectedRoute role="RESIDENT">
              <ResidentProfile />
            </ProtectedRoute>
          }
        />

        {/* ========= FALLBACK ========= */}
        <Route path="*" element={<Navigate to="/" />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;
