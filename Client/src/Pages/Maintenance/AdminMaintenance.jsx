import { useEffect, useState } from "react";
import DashboardLayout from "../../layouts/DashboardLayout";
import "./Maintenance.css";

export default function AdminMaintenance() {
  const [maintenanceData, setMaintenanceData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch("http://localhost:8080/api/admin/maintenance")
      .then(res => res.json())
      .then(data => {
        setMaintenanceData(data);
        setLoading(false);
      })
      .catch(err => {
        console.error("Error fetching admin maintenance:", err);
        setLoading(false);
      });
  }, []);

  if (loading) return <DashboardLayout><p>Loading...</p></DashboardLayout>;

  const handleApprove = (id) => {
    fetch(`http://localhost:8080/api/admin/maintenance/${id}/approve`, {
      method: "PUT"
    })
      .then(res => res.json())
      .then(updated => {
        setMaintenanceData(prev =>
          prev.map(m => m.maintenanceId === id ? updated : m)
        );
      });
  };

  const handleReject = (id) => {
    fetch(`http://localhost:8080/api/admin/maintenance/${id}/reject`, {
      method: "PUT"
    })
      .then(res => res.json())
      .then(updated => {
        setMaintenanceData(prev =>
          prev.map(m => m.maintenanceId === id ? updated : m)
        );
      });
  };

  return (
    <DashboardLayout>
      <div className="maintenance-container">
        <h2 className="maintenance-title">Maintenance Management</h2>

        <table className="maintenance-table">
          <thead>
            <tr>
              <th>Flat ID</th>
              <th>Amount</th>
              <th>Due Date</th>
              <th>Payment Status</th>
              <th>Transaction ID</th>
              <th>Payment Proof</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>
            {maintenanceData.length === 0 ? (
              <tr><td colSpan={7}>No records found.</td></tr>
            ) : (
              maintenanceData.map(item => (
                <tr key={item.maintenanceId}>
                  <td>{item.flatId}</td>
                  <td>{item.amount}</td>
                  <td>{item.dueDate}</td>
                  <td>{item.paymentStatus}</td>
                  <td>{item.transactionId || "-"}</td>
                  <td>
                    {item.paymentProof ? (
                      <a href={`http://localhost:8080/uploads/${item.paymentProof}`} target="_blank" rel="noopener noreferrer">
                        View
                      </a>
                    ) : "-"}
                  </td>
                  <td>
                    {item.paymentStatus === "APPROVAL_PENDING" && (
                      <>
                        <button onClick={() => handleApprove(item.maintenanceId)}>Approve</button>
                        <button onClick={() => handleReject(item.maintenanceId)} style={{ marginLeft: "5px" }}>Reject</button>
                      </>
                    )}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </DashboardLayout>
  );
}
