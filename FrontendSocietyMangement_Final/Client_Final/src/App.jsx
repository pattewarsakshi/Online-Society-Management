import { BrowserRouter, Routes, Route } from "react-router-dom";

// AUTH PAGES
import Login from "./Pages/Login/Login";
import Register from "./Pages/Register/Register";
import ForgotPassword from "./Pages/ForgotPassword/ForgotPassword";
import Home from "./Pages/Home";
import AboutUs from "./Pages/AboutUs";
import TakeDemo from "./Pages/TakeDemo";

// ADMIN DASHBOARDS
import AdminDashboard from "./Pages/AdminDashboard/AdminDashboard";
import ApproveUsers from "./Pages/AdminDashboard/ApproveUsers";
import ManageFlats from "./Pages/AdminDashboard/ManageFlats";

// RESIDENT DASHBOARDS
import MemberDashboard from "./Pages/MemberDashboard/MemberDashboard";

// MODULE PAGES
import Notices from "./Pages/Notices/Notices";
import AddNotices from "./Pages/Notices/AddNotices";

import Complaints from "./Pages/Complaints/Complaints";
import AddComplaints from "./Pages/Complaints/AddComplaints";

import Maintenance from "./Pages/Maintenance/Maintenance";

import MembersDirectory from "./Pages/MembersDirectory/MembersDirectory";

// AMENITIES
import AdminAmenities from "./Pages/Amenities/AdminAmenities/AdminAmenities";
import AdminBookings from "./Pages/Amenities/AdminAmenities/AdminBookings"; // ✅ ADDED
import ResidentAmenities from "./Pages/Amenities/ResidentAmenities";
import MyBookings from "./Pages/Amenities/MyBookings";

import Profile from "./Pages/Profile/Profile";

import VisitorsList from "./Pages/Visitors/VisitorsList";
import AddVisitor from "./Pages/Visitors/AddVisitor";

import ParkingList from "./Pages/parking/ParkingList";

import DocumentsList from "./Pages/documents/DocumentsList";
import UploadDocument from "./Pages/documents/UploadDocument";

import Notifications from "./Pages/notifications/Notifications";

import ErrorPage from "./Pages/ErrorPage/ErrorPage";
import PrivateRoute from "./Components/PrivateRoute";


// ⭐ React Toastify
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* AUTH ROUTES */}
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/forgot" element={<ForgotPassword />} />
        <Route path="/about" element={<AboutUs />} />
        <Route path="/demo" element={<TakeDemo />} />

        {/* ADMIN ROUTES */}
        <Route
          path="/admin/dashboard"
          element={
            <PrivateRoute role="ADMIN">
              <AdminDashboard />
            </PrivateRoute>
          }
        />
        <Route path="/admin/approve-users" element={<ApproveUsers />} />
        <Route path="/admin/manage-flats" element={<ManageFlats />} />



        {/* MEMBER ROUTES */}
        <Route
          path="/member/dashboard"
          element={
            <PrivateRoute role="RESIDENT">
              <MemberDashboard />
            </PrivateRoute>
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

        {/* DIRECTORY */}
        <Route path="/directory" element={<MembersDirectory />} />

        {/* AMENITIES */}
        <Route
          path="/admin/amenities"
          element={
            <PrivateRoute role="ADMIN">
              <AdminAmenities />
            </PrivateRoute>
          }
        />
        <Route
          path="/admin/bookings"
          element={
            <PrivateRoute role="ADMIN">
              <AdminBookings />
            </PrivateRoute>
          }
        />
        <Route
          path="/amenities"
          element={
            <PrivateRoute role="RESIDENT">
              <ResidentAmenities />
            </PrivateRoute>
          }
        />
        <Route
          path="/amenities/my-bookings"
          element={
            <PrivateRoute role="RESIDENT">
              <MyBookings />
            </PrivateRoute>
          }
        />

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
