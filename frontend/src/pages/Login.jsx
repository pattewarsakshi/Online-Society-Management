import { useState } from "react";
import api from "../api/axios";
import { useAuth } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { setUser } = useAuth();


  const navigate = useNavigate();


  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const res = await api.post("/api/auth/login", {
        email,
        password,
      });

      const { token, role, societyId } = res.data;

      // store token
      localStorage.setItem("token", token);

      // store user in context
      setUser({ role, societyId });

      if (role === "SUPER_ADMIN") 
    {
     navigate("/super-admin/societies");
    }


      alert("User stored in AuthContext");
    } catch (err) {
      console.error("LOGIN ERROR 👉", err);
    }
  };

  return (
    <div style={{ padding: 40 }}>
      <h2>Login</h2>

      <form onSubmit={handleLogin}>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        /><br /><br />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        /><br /><br />

        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default Login;
