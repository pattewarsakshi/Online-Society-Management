import DashboardLayout from "../../layouts/DashboardLayout";
import { useEffect, useState } from "react";
import "./AddNotices.css";
import { toast } from "react-toastify";

export default function AddNotices() {
  const [notices, setNotices] = useState([]);
  const [newNotice, setNewNotice] = useState({
    title: "",
    date: "",
  });

  useEffect(() => {
    loadDummyNotices();
    setNotices(JSON.parse(localStorage.getItem("notices") || "[]"));
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewNotice((prev) => ({ ...prev, [name]: value }));
  };

  const handleAddNotice = (e) => {
    e.preventDefault();

    if (!newNotice.title || !newNotice.date) {
      toast.error("Please fill in both title and date");
      return;
    }

    const updated = [
      ...notices,
      { id: Date.now(), title: newNotice.title, date: newNotice.date },
    ];

    setNotices(updated);
    localStorage.setItem("notices", JSON.stringify(updated));
    toast.success("Notice added successfully!");

    setNewNotice({ title: "", date: "" }); // Reset form
  };

  return (
    <DashboardLayout>
      <div className="notices-container">
        <h2 className="page-title">Notices</h2>
        <p className="page-subtext">View and add new notices</p>

        {/* Add Notice Form */}
        <div className="notice-form-card">
          <form onSubmit={handleAddNotice} className="notice-form">
            <input
              type="text"
              name="title"
              value={newNotice.title}
              onChange={handleChange}
              placeholder="Notice Title"
            />
            <input
              type="date"
              name="date"
              value={newNotice.date}
              onChange={handleChange}
            />
            <button type="submit">Add Notice</button>
          </form>
        </div>

        {/* Notices Table */}
        <div className="table-card">
          <table className="notices-table">
            <thead>
              <tr>
                <th>Title</th>
                <th>Date</th>
              </tr>
            </thead>
            <tbody>
              {[...notices].reverse().map((n) => (
                <tr key={n.id}>
                  <td>{n.title}</td>
                  <td>{n.date}</td>
                </tr>
              ))}
            </tbody>
          </table>
          {notices.length === 0 && (
            <p className="no-data-msg">No notices found.</p>
          )}
        </div>
      </div>
    </DashboardLayout>
  );
}

// Dummy data loader
const loadDummyNotices = () => {
  if (!localStorage.getItem("notices")) {
    const dummy = [
      { id: 1, title: "Community Hall Booking Closed", date: "2025-01-05" },
      { id: 2, title: "Electricity maintenance on Sunday", date: "2025-01-06" },
      { id: 3, title: "Annual General Meeting", date: "2025-01-12" },
    ];
    localStorage.setItem("notices", JSON.stringify(dummy));
  }
};
