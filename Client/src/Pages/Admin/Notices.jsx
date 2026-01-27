import { useEffect, useState } from "react";
import AdminLayout from "../../layouts/AdminLayout";
import api from "../../api/api";
import "./Notices.css";

export default function Notices() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [notices, setNotices] = useState([]);

  const loadNotices = async () => {
    const res = await api.get("/api/notices");
    setNotices(res.data);
  };

  useEffect(() => {
    loadNotices();
  }, []);

  const createNotice = async (e) => {
    e.preventDefault();
    await api.post("/api/notices", { title, description });
    setTitle("");
    setDescription("");
    loadNotices();
  };

  return (
    <AdminLayout>
      <h2>Notices</h2>

      <form className="notice-form" onSubmit={createNotice}>
        <input
          placeholder="Notice title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />

        <textarea
          placeholder="Notice description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
        />

        <button type="submit">Post Notice</button>
      </form>

      <div className="notice-list">
        {notices.map((n) => (
          <div key={n.noticeId} className="notice-card">
            <h4>{n.title}</h4>
            <p>{n.description}</p>
          </div>
        ))}
      </div>
    </AdminLayout>
  );
}
