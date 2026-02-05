import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const res = await api.post("/api/auth/login", {
        email,
        password,
      });

      console.log("LOGIN RESPONSE 👉", res.data);

      const { token, role } = res.data;

      localStorage.setItem("token", token);
      localStorage.setItem("role", role);

      // ✅ React Router navigation
      if (role === "SUPER_ADMIN") navigate("/super-admin");
      else if (role === "ADMIN") navigate("/admin");
      else if (role === "GUARD") navigate("/guard");
      else if (role === "TENANT") navigate("/tenant");
      else navigate("/");

    } catch (err) {
      console.error("LOGIN ERROR 👉", err?.response?.data || err.message);
      alert("Login failed");
    }

    console.log("ROLE FROM BACKEND 👉", role);

const cleanRole = role.toUpperCase(); // 🔥 IMPORTANT

console.log("CLEAN ROLE 👉", cleanRole);
console.log("ABOUT TO NAVIGATE 👉 /guard");

navigate("/guard");

  };

  return (
    <div style={{ padding: "40px" }}>
      <h2>Login</h2>

      <form onSubmit={handleLogin}>
        <input
          type="email"
          placeholder="Email"
          value={email}
          required
          onChange={(e) => setEmail(e.target.value)}
        />

        <br /><br />

        <input
          type="password"
          placeholder="Password"
          value={password}
          required
          autoComplete="current-password"
          onChange={(e) => setPassword(e.target.value)}
        />

        <br /><br />

        <button type="submit">Login</button>
      </form>
    </div>
  );
}
