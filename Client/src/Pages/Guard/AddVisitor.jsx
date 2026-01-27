import { useState } from "react";
import GuardLayout from "../../layouts/GuardLayout";
import api from "../../api/api.js";
import "./Guard.css";

export default function AddVisitor() {
  const [visitorName, setVisitorName] = useState("");
  const [phone, setPhone] = useState("");
  const [purpose, setPurpose] = useState("");
  const [flatId, setFlatId] = useState("");

  const addVisitor = async (e) => {
    e.preventDefault();
    try {
      await api.post("/api/visitors/society/1/user/1", {
        visitorName,
        phone,
        purpose,
        flatId: Number(flatId),
      });

      alert("Visitor entry added");
      setVisitorName("");
      setPhone("");
      setPurpose("");
      setFlatId("");
    } catch (err) {
      alert(err.response?.data || "Failed to add visitor");
    }
  };

  return (
    <GuardLayout>
      <div className="guard-page">
        <div className="guard-card">
          <h2>Add Visitor Entry</h2>
          <p className="sub-text">
            Enter visitor details before allowing entry
          </p>

          <form className="guard-form" onSubmit={addVisitor}>
            <div className="form-group">
              <label>Visitor Name</label>
              <input
                value={visitorName}
                onChange={(e) => setVisitorName(e.target.value)}
                required
              />
            </div>

            <div className="form-group">
              <label>Phone Number</label>
              <input
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
                required
              />
            </div>

            <div className="form-group">
              <label>Purpose</label>
              <input
                value={purpose}
                onChange={(e) => setPurpose(e.target.value)}
                required
              />
            </div>

            <div className="form-group">
              <label>Flat ID</label>
              <input
                value={flatId}
                onChange={(e) => setFlatId(e.target.value)}
                required
              />
            </div>

            <button type="submit" className="primary-btn">
              Add Visitor
            </button>
          </form>
        </div>
      </div>
    </GuardLayout>
  );
}
