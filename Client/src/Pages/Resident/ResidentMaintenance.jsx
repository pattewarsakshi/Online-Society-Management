import { useEffect, useState } from "react";
import ResidentLayout from "../../layouts/ResidentLayout";
import api from "../../api/api";
import "./ResidentMaintenance.css";

export default function ResidentMaintenance() {
  const [records, setRecords] = useState([]);

  useEffect(() => {
    api.get("/api/resident/maintenance")
      .then(res => setRecords(res.data))
      .catch(() => alert("Failed to load maintenance"));
  }, []);

  return (
    <ResidentLayout>
      <div className="maintenance-card">
        <h2>Maintenance</h2>

        <table>
          <thead>
            <tr>
              <th>Month</th>
              <th>Amount</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {records.map(m => (
              <tr key={m.id}>
                <td>{m.month}</td>
                <td>₹{m.amount}</td>
                <td>
                  <span className={`status ${m.status.toLowerCase()}`}>
                    {m.status}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </ResidentLayout>
  );
}
