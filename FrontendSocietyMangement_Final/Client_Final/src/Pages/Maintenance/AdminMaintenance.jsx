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

  const [editingId, setEditingId] = useState(null);

  // POPUP
  const [popup, setPopup] = useState({ message: "", type: "info", show: false });

  const showPopup = (message, type = "info") => {
    setPopup({ message, type, show: true });
    setTimeout(() => setPopup(p => ({ ...p, show: false })), 2500);
  };

  // ================= FETCH =================
  const fetchMaintenance = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/admin/maintenance", {
        credentials: "include",
      });
      if (!res.ok) throw new Error("Failed to fetch");

      const data = await res.json();
      setMaintenanceData(data);
    } catch (err) {
      console.error(err);
      showPopup("Failed to load maintenance", "error");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchMaintenance();
  }, []);

  // ================= CREATE / UPDATE =================
  const handleCreateOrUpdate = async () => {
    if (!towerName || !flatNumber || !amount || !dueDate) {
      showPopup("All fields are required", "error");
      return;
    }

    // Only allow editing if status is PENDING
    if (editingId) {
      const current = maintenanceData.find(m => m.maintenanceId === editingId);
      if (current.paymentStatus !== "PENDING") {
        showPopup("Only pending maintenance can be edited", "error");
        return;
      }
    }

    const method = editingId ? "PUT" : "POST";
    const url = editingId
      ? `http://localhost:8080/api/admin/maintenance/${editingId}`
      : "http://localhost:8080/api/admin/maintenance";

    try {
      const res = await fetch(url, {
        method,
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ towerName, flatNumber, amount, dueDate, overDueDate }),
      });

      if (!res.ok) throw new Error("Save failed");

      const saved = await res.json();

      if (editingId) {
        setMaintenanceData(prev =>
          prev.map(m => (m.maintenanceId === editingId ? saved : m))
        );
        showPopup("Maintenance updated", "success");
      } else {
        setMaintenanceData(prev => [...prev, saved]);
        showPopup("Maintenance created", "success");
      }

      resetForm();
    } catch (err) {
      console.error(err);
      showPopup("Operation failed", "error");
    }
  };

  const resetForm = () => {
    setTowerName("");
    setFlatNumber("");
    setAmount("");
    setDueDate("");
    setOverDueDate("");
    setEditingId(null);
  };

  // ================= DELETE =================
  const handleDelete = async (id, status) => {
    if (status === "PAID" || status === "APPROVAL_PENDING") {
      showPopup("Cannot delete paid or approval pending maintenance", "error");
      return;
    }
    if (!window.confirm("Delete this maintenance?")) return;

    try {
      await fetch(`http://localhost:8080/api/admin/maintenance/${id}`, {
        method: "DELETE",
        credentials: "include",
      });
      setMaintenanceData(prev => prev.filter(m => m.maintenanceId !== id));
      showPopup("Maintenance deleted", "success");
    } catch {
      showPopup("Delete failed", "error");
    }
  };

  // ================= APPROVE =================
  const handleApprove = async (id, status) => {
    if (status !== "APPROVAL_PENDING") {
      showPopup("Only approval pending maintenance can be approved", "error");
      return;
    }

    try {
      const res = await fetch(
        `http://localhost:8080/api/admin/maintenance/${id}/approve`,
        { method: "PUT", credentials: "include" }
      );

      if (!res.ok) throw new Error();

      const updated = await res.json();
      setMaintenanceData(prev =>
        prev.map(m => (m.maintenanceId === id ? updated : m))
      );

      showPopup("Payment approved", "success");
    } catch {
      showPopup("Approve failed", "error");
    }
  };

  // ================= EDIT =================
  const handleEdit = (item) => {
    if (item.paymentStatus !== "PENDING") {
      showPopup("Only pending maintenance can be edited", "info");
      return;
    }

    setEditingId(item.maintenanceId);
    setTowerName(item.towerName);
    setFlatNumber(item.flatNumber);
    setAmount(item.amount);
    setDueDate(item.dueDate);
    setOverDueDate(item.overDueDate || "");

    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  if (loading) return <DashboardLayout><p>Loading...</p></DashboardLayout>;

  return (
    <DashboardLayout>
      {popup.show && <div className={`popup ${popup.type}`}>{popup.message}</div>}

      <div className="maintenance-container">
        <h2>{editingId ? "Edit Maintenance" : "Add Maintenance"}</h2>

        <div className="admin-form">
          <select value={towerName} onChange={e => setTowerName(e.target.value)}>
            <option value="">Select Tower</option>
            <option value="A">Tower A</option>
            <option value="B">Tower B</option>
            <option value="C">Tower C</option>
          </select>

          <input
            placeholder="Flat Number"
            value={flatNumber}
            onChange={e => setFlatNumber(e.target.value)}
          />
          <input
            type="number"
            placeholder="Amount"
            value={amount}
            onChange={e => setAmount(e.target.value)}
          />
          <input type="date" value={dueDate} onChange={e => setDueDate(e.target.value)} />
          <input type="date" value={overDueDate} onChange={e => setOverDueDate(e.target.value)} />

          <button onClick={handleCreateOrUpdate}>
            {editingId ? "Update" : "Create"}
          </button>
          {editingId && <button className="cancel-btn" onClick={resetForm}>Cancel</button>}
        </div>

        <table className="maintenance-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Tower</th>
              <th>Flat</th>
              <th>Amount</th>
              <th>Status</th>
              <th>Screenshot</th>
              <th>Transaction ID</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {maintenanceData.map(m => {
              const canEdit = m.paymentStatus === "PENDING";
              const canApprove = m.paymentStatus === "APPROVAL_PENDING";

              return (
                <tr key={m.maintenanceId}>
                  <td>{m.maintenanceId}</td>
                  <td>{m.towerName}</td>
                  <td>{m.flatNumber}</td>
                  <td>{m.amount}</td>
                  <td>{m.paymentStatus}</td>
                  <td>
                    {m.paymentProof ? (
                      <a
                        href={`http://localhost:8080/uploads/${encodeURIComponent(m.paymentProof)}`}
                        target="_blank"
                        rel="noreferrer"
                      >
                        <img
                          src={`http://localhost:8080/uploads/${encodeURIComponent(m.paymentProof)}`}
                          alt="proof"
                          style={{
                            width: "90px",
                            height: "60px",
                            objectFit: "cover",
                            borderRadius: "6px",
                            cursor: "pointer"
                          }}
                        />
                      </a>
                    ) : "-"}
                  </td>

                  <td>
                    {m.transactionId || "-"}
                  </td>
                  
                  <td>
                    <button
                      disabled={!canEdit}
                      className={`btn ${!canEdit ? "disabled" : ""}`}
                      onClick={() => handleEdit(m)}
                    >
                      Edit
                    </button>
                    <button
                      disabled={!canEdit}
                      className={`btn danger ${!canEdit ? "disabled" : ""}`}
                      onClick={() => handleDelete(m.maintenanceId, m.paymentStatus)}
                    >
                      Delete
                    </button>
                    <button
                      disabled={!canApprove}
                      className={`btn success ${!canApprove ? "disabled" : ""}`}
                      onClick={() => handleApprove(m.maintenanceId, m.paymentStatus)}
                    >
                      Approve
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </DashboardLayout>
  );
}
