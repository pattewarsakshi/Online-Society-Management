import { useEffect, useState } from "react";
import GuardLayout from "../../layouts/GuardLayout";
import api from "../../api/api.js";
import "./Guard.css";

export default function InsideVisitors() {
  const [visitors, setVisitors] = useState([]);

  const loadVisitors = async () => {
    try {
      const res = await api.get("/api/visitors/society/1/inside");
      setVisitors(res.data);
    } catch (err) {
      alert("Failed to load visitors");
    }
  };

  useEffect(() => {
    loadVisitors();
  }, []);

  const exitVisitor = async (visitorId) => {
    try {
      await api.put(`/api/visitors/society/1/exit/${visitorId}`);
      loadVisitors();
    } catch {
      alert("Exit failed");
    }
  };

  return (
    <GuardLayout>
      <div className="guard-card wide">
        <h2>Inside Visitors</h2>
        <p className="sub-text">Visitors currently inside the society</p>

        {visitors.length === 0 ? (
          <p className="empty-text">No visitors inside</p>
        ) : (
          <table className="visitor-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Purpose</th>
                <th>Flat</th>
                <th>Entry Time</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {visitors.map((v) => (
                <tr key={v.visitorId}>
                  <td>{v.visitorName}</td>
                  <td>{v.purpose}</td>
                  <td>A-{v.flatId}</td>
                  <td>
                    {new Date(v.entryTime).toLocaleString()}
                  </td>
                  <td>
                    <span className="badge inside">INSIDE</span>
                  </td>
                  <td>
                    <button
                      className="exit-btn"
                      onClick={() => exitVisitor(v.visitorId)}
                    >
                      Exit
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </GuardLayout>
  );
}
