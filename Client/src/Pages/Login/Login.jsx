import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "./Login.css";

export default function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({});

  const handleLogin = (e) => {
    e.preventDefault();
    let newErrors = {};

    // REQUIRED FIELDS
    if (!email.trim()) newErrors.email = "Email is required";
    if (!password.trim()) newErrors.password = "Password is required";

    // EMAIL FORMAT
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (email && !emailRegex.test(email))
      newErrors.email = "Invalid email format";

    // PASSWORD LENGTH
    if (password && password.length < 6)
      newErrors.password = "Password must be at least 6 characters";

    // IF ANY ERRORS → SHOW FIRST TOAST
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      const firstError = Object.values(newErrors)[0];
      toast.error(firstError);
      return;
    }

    // ADMIN LOGIN CHECK
    if (email === "admin@society.com") {
      localStorage.setItem("token", `admin-${Date.now()}`);
      localStorage.setItem("role", "admin");
      localStorage.setItem("userEmail", email);

      toast.success("Admin login successful!");
      setTimeout(() => navigate("/admin/dashboard"), 1500);
      return;
    }

    // MEMBER LOGIN CHECK
    const members = JSON.parse(localStorage.getItem("members") || "[]");
    const exists = members.find((m) => m.email === email);

    if (!exists) {
      toast.error("User not found. Please register first.");
      return;
    }

    // Password check (simple for demo)
    if (exists.password !== password) {
      toast.error("Incorrect password");
      return;
    }

    // MEMBER LOGIN SUCCESS
    localStorage.setItem("token", `member-${Date.now()}`);
    localStorage.setItem("role", "member");
    localStorage.setItem("userEmail", email);

    toast.success("Login successful!");
    setTimeout(() => navigate("/member/dashboard"), 1500);
  };

  return (
    <div className="auth-page">
      <div className="auth-card">

        <h2 className="auth-title">Login</h2>
        <p className="auth-subtitle">Sign in to continue</p>

        <form className="auth-form" onSubmit={handleLogin}>

          {/* EMAIL */}
          <label className="auth-label">Email *</label>
          <input
            className="auth-input"
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => {
              setEmail(e.target.value);
              setErrors({});
            }}
          />
          {errors.email && <p className="auth-error">{errors.email}</p>}

          {/* PASSWORD */}
          <label className="auth-label">Password *</label>
          <input
            className="auth-input"
            type="password"
            placeholder="Enter your password"
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
              setErrors({});
            }}
          />
          {errors.password && <p className="auth-error">{errors.password}</p>}

          {/* FORGOT PASSWORD */}
          <div className="auth-forgot">
            <Link to="/forgot">Forgot Password?</Link>
          </div>

          {/* LOGIN BUTTON */}
          <button className="auth-button" type="submit">
            Login
          </button>

          {/* FOOTER */}
          <p className="auth-footer">
            Don’t have an account?
            <Link className="auth-link" to="/register">
              Register
            </Link>
          </p>
        </form>

        <div className="auth-demo-info">
          <p><strong>Admin Login:</strong> admin@society.com</p>
          <p><strong>Member Login:</strong> Use any registered email</p>
        </div>

      </div>
    </div>
  );
}
