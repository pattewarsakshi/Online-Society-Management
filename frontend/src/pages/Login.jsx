import { useState } from "react";
import api from "../api/axios";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
  try {
    const res = await api.post("/api/auth/login", {
      email,
      password,
    });

    const { token, role } = res.data;

    // 1️⃣ Persist auth info
    localStorage.setItem("token", token);
    localStorage.setItem("role", role);

    // 2️⃣ Role-based redirect (THIS PART)
    if (role === "SUPER_ADMIN") {
      window.location.href = "/super-admin";
    } else if (role === "ADMIN") {
      window.location.href = "/admin";
    } else if (role === "GUARD") {
      window.location.href = "/guard";
    } else if (role === "TENANT") {
      window.location.href = "/tenant";
    } else {
      // fallback safety
      window.location.href = "/";
    }

  } catch (err) {
    alert("Invalid credentials");
  }
};


  return (
    <div>
      <h2>Login</h2>
      <input placeholder="Email" onChange={e => setEmail(e.target.value)} />
      <input type="password" placeholder="Password" onChange={e => setPassword(e.target.value)} />
      <button onClick={handleLogin}>Login</button>
    </div>
  );
}

export default Login;
