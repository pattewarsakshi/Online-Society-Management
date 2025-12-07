import DashboardLayout from "../../layouts/DashboardLayout";
import { useEffect, useState } from "react";
import "./Events.css";

export default function Events() {
  const [events, setEvents] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [statusFilter, setStatusFilter] = useState("All"); // Upcoming / Ongoing / Past / All

  useEffect(() => {
    const dummy = loadDummyEvents(true);
    setEvents(dummy);
  }, []);

  const updateStatus = (id, newStatus) => {
    const updated = events.map((e) =>
      e.id === id ? { ...e, status: newStatus } : e
    );
    setEvents(updated);
    localStorage.setItem("events", JSON.stringify(updated));
  };

  const filteredEvents = events.filter((e) => {
    const matchesStatus =
      statusFilter === "All" ? true : e.status === statusFilter;
    const matchesSearch =
      e.title.toLowerCase().includes(searchText.toLowerCase()) ||
      e.description.toLowerCase().includes(searchText.toLowerCase());
    return matchesStatus && matchesSearch;
  });

  return (
    <DashboardLayout>
      <div className="events-container">
        <h2 className="page-title">Society Events</h2>
        <p className="page-subtext">View and manage upcoming and past events</p>

        {/* Search & Filter */}
        <div className="filter-row">
          <input
            type="text"
            placeholder="Search events..."
            value={searchText}
            onChange={(e) => setSearchText(e.target.value)}
            className="search-input"
          />
          <select
            className="filter-select"
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
          >
            <option value="All">All</option>
            <option value="Upcoming">Upcoming</option>
            <option value="Ongoing">Ongoing</option>
            <option value="Past">Past</option>
          </select>
        </div>

        <div className="table-card">
          <table className="events-table">
            <thead>
              <tr>
                <th>Title</th>
                <th>Description</th>
                <th>Date</th>
                <th>Status</th>
                <th>Change Status</th>
              </tr>
            </thead>
            <tbody>
              {[...filteredEvents].reverse().map((e) => (
                <tr key={e.id}>
                  <td>{e.title}</td>
                  <td>{e.description}</td>
                  <td>{e.date}</td>
                  <td>
                    <span className={`status-tag ${e.status.toLowerCase()}`}>
                      {e.status}
                    </span>
                  </td>
                  <td>
                    <select
                      value={e.status}
                      onChange={(ev) => updateStatus(e.id, ev.target.value)}
                    >
                      <option>Upcoming</option>
                      <option>Ongoing</option>
                      <option>Past</option>
                    </select>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {filteredEvents.length === 0 && (
            <p className="no-data-msg">No events found.</p>
          )}
        </div>
      </div>
    </DashboardLayout>
  );
}

// Dummy Events Data
const loadDummyEvents = (reset = false) => {
  if (reset || !localStorage.getItem("events")) {
    const dummy = [
      { id: 1, title: "Annual Meeting", description: "Society annual general meeting", date: "2025-01-10", status: "Upcoming" },
      { id: 2, title: "Yoga Session", description: "Morning yoga for residents", date: "2025-01-05", status: "Past" },
      { id: 3, title: "Blood Donation Camp", description: "Blood donation drive in society", date: "2025-01-15", status: "Upcoming" },
      { id: 4, title: "Cultural Fest", description: "Cultural event for all residents", date: "2025-01-07", status: "Ongoing" },
      { id: 5, title: "Fire Drill", description: "Safety and fire drill training", date: "2025-01-08", status: "Upcoming" },
      { id: 6, title: "Community Cleaning", description: "Clean the society compound", date: "2025-01-03", status: "Past" },
      { id: 7, title: "Cooking Workshop", description: "Learn healthy recipes", date: "2025-01-12", status: "Upcoming" },
      { id: 8, title: "Gardening Class", description: "Gardening techniques for balcony plants", date: "2025-01-09", status: "Ongoing" },
    ];

    localStorage.setItem("events", JSON.stringify(dummy));
    return dummy;
  }

  return JSON.parse(localStorage.getItem("events"));
};
