import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = (e) => {
    e.preventDefault();

    const fakeToken = "temporary-demo-token";
    const adminEmail = localStorage.getItem("adminEmail");

    if (email === adminEmail) {
      localStorage.setItem("token", fakeToken);
      localStorage.setItem("role", "admin");
      navigate("/admin/dashboard");
      return;
    }

    localStorage.setItem("token", fakeToken);
    localStorage.setItem("role", "member");
    navigate("/member/dashboard");
  };

  return (
    <div className="auth-page">
      <div className="auth-card">

        <h2 className="auth-title">Login</h2>
        <p className="auth-subtitle">Sign in to continue</p>

        <form className="auth-form" onSubmit={handleLogin}>

          {/* EMAIL */}
          <div>
            <label className="auth-label">Email</label>
            <input
              className="auth-input"
              type="email"
              placeholder="Enter your email"
              required
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          {/* PASSWORD */}
          <div>
            <label className="auth-label">Password</label>
            <input
              className="auth-input"
              type="password"
              placeholder="Enter your password"
              required
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          {/* FORGOT PASSWORD */}
          <div className="auth-forgot">
            <Link to="/forgot">Forgot Password?</Link>
          </div>

          {/* LOGIN BUTTON */}
          <button className="auth-button" type="submit">
            Login
          </button>

          {/* REGISTER LINK */}
          <div className="auth-footer">
            Don’t have an account?
            <Link to="/register" className="auth-link">
              Register
            </Link>
          </div>

        </form>

      </div>
    </div>
  );
}
