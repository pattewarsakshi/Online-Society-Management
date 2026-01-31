// import { useEffect, useState } from "react";
// import { useAuth } from "../../contexts/AuthContext";
// import { useNavigate } from "react-router-dom";
// import DashboardLayout from "../../layouts/DashboardLayout";
// import Card from "../../Components/Card";
// import { FaExclamationTriangle, FaCreditCard, FaCalendar, FaBell } from "react-icons/fa";
// import api from "../../api/axios";
// import "./MemberDashboard.css";

// export default function MemberDashboard() {
//   const { user, loading } = useAuth();
//   const navigate = useNavigate();

//   const [openComplaints, setOpenComplaints] = useState(0);
//   const [activeNotices, setActiveNotices] = useState(0);
//   const [recentActivities, setRecentActivities] = useState([]);
//   const [upcomingEvents, setUpcomingEvents] = useState([]);

//   // Dummy maintenance data
//   const maintenanceData = {
//     nextDueDate: "2024-02-15",
//     amount: 2500,
//   };

//   // Dummy upcoming events
//   const dummyEvents = [
//     { id: 1, title: "UrbanNest Annual Meeting", date: "2024-02-20", time: "6:00 PM" },
//     { id: 2, title: "Festival Celebration", date: "2024-02-25", time: "7:00 PM" },
//     { id: 3, title: "Community Cleanup Drive", date: "2024-03-01", time: "9:00 AM" },
//   ];

//   // Redirect if user not logged in
//   useEffect(() => {
//     if (!loading && !user) {
//       navigate("/login", { replace: true });
//     }
//   }, [user, loading, navigate]);

//   // 🔹 Load OPEN complaints count (REAL DATA)
//   useEffect(() => {
//     if (!user) return;

//     const loadOpenComplaintCount = async () => {
//       try {
//         const res = await api.get("/complaints/my/pending/count");
//         setOpenComplaints(res.data.count || 0);
//       } catch (err) {
//         console.error("Failed to load open complaints count", err);
//       }
//     };

//     loadOpenComplaintCount();
//   }, [user]);

//   // 🔹 Load notices + activities (unchanged)
//   useEffect(() => {
//     if (!user) return;

//     const notices = JSON.parse(localStorage.getItem("notices") || "[]");

//     const activeNoticesCount = notices.filter(
//       (notice) => notice.status === "active" || !notice.status
//     ).length;
//     setActiveNotices(activeNoticesCount);

//     const activities = [];

//     notices.slice(0, 3).forEach((notice) => {
//       activities.push({
//         id: `notice-${notice.id}`,
//         text: `New Notice: ${notice.title || "UrbanNest Notice"}`,
//         time: notice.createdAt
//           ? new Date(notice.createdAt).toLocaleDateString()
//           : "Recently",
//         type: "notice",
//       });
//     });

//     setRecentActivities(activities);
//     setUpcomingEvents(dummyEvents);
//   }, [user]);

//   const formatDate = (dateString) => {
//     const date = new Date(dateString);
//     return date.toLocaleDateString("en-US", {
//       year: "numeric",
//       month: "long",
//       day: "numeric",
//     });
//   };

//   if (loading || !user) {
//     return (
//       <DashboardLayout>
//         <div className="loading-dashboard">
//           <p>Loading your dashboard...</p>
//         </div>
//       </DashboardLayout>
//     );
//   }

//   return (
//     <DashboardLayout>
//       <div className="member-dashboard">
//         {/* Welcome Section */}
//         <div className="welcome-section">
//           <h1 className="welcome-title">
//             Welcome back, {user.firstName || "Member"}!
//           </h1>
//           <p className="welcome-subtitle">
//             Here's what's happening in your UrbanNest community today.
//           </p>
//         </div>

//         {/* Stat Cards */}
//         <div className="stats-grid">
//           {/* 🔹 OPEN COMPLAINTS */}
//           <Card
//             className="stat-card complaints-card clickable"
//             onClick={() => navigate("/complaints?status=OPEN")}
//           >
//             <div className="stat-icon">
//               <FaExclamationTriangle />
//             </div>
//             <div className="stat-content">
//               <h3 className="stat-label">Open</h3>
//               <p className="stat-value">{openComplaints}</p>
//             </div>
//           </Card>

//           <Card className="stat-card maintenance-card">
//             <div className="stat-icon"><FaCreditCard /></div>
//             <div className="stat-content">
//               <h3 className="stat-label">Due Maintenance</h3>
//               <p className="stat-value">₹{maintenanceData.amount.toLocaleString()}</p>
//               <p className="stat-subtext">
//                 Due: {formatDate(maintenanceData.nextDueDate)}
//               </p>
//             </div>
//           </Card>

//           <Card className="stat-card events-card">
//             <div className="stat-icon"><FaCalendar /></div>
//             <div className="stat-content">
//               <h3 className="stat-label">Upcoming Events</h3>
//               <p className="stat-value">{upcomingEvents.length}</p>
//             </div>
//           </Card>

//           <Card className="stat-card notices-card">
//             <div className="stat-icon"><FaBell /></div>
//             <div className="stat-content">
//               <h3 className="stat-label">Active Notices</h3>
//               <p className="stat-value">{activeNotices}</p>
//             </div>
//           </Card>
//         </div>

//         {/* Main Content Row */}
//         <div className="content-row">
//           <Card className="activities-card">
//             <h2 className="section-title">Recent Activities</h2>
//             <div className="activities-list">
//               {recentActivities.length > 0 ? (
//                 recentActivities.map((activity) => (
//                   <div key={activity.id} className="activity-item">
//                     <div className="activity-dot"></div>
//                     <div className="activity-content">
//                       <p className="activity-text">{activity.text}</p>
//                       <span className="activity-time">{activity.time}</span>
//                     </div>
//                   </div>
//                 ))
//               ) : (
//                 <p className="empty-state">No recent activities</p>
//               )}
//             </div>
//           </Card>

//           <Card className="events-card-list">
//             <h2 className="section-title">Upcoming Events</h2>
//             <div className="events-list">
//               {upcomingEvents.map((event) => (
//                 <div key={event.id} className="event-item">
//                   <div className="event-date">
//                     <span className="event-day">
//                       {new Date(event.date).getDate()}
//                     </span>
//                     <span className="event-month">
//                       {new Date(event.date).toLocaleDateString("en-US", {
//                         month: "short",
//                       })}
//                     </span>
//                   </div>
//                   <div className="event-details">
//                     <h4 className="event-title">{event.title}</h4>
//                     <p className="event-time">{event.time}</p>
//                   </div>
//                 </div>
//               ))}
//             </div>
//           </Card>
//         </div>
//       </div>
//     </DashboardLayout>
//   );
// }


import { useEffect, useState } from "react";
import { useAuth } from "../../contexts/AuthContext";
import { useNavigate } from "react-router-dom";
import DashboardLayout from "../../layouts/DashboardLayout";
import Card from "../../Components/Card";
import { FaExclamationTriangle, FaCreditCard, FaCalendar, FaBell } from "react-icons/fa";
import api from "../../api/axios";
import "./MemberDashboard.css";

export default function MemberDashboard() {
  const { user, loading } = useAuth();
  const navigate = useNavigate();

  const [openComplaints, setOpenComplaints] = useState(0);
  const [activeNotices, setActiveNotices] = useState(0);
  const [recentActivities, setRecentActivities] = useState([]);
  const [upcomingEvents, setUpcomingEvents] = useState([]);
  const [pendingMaintenance, setPendingMaintenance] = useState(null); // NEW

  // Dummy upcoming events
  const dummyEvents = [
    { id: 1, title: "UrbanNest Annual Meeting", date: "2024-02-20", time: "6:00 PM" },
    { id: 2, title: "Festival Celebration", date: "2024-02-25", time: "7:00 PM" },
    { id: 3, title: "Community Cleanup Drive", date: "2024-03-01", time: "9:00 AM" },
  ];

  // Redirect if user not logged in
  useEffect(() => {
    if (!loading && !user) {
      navigate("/login", { replace: true });
    }
  }, [user, loading, navigate]);

  // 🔹 Load OPEN complaints count
  useEffect(() => {
    if (!user) return;

    const loadOpenComplaintCount = async () => {
      try {
        const res = await api.get("/complaints/my/pending/count");
        setOpenComplaints(res.data.count || 0);
      } catch (err) {
        console.error("Failed to load open complaints count", err);
      }
    };

    loadOpenComplaintCount();
  }, [user]);

  // 🔹 Load notices + activities
  useEffect(() => {
    if (!user) return;

    const notices = JSON.parse(localStorage.getItem("notices") || "[]");
    const activeNoticesCount = notices.filter(
      (notice) => notice.status === "active" || !notice.status
    ).length;
    setActiveNotices(activeNoticesCount);

    const activities = [];
    notices.slice(0, 3).forEach((notice) => {
      activities.push({
        id: `notice-${notice.id}`,
        text: `New Notice: ${notice.title || "UrbanNest Notice"}`,
        time: notice.createdAt
          ? new Date(notice.createdAt).toLocaleDateString()
          : "Recently",
        type: "notice",
      });
    });

    setRecentActivities(activities);
    setUpcomingEvents(dummyEvents);
  }, [user]);

  // 🔹 Load pending maintenance from backend
  useEffect(() => {
    if (!user?.flatId) return;

    const fetchPendingMaintenance = async () => {
      try {
        const res = await api.get(`/resident/maintenance/flat/${user.flatId}/pending`);
        setPendingMaintenance(res.data); // will be null if none pending
      } catch (err) {
        console.error("Failed to fetch pending maintenance", err);
      }
    };

    fetchPendingMaintenance();
  }, [user]);

  const formatDate = (dateString) => {
    if (!dateString) return "-";
    const date = new Date(dateString);
    return date.toLocaleDateString("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });
  };

  if (loading || !user) {
    return (
      <DashboardLayout>
        <div className="loading-dashboard">
          <p>Loading your dashboard...</p>
        </div>
      </DashboardLayout>
    );
  }

  return (
    <DashboardLayout>
      <div className="member-dashboard">
        {/* Welcome Section */}
        <div className="welcome-section">
          <h1 className="welcome-title">
            Welcome back, {user.firstName || "Member"}!
          </h1>
          <p className="welcome-subtitle">
            Here's what's happening in your UrbanNest community today.
          </p>
        </div>

        {/* Stat Cards */}
        <div className="stats-grid">
          {/* 🔹 OPEN COMPLAINTS */}
          <Card
            className="stat-card complaints-card clickable"
            onClick={() => navigate("/complaints?status=OPEN")}
          >
            <div className="stat-icon">
              <FaExclamationTriangle />
            </div>
            <div className="stat-content">
              <h3 className="stat-label">Open</h3>
              <p className="stat-value">{openComplaints}</p>
            </div>
          </Card>

          {/* 🔹 DUE MAINTENANCE */}
          <Card
            className="stat-card maintenance-card clickable"
            onClick={() => navigate("/maintenance")}
          >
            <div className="stat-icon"><FaCreditCard /></div>
            <div className="stat-content">
              <h3 className="stat-label">Due Maintenance</h3>
              <p className="stat-value">
                ₹{pendingMaintenance?.amount?.toLocaleString() || 0}
              </p>
              {pendingMaintenance && (
                <p className="stat-subtext">
                  Due: {formatDate(pendingMaintenance.dueDate)}
                </p>
              )}
            </div>
          </Card>

          {/* 🔹 UPCOMING EVENTS */}
          <Card className="stat-card events-card">
            <div className="stat-icon"><FaCalendar /></div>
            <div className="stat-content">
              <h3 className="stat-label">Upcoming Events</h3>
              <p className="stat-value">{upcomingEvents.length}</p>
            </div>
          </Card>

          {/* 🔹 ACTIVE NOTICES */}
          <Card className="stat-card notices-card">
            <div className="stat-icon"><FaBell /></div>
            <div className="stat-content">
              <h3 className="stat-label">Active Notices</h3>
              <p className="stat-value">{activeNotices}</p>
            </div>
          </Card>
        </div>

        {/* Main Content Row */}
        <div className="content-row">
          <Card className="activities-card">
            <h2 className="section-title">Recent Activities</h2>
            <div className="activities-list">
              {recentActivities.length > 0 ? (
                recentActivities.map((activity) => (
                  <div key={activity.id} className="activity-item">
                    <div className="activity-dot"></div>
                    <div className="activity-content">
                      <p className="activity-text">{activity.text}</p>
                      <span className="activity-time">{activity.time}</span>
                    </div>
                  </div>
                ))
              ) : (
                <p className="empty-state">No recent activities</p>
              )}
            </div>
          </Card>

          <Card className="events-card-list">
            <h2 className="section-title">Upcoming Events</h2>
            <div className="events-list">
              {upcomingEvents.map((event) => (
                <div key={event.id} className="event-item">
                  <div className="event-date">
                    <span className="event-day">
                      {new Date(event.date).getDate()}
                    </span>
                    <span className="event-month">
                      {new Date(event.date).toLocaleDateString("en-US", {
                        month: "short",
                      })}
                    </span>
                  </div>
                  <div className="event-details">
                    <h4 className="event-title">{event.title}</h4>
                    <p className="event-time">{event.time}</p>
                  </div>
                </div>
              ))}
            </div>
          </Card>
        </div>
      </div>
    </DashboardLayout>
  );
}
