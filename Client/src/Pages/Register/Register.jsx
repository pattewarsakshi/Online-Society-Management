import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../../services/api";
import "./Register.css";

export default function Register() {
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

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

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

        // IMPORTANT: enum value (must match Role enum)
        role: "RESIDENT",

        // backend expects Integer
        societyId: Number(form.societyId),
        flatId: Number(form.flatId),
      });

      alert("Registration successful! Please login.");
      navigate("/login");

    } catch (err) {
      setError(err.response?.data?.message || "Registration failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="register-container">
      <form className="register-card" onSubmit={handleSubmit}>
        <h2>Resident Registration</h2>

        {error && <p className="error">{error}</p>}

        <input
          name="firstName"
          placeholder="First Name"
          value={form.firstName}
          onChange={handleChange}
          required
        />

        <input
          name="middleName"
          placeholder="Middle Name (optional)"
          value={form.middleName}
          onChange={handleChange}
        />

        <input
          name="lastName"
          placeholder="Last Name"
          value={form.lastName}
          onChange={handleChange}
          required
        />

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={form.email}
          onChange={handleChange}
          required
        />

        <input
          name="phone"
          placeholder="Phone Number"
          value={form.phone}
          onChange={handleChange}
          required
        />

        <input
          type="number"
          name="societyId"
          placeholder="Society ID"
          value={form.societyId}
          onChange={handleChange}
          required
        />

        <input
          type="number"
          name="flatId"
          placeholder="Flat ID"
          value={form.flatId}
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
          required
        />

        <button type="submit" disabled={loading}>
          {loading ? "Registering..." : "Register"}
        </button>

        <p className="login-link">
          Already registered? <Link to="/login">Login</Link>
        </p>
      </form>
    </div>
  );
}
