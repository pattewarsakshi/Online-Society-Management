import { useEffect, useState } from "react";
import DashboardLayout from "../../layouts/DashboardLayout";
import "./Maintenance.css";

export default function AdminMaintenance() {
  const [maintenanceData, setMaintenanceData] = useState([]);
  const [loading, setLoading] = useState(true);

  // FORM STATE
  const [towerName, setTowerName] = useState("");
  const [flatNumber, setFlatNumber] = useState("");
  const [amount, setAmount] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [overDueDate, setOverDueDate] = useState("");

  // EDIT STATE
  const [editingId, setEditingId] = useState(null);

  // ===== POPUP STATE =====
  const [popup, setPopup] = useState({ message: "", type: "info", show: false });

  const showPopup = (message, type = "info") => {
    setPopup({ message, type, show: true });
    setTimeout(() => setPopup({ ...popup, show: false }), 2500);
  };

  // FETCH ALL MAINTENANCE
  const fetchMaintenance = () => {
    fetch("http://localhost:8080/api/admin/maintenance")
      .then(res => res.json())
      .then(setMaintenanceData)
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchMaintenance();
  }, []);

  // CREATE OR UPDATE MAINTENANCE
  const handleCreateOrUpdate = () => {
    if (!towerName || !flatNumber || !amount || !dueDate || !overDueDate) {
      showPopup("All fields are required", "error");
      return;
    }

    const method = editingId ? "PUT" : "POST";
    const url = editingId
      ? `http://localhost:8080/api/admin/maintenance/${editingId}`
      : "http://localhost:8080/api/admin/maintenance";

    fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ towerName, flatNumber, amount, dueDate, overDueDate })
    })
      .then(res => res.json())
      .then(newItem => {
        if (editingId) {
          setMaintenanceData(prev =>
            prev.map(item => (item.maintenanceId === editingId ? newItem : item))
          );
          setEditingId(null);
          alert(`Maintenance updated for Tower ${newItem.towerName}, Flat ${newItem.flatNumber}`);
        } else {
          setMaintenanceData(prev => [...prev, newItem]);
          alert(`Maintenance created for Tower ${newItem.towerName}, Flat ${newItem.flatNumber}`);
        }

        setTowerName("");
        setFlatNumber("");
        setAmount("");
        setDueDate("");
        setOverDueDate("");

        showPopup(`Maintenance created for Tower ${newItem.towerName}, Flat ${newItem.flatNumber}`, "success");

      });
  };

  // DELETE MAINTENANCE
  const handleDelete = id => {
    if (!window.confirm("Are you sure you want to delete this maintenance?")) return;

    fetch(`http://localhost:8080/api/admin/maintenance/${id}`, { method: "DELETE" })
      .then(() => {
        showPopup("Maintenance deleted successfully", "error");
        fetchMaintenance(); //  important
      });
  };

  // APPROVE MAINTENANCE
  const handleApprove = (id) => {
    fetch(`http://localhost:8080/api/admin/maintenance/${id}/approve`, {
      method: "PUT"
    })
      .then(res => {
        if (!res.ok) throw new Error("Approve failed");
        return res.json();
      })
      .then(updated => {
        setMaintenanceData(prev =>
          prev.map(item =>
            item.maintenanceId === id ? updated : item
          )
        );

        showPopup(
          `Maintenance approved for Tower ${updated.towerName}, Flat ${updated.flatNumber}`,
          "success"
        );
      })
      .catch(() => {
        showPopup("Failed to approve maintenance", "error");
      });
  };


  // EDIT MAINTENANCE
  const handleEdit = item => {
    setEditingId(item.maintenanceId);
    setTowerName(item.towerName);
    setFlatNumber(item.flatNumber);
    setAmount(item.amount);
    setDueDate(item.dueDate);
    setOverDueDate(item.overDueDate);

    window.scrollTo({ top: 0, behavior: "smooth" });
    showPopup("Editing maintenance record", "info");
  };

  if (loading) return <DashboardLayout><p>Loading...</p></DashboardLayout>;

  return (
    <DashboardLayout>
        {/* POPUP NOTIFICATION */}
          {popup.show && (
            <div className={`popup ${popup.type}`}>
              {popup.message}
            </div>
          )}
      <div className="maintenance-container">

        <h2 className="maintenance-title">{editingId ? "Edit Maintenance" : "Add Maintenance"}</h2>

        <div className="admin-form">
          <label>Tower</label>
          <select value={towerName} onChange={e => setTowerName(e.target.value)}>
            <option value="">Select Tower</option>
            <option value="A">Tower A</option>
            <option value="B">Tower B</option>
            <option value="C">Tower C</option>
          </select>

          <label>Flat Number</label>
          <input
            type="text"
            placeholder="e.g. 101"
            value={flatNumber}
            onChange={e => setFlatNumber(e.target.value)}
          />

          <label>Amount</label>
          <input
            type="number"
            value={amount}
            onChange={e => setAmount(e.target.value)}
          />

          <label>Due Date</label>
          <input
            type="date"
            value={dueDate}
            onChange={e => setDueDate(e.target.value)}
          />

          <label>Overdue Date</label>
          <input
            type="date"
            value={overDueDate}
            onChange={e => setOverDueDate(e.target.value)}
          />

          <button onClick={handleCreateOrUpdate}>
            {editingId ? "Update Maintenance" : "Create Maintenance"}
          </button>
        </div>

        <h2 className="maintenance-title">Maintenance Records</h2>

        <table className="maintenance-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Tower</th>
              <th>Flat Number</th>
              <th>Amount</th>
              <th>Due Date</th>
              <th>Overdue Date</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {maintenanceData.map(item => (
              <tr key={item.maintenanceId}>
                <td>{item.maintenanceId}</td>
                <td>{item.towerName}</td>
                <td>{item.flatNumber}</td>
                <td>{item.amount}</td>
                <td>{item.dueDate}</td>
                <td>{item.overDueDate}</td>
                <td>{item.paymentStatus}</td>
                <td>
                  <button onClick={() => handleEdit(item)}>Edit</button>
                  <button onClick={() => handleDelete(item.maintenanceId)}>Delete</button>
                  {item.paymentStatus === "PENDING" && (
                    <button onClick={() => handleApprove(item.maintenanceId)}>Approve</button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

      </div>
    </DashboardLayout>
  );
}
