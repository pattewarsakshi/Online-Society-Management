import { useEffect, useState } from "react";
import GuardLayout from "../../layouts/GuardLayout";
import api from "../../api/api.js";
import "./Guard.css";

export default function TodayVisitors() {
  const [visitors, setVisitors] = useState([]);

  const loadTodayVisitors = async () => {
    try {
      const res = await api.get("/api/visitors/society/1/today");
      setVisitors(res.data);
    } catch (err) {
      alert("Failed to load today's visitors");
    }
  };

  useEffect(() => {
    loadTodayVisitors();
  }, []);

  return (
    <GuardLayout>
      <div className="guard-card wide">
        <h2>Today Visitors</h2>
        <p className="sub-text">All visitors who entered today</p>

        {visitors.length === 0 ? (
          <p className="empty-text">No visitors today</p>
        ) : (
          <table className="visitor-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Purpose</th>
                <th>Flat</th>
                <th>Entry Time</th>
                <th>Exit Time</th>
                <th>Status</th>
              </tr>
            </thead>

            <tbody>
              {visitors.map((v) => (
                <tr key={v.visitorId}>
                  <td>{v.visitorName}</td>
                  <td>{v.purpose}</td>
                  <td>A-{v.flatId}</td>
                  <td>{new Date(v.entryTime).toLocaleString()}</td>
                  <td>
                    {v.exitTime
                      ? new Date(v.exitTime).toLocaleString()
                      : "-"}
                  </td>
                  <td>
                    {v.exitTime ? (
                      <span className="badge exited">EXITED</span>
                    ) : (
                      <span className="badge inside">INSIDE</span>
                    )}
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
