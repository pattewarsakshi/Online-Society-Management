import DashboardLayout from "../../layouts/DashboardLayout";
import { useEffect, useState } from "react";
//import "";

export default function Notifications() {
  const [notifications, setNotifications] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [filter, setFilter] = useState("All"); // All / Seen / Unseen

  useEffect(() => {
    const dummy = loadDummyNotifications();
    setNotifications(dummy);
  }, []);

  const toggleSeen = (id) => {
    const updated = notifications.map((n) =>
      n.id === id ? { ...n, seen: !n.seen } : n
    );
    setNotifications(updated);
    localStorage.setItem("notifications", JSON.stringify(updated));
  };

  const filteredNotifications = notifications.filter((n) => {
    const matchesSearch = n.msg.toLowerCase().includes(searchText.toLowerCase());
    const matchesFilter =
      filter === "All"
        ? true
        : filter === "Seen"
        ? n.seen
        : !n.seen;

    return matchesSearch && matchesFilter;
  });

  return (
    <DashboardLayout>
      <div className="notifications-container">
        <h2 className="page-title">
          Notications
          <span className="badge-count">({notifications.length})</span>
        </h2>
        <p className="page-subtext">Manage system notifications</p>

        {/* Search & Filter */}
        <div className="filter-row">
          <input
            type="text"
            placeholder="Search notifications..."
            value={searchText}
            onChange={(e) => setSearchText(e.target.value)}
            className="search-input"
          />

          <select
            className="filter-select"
            value={filter}
            onChange={(e) => setFilter(e.target.value)}
          >
            <option value="All">All</option>
            <option value="Seen">Seen</option>
            <option value="Unseen">Unseen</option>
          </select>
        </div>

        <div className="table-card">
          <table className="notifications-table">
            <thead>
              <tr>
                <th>Message</th>
                <th>Status</th>
                <th>Mark as Seen/Unseen</th>
              </tr>
            </thead>

            <tbody>
              {[...filteredNotifications].reverse().map((n) => (
                <tr key={n.id}>
                  <td>{n.msg}</td>
                  <td>
                    <span className={`status-tag ${n.seen ? "seen" : "unseen"}`}>
                      {n.seen ? "Seen" : "Unseen"}
                    </span>
                  </td>
                  <td>
                    <button
                      className="toggle-btn"
                      onClick={() => toggleSeen(n.id)}
                    >
                      Mark {n.seen ? "Unseen" : "Seen"}
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {filteredNotifications.length === 0 && (
            <p className="no-data-msg">No notifications found.</p>
          )}
        </div>
      </div>
    </DashboardLayout>
  );
}

// Dummy Notifications
const loadDummyNotifications = () => {
  if (!localStorage.getItem("notifications")) {
    const dummy = [
      { id: 1, msg: "Meeting on Saturday", seen: false },
      { id: 2, msg: "Maintenance reminder", seen: true },
      { id: 3, msg: "New visitor registered", seen: false },
      { id: 4, msg: "Payment confirmation", seen: true },
      { id: 5, msg: "Emergency contact update", seen: false },
      { id: 6, msg: "Event reminder: Cultural Fest", seen: true },
      { id: 7, msg: "Fire drill scheduled", seen: false },
      { id: 8, msg: "Security alert: Gate access", seen: false },
    ];
    localStorage.setItem("notifications", JSON.stringify(dummy));
    return dummy;
  }
  return JSON.parse(localStorage.getItem("notifications"));
};

