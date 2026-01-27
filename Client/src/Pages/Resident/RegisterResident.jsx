import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api/api";
import "./RegisterResident.css";

export default function RegisterResident() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    firstName: "",
    middleName: "",
    lastName: "",
    email: "",
    phone: "",
    password: "",
    societyId: "",
    flatId: "",
  });

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      await api.post("/api/auth/register", {
        firstName: form.firstName,
        middleName: form.middleName || null,
        lastName: form.lastName,
        email: form.email,
        phone: form.phone,
        password: form.password,
        role: "RESIDENT",
        societyId: Number(form.societyId),
        flatId: Number(form.flatId),
      });

      alert("Resident registered successfully. Please login.");
      navigate("/login");

    } catch (err) {
      setError(err.response?.data || "Registration failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="resident-register-container">
      <form className="resident-register-card" onSubmit={handleSubmit}>
        <h2>Resident Registration</h2>

        {error && <p className="error">{error}</p>}

        <input name="firstName" placeholder="First Name" onChange={handleChange} required />
        <input name="middleName" placeholder="Middle Name (optional)" onChange={handleChange} />
        <input name="lastName" placeholder="Last Name" onChange={handleChange} required />
        <input type="email" name="email" placeholder="Email" onChange={handleChange} required />
        <input name="phone" placeholder="Phone" onChange={handleChange} required />
        <input type="number" name="societyId" placeholder="Society ID" onChange={handleChange} required />
        <input type="number" name="flatId" placeholder="Flat ID" onChange={handleChange} required />
        <input type="password" name="password" placeholder="Password" onChange={handleChange} required />

        <button type="submit" disabled={loading}>
          {loading ? "Registering..." : "Register"}
        </button>
      </form>
    </div>
  );
}
