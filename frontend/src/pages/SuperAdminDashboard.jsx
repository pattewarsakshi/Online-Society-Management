import { useState } from "react";
import api from "../api/axios";

function SuperAdminDashboard() {
  const [name, setName] = useState("");
  const [address, setAddress] = useState("");

  const createSociety = async () => {
    try {
      await api.post("/api/societies", {
        societyName: name,
        address: address,
      });
      alert("Society created successfully");
    } catch (err) {
      alert("Failed to create society");
    }
  };

  return (
    <div>
      <h2>Super Admin Dashboard</h2>

      <h3>Create Society</h3>
      <input
        placeholder="Society Name"
        onChange={(e) => setName(e.target.value)}
      />
      <input
        placeholder="Address"
        onChange={(e) => setAddress(e.target.value)}
      />
      <button onClick={createSociety}>Create</button>
    </div>
  );
}

export default SuperAdminDashboard;
