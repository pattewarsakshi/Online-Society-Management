import DashboardLayout from "../../layouts/DashboardLayout";
import { useEffect, useState } from "react";
import "./Amenities.css";

export default function Amenities() {
  const [amenities, setAmenities] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [statusFilter, setStatusFilter] = useState("All");

  useEffect(() => {
    const dummy = loadDummyAmenities(true);
    setAmenities(dummy);
  }, []);

  const updateStatus = (id, newStatus) => {
    const updated = amenities.map((a) =>
      a.id === id ? { ...a, status: newStatus } : a
    );
    setAmenities(updated);
    localStorage.setItem("amenities", JSON.stringify(updated));
  };

  const filteredAmenities = amenities.filter((a) => {
    const matchesStatus =
      statusFilter === "All" ? true : a.status === statusFilter;
    const matchesSearch = a.name.toLowerCase().includes(searchText.toLowerCase());
    return matchesStatus && matchesSearch;
  });

  return (
    <DashboardLayout>
      <div className="amenities-container">
        <h2 className="page-title">Amenities Management</h2>
        <p className="page-subtext">View and update society amenities</p>

        {/* Filter & Search */}
        <div className="filter-row">
          <input
            type="text"
            placeholder="Search amenities..."
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
            <option value="Available">Available</option>
            <option value="Booked">Booked</option>
            <option value="Under Maintenance">Under Maintenance</option>
          </select>
        </div>

        <div className="table-card">
          <table className="amenities-table">
            <thead>
              <tr>
                <th>Amenity</th>
                <th>Status</th>
                <th>Last Updated</th>
                <th>Change Status</th>
              </tr>
            </thead>
            <tbody>
              {[...filteredAmenities].map((a) => (
                <tr key={a.id}>
                  <td>{a.name}</td>
                  <td>
                    <span className={`status-tag ${a.status.replace(" ", "").toLowerCase()}`}>
                      {a.status}
                    </span>
                  </td>
                  <td>{a.updatedAt}</td>
                  <td>
                    <select
                      value={a.status}
                      onChange={(e) => updateStatus(a.id, e.target.value)}
                    >
                      <option>Available</option>
                      <option>Booked</option>
                      <option>Under Maintenance</option>
                    </select>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {filteredAmenities.length === 0 && (
            <p className="no-data-msg">No amenities found.</p>
          )}
        </div>
      </div>
    </DashboardLayout>
  );
}

// Dummy Data
const loadDummyAmenities = (reset = false) => {
  if (reset || !localStorage.getItem("amenities")) {
    const dummy = [
      { id: 1, name: "Community Hall", status: "Available", updatedAt: "2025-01-05" },
      { id: 2, name: "Swimming Pool", status: "Under Maintenance", updatedAt: "2025-01-06" },
      { id: 3, name: "Gym", status: "Booked", updatedAt: "2025-01-07" },
      { id: 4, name: "Tennis Court", status: "Available", updatedAt: "2025-01-03" },
      { id: 5, name: "Library", status: "Available", updatedAt: "2025-01-02" },
      { id: 6, name: "Children Play Area", status: "Booked", updatedAt: "2025-01-04" },
      { id: 7, name: "Garden Area", status: "Available", updatedAt: "2025-01-01" },
      { id: 8, name: "Parking Lot", status: "Booked", updatedAt: "2025-01-05" },
      { id: 9, name: "Basketball Court", status: "Under Maintenance", updatedAt: "2025-01-06" },
      { id: 10, name: "Badminton Court", status: "Available", updatedAt: "2025-01-07" },
    ]
     localStorage.setItem("amenities", JSON.stringify(dummy));
    return dummy;
  }
  return JSON.parse(localStorage.getItem("amenities"));
};
