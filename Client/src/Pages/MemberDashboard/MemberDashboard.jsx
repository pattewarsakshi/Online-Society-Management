import { useEffect, useState } from "react";
import { useAuth } from "../../contexts/AuthContext";
import DashboardLayout from "../../layouts/DashboardLayout";
import Card from "../../Components/Card";
import { FaExclamationTriangle, FaCreditCard, FaCalendar, FaBell } from "react-icons/fa";
import "./MemberDashboard.css";

export default function MemberDashboard() {
  const { user } = useAuth();
  const [pendingComplaints, setPendingComplaints] = useState(0);
  const [activeNotices, setActiveNotices] = useState(0);
  const [recentActivities, setRecentActivities] = useState([]);
  const [upcomingEvents, setUpcomingEvents] = useState([]);

  // Dummy maintenance data
  const maintenanceData = {
    nextDueDate: "2024-02-15",
    amount: 2500,
  };

  // Dummy upcoming events
  const dummyEvents = [
    { id: 1, title: "UrbanNest Annual Meeting", date: "2024-02-20", time: "6:00 PM" },
    { id: 2, title: "Festival Celebration", date: "2024-02-25", time: "7:00 PM" },
    { id: 3, title: "Community Cleanup Drive", date: "2024-03-01", time: "9:00 AM" },
  ];

  useEffect(() => {
    // Load complaints from localStorage
    const complaints = JSON.parse(localStorage.getItem("complaints") || "[]");
    
    // Filter complaints by logged-in member's email
    const memberComplaints = complaints.filter(
      (complaint) => complaint.memberEmail === user?.email
    );
    
    // Count pending complaints
    const pendingCount = memberComplaints.filter(
      (complaint) => complaint.status === "pending"
    ).length;
    
    setPendingComplaints(pendingCount);

    // Load notices from localStorage
    const notices = JSON.parse(localStorage.getItem("notices") || "[]");
    const activeNoticesCount = notices.filter(
      (notice) => notice.status === "active" || !notice.status
    ).length;
    setActiveNotices(activeNoticesCount);

    // Generate recent activities from complaints and notices
    const activities = [];
    
    // Add recent complaints as activities
    memberComplaints.slice(0, 3).forEach((complaint) => {
      activities.push({
        id: `complaint-${complaint.id}`,
        text: `Complaint: ${complaint.subject || complaint.title || "New complaint"}`,
        time: complaint.createdAt 
          ? new Date(complaint.createdAt).toLocaleDateString()
          : "Recently",
        type: "complaint",
      });
    });

    // Add recent notices as activities
    notices.slice(0, 2).forEach((notice) => {
      activities.push({
        id: `notice-${notice.id}`,
        text: `New Notice: ${notice.title || notice.subject || "UrbanNest Notice"}`,
        time: notice.createdAt 
          ? new Date(notice.createdAt).toLocaleDateString()
          : "Recently",
        type: "notice",
      });
    });

    // Sort by time (most recent first) and limit to 5
    activities.sort((a, b) => {
      if (a.time === "Recently" && b.time !== "Recently") return -1;
      if (a.time !== "Recently" && b.time === "Recently") return 1;
      return 0;
    });

    setRecentActivities(activities.slice(0, 5));
    setUpcomingEvents(dummyEvents);
  }, [user]);

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });
  };

  return (
    <DashboardLayout>
      <div className="member-dashboard">
        {/* Welcome Section */}
        <div className="welcome-section">
          <h1 className="welcome-title">
            Welcome back, {user?.name || "Member"}!
          </h1>
          <p className="welcome-subtitle">
            Here's what's happening in your UrbanNest community today.
          </p>
        </div>

        {/* Stat Cards */}
        <div className="stats-grid">
          {/* Pending Complaints Card */}
          <Card className="stat-card complaints-card">
            <div className="stat-icon"><FaExclamationTriangle /></div>
            <div className="stat-content">
              <h3 className="stat-label">Pending Complaints</h3>
              <p className="stat-value">{pendingComplaints}</p>
            </div>
          </Card>

          {/* Due Maintenance Payment Card */}
          <Card className="stat-card maintenance-card">
            <div className="stat-icon"><FaCreditCard /></div>
            <div className="stat-content">
              <h3 className="stat-label">Due Maintenance</h3>
              <p className="stat-value">₹{maintenanceData.amount.toLocaleString()}</p>
              <p className="stat-subtext">Due: {formatDate(maintenanceData.nextDueDate)}</p>
            </div>
          </Card>

          {/* Upcoming Events Card */}
          <Card className="stat-card events-card">
            <div className="stat-icon"><FaCalendar /></div>
            <div className="stat-content">
              <h3 className="stat-label">Upcoming Events</h3>
              <p className="stat-value">{upcomingEvents.length}</p>
            </div>
          </Card>

          {/* Active Notices Card */}
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
          {/* Recent Activities */}
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

          {/* Upcoming Events */}
          <Card className="events-card-list">
            <h2 className="section-title">Upcoming Events</h2>
            <div className="events-list">
              {upcomingEvents.length > 0 ? (
                upcomingEvents.map((event) => (
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
                ))
              ) : (
                <p className="empty-state">No upcoming events</p>
              )}
            </div>
          </Card>
        </div>
      </div>
    </DashboardLayout>
  );
}
