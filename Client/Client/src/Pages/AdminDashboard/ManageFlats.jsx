import { useEffect, useState } from "react";
import api from "../../api/axios";
import DashboardLayout from "../../layouts/DashboardLayout";
import "./ManageFlats.css";

export default function ManageFlats() {
  const [flats, setFlats] = useState([]);

  useEffect(() => {
    api
      .get("/api/admin/flats?societyId=1")
      .then((res) => setFlats(res.data));
  }, []);

  return (
    <DashboardLayout>
      <div className="manage-flats-container">
        <h1>All Flats</h1>

        <table className="flats-table">
          <thead>
            <tr>
              <th>Society</th>
              <th>Tower</th>
              <th>Flat No</th>
              <th>Area (sqft)</th>
              <th>Status</th>
            </tr>
          </thead>

          <tbody>
            {flats.map((flat) => (
              <tr key={flat.flatId}>
                <td>{flat.societyName}</td>
                <td>{flat.towerName}</td>
                <td>{flat.flatNumber}</td>
                <td>{flat.areaSqft}</td>
                <td>
                  <span className={`status ${flat.status.toLowerCase()}`}>
                    {flat.status}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </DashboardLayout>
  );
}
