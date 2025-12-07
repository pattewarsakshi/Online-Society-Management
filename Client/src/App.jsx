import { BrowserRouter, Routes, Route } from "react-router-dom";

// AUTH PAGES
import Login from "./Pages/Login/Login";
import Register from "./Pages/Register/Register";
import ForgotPassword from "./Pages/ForgotPassword/ForgotPassword";

// DASHBOARDS
import AdminDashboard from "./Pages/AdminDashboard/AdminDashboard";
import MemberDashboard from "./Pages/MemberDashboard/MemberDashboard";

// MODULE PAGES
import Notices from "./Pages/Notices/Notices";
import AddNotices from "./Pages/Notices/AddNotices";

import Complaints from "./Pages/Complaints/Complaints";
import AddComplaints from "./Pages/Complaints/AddComplaints";

import Maintenance from "./Pages/Maintenance/Maintenance";
import PayMaintenance from "./Pages/Maintenance/PayMaintenance";

import MembersDirectory from "./Pages/MembersDirectory/MembersDirectory";

import Amenities from "./Pages/Amenities/Amenities";
import BookAmenity from "./Pages/Amenities/BookAmenity";

import Events from "./Pages/Events/Events";
import Profile from "./Pages/Profile/Profile";

import VisitorsList from "./Pages/Visitors/VisitorsList";
import AddVisitor from "./Pages/Visitors/AddVisitor";

import ParkingList from "./Pages/parking/ParkingList";

import DocumentsList from "./Pages/documents/DocumentsList";
import UploadDocument from "./Pages/documents/UploadDocument"; 
// 👆 CORRECTED — before you imported DocumentsList again by mistake

import Notifications from "./Pages/notifications/Notifications";

import ErrorPage from "./Pages/ErrorPage/ErrorPage";
import ProtectedRoute from "./Components/ProtectedRoute";

// ⭐ React Toastify
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* AUTH ROUTES */}
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/forgot" element={<ForgotPassword />} />

        {/* ADMIN ROUTES */}
        <Route
          path="/admin/dashboard"
          element={
            <ProtectedRoute role="admin">
              <AdminDashboard />
            </ProtectedRoute>
          }
        />

        {/* MEMBER ROUTES */}
        <Route
          path="/member/dashboard"
          element={
            <ProtectedRoute role="member">
              <MemberDashboard />
            </ProtectedRoute>
          }
        />

        {/* NOTICES */}
        <Route path="/notices" element={<Notices />} />
        <Route path="/notices/add" element={<AddNotices />} />

        {/* COMPLAINTS */}
        <Route path="/complaints" element={<Complaints />} />
        <Route path="/complaints/add" element={<AddComplaints />} />

        {/* MAINTENANCE */}
        <Route path="/maintenance" element={<Maintenance />} />
        <Route path="/maintenance/pay" element={<PayMaintenance />} />

        {/* DIRECTORY */}
        <Route path="/directory" element={<MembersDirectory />} />

        {/* AMENITIES */}
        <Route path="/amenities" element={<Amenities />} />
        <Route path="/amenities/book" element={<BookAmenity />} />

        {/* EVENTS */}
        <Route path="/events" element={<Events />} />

        {/* PROFILE */}
        <Route path="/profile" element={<Profile />} />

        {/* VISITORS */}
        <Route path="/visitors" element={<VisitorsList />} />
        <Route path="/visitors/add" element={<AddVisitor />} />

        {/* PARKING */}
        <Route path="/parking" element={<ParkingList />} />

        {/* DOCUMENTS */}
        <Route path="/documents" element={<DocumentsList />} />
        <Route path="/documents/upload" element={<UploadDocument />} />

        {/* NOTIFICATIONS */}
        <Route path="/notifications" element={<Notifications />} />

        {/* ERROR PAGE */}
        <Route path="*" element={<ErrorPage />} />

      </Routes>

      
      <ToastContainer position="top-center" autoClose={1800} pauseOnHover={false} />

    </BrowserRouter>
  );
}

export default App;
