import { useState } from "react";
import api from "../../api/api";
import "./RegisterSociety.css";

export default function RegisterSociety() {
  const [societyName, setSocietyName] = useState("");
  const [address, setAddress] = useState("");
  const [city, setCity] = useState("");
  const [pincode, setPincode] = useState("");

  const submit = async () => {
    try {
      await api.post("/api/societies", {
        societyName,
        address,
        city,
        pincode,
      });
      alert("Society registered successfully. Now create Admin.");
    } catch {
      alert("Failed to register society");
    }
  };

  return (
    <div className="register-card">
      <h2>Register Society</h2>

      <input placeholder="Society Name" onChange={e => setSocietyName(e.target.value)} />
      <input placeholder="Address" onChange={e => setAddress(e.target.value)} />
      <input placeholder="City" onChange={e => setCity(e.target.value)} />
      <input placeholder="Pincode" onChange={e => setPincode(e.target.value)} />

      <button onClick={submit}>Register</button>
    </div>
  );
}
