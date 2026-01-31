import { useAuth } from "../../contexts/AuthContext";
import { useEffect, useState } from "react";
import DashboardLayout from "../../layouts/DashboardLayout";
import UploadPaymentModal from "./UploadPaymentModal";
import "./Maintenance.css";

export default function ResidentMaintenance() {
  const { user } = useAuth(); // get current logged-in user
  const [maintenanceData, setMaintenanceData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedMaintenance, setSelectedMaintenance] = useState(null);

  useEffect(() => {
    // If user not logged in or no flatId, skip fetch
    if (!user?.flatId) {
      setLoading(false);
      return;
    }

    // Fetch maintenance for the user's flat
    fetch(`http://localhost:8080/api/resident/maintenance/flat/${user.flatId}`, {
      credentials: "include" // include cookies if needed
    })
      .then(res => res.json())
      .then(data => {
        setMaintenanceData(data);
        setLoading(false);
      })
      .catch(err => {
        console.error("Error fetching maintenance:", err);
        setLoading(false);
      });
  }, [user]);

  // Show message if user not logged in
  if (!user) {
    return (
      <DashboardLayout>
        <p>Please log in to view your maintenance records.</p>
      </DashboardLayout>
    );
  }

  // Show loading state
  if (loading) {
    return (
      <DashboardLayout>
        <p>Loading...</p>
      </DashboardLayout>
    );
  }

  return (
    <DashboardLayout>
      <div className="maintenance-container">
        <h2 className="maintenance-title">My Maintenance</h2>

        <table className="maintenance-table">
          <thead>
            <tr>
              <th>Month</th>
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
              <tr>
                <td colSpan="7" style={{ textAlign: "center" }}>
                  No maintenance records found
                </td>
              </tr>
            ) : (
              maintenanceData.map(item => (
                <tr key={item.maintenanceId}>
                  <td>{item.dueDate?.slice(0, 7)}</td>
                  <td>₹ {item.amount}</td>
                  <td>{item.dueDate}</td>

                  {/* STATUS BADGE */}
                  <td>
                    <span className={`status ${item.paymentStatus.toLowerCase()}`}>
                      {item.paymentStatus.replace("_", " ")}
                    </span>
                  </td>

                  {/* SCREENSHOT */}
                  <td>
                    {item.paymentProof ? (
                      <a
                        href={`http://localhost:8080/uploads/${item.paymentProof}`}
                        target="_blank"
                        rel="noreferrer"
                      >
                        <img
                          src={`http://localhost:8080/uploads/${item.paymentProof}`}
                          className="proof-thumb"
                          alt="proof"
                        />
                      </a>
                    ) : (
                      "-"
                    )}
                  </td>

                  {/* TXN */}
                  <td>{item.transactionId || "-"}</td>

                  {/* ACTION */}
                  <td>
                    <button
                      className={`btn success ${
                        item.paymentStatus !== "PENDING" ? "disabled" : ""
                      }`}
                      disabled={item.paymentStatus !== "PENDING"}
                      onClick={() => setSelectedMaintenance(item)}
                    >
                      Upload
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>

        </table>

        {selectedMaintenance && (
          <UploadPaymentModal
            maintenance={selectedMaintenance}
            onClose={() => setSelectedMaintenance(null)}
            onUpload={(updatedItem) => {
              setMaintenanceData(prev =>
                prev.map(m =>
                  m.maintenanceId === updatedItem.maintenanceId ? updatedItem : m
                )
              );
              setSelectedMaintenance(null);
            }}
          />
        )}
      </div>
    </DashboardLayout>
  );
}
