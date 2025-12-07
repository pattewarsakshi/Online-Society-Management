import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "./ForgotPassword.css";

export default function ForgotPassword() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [errors, setErrors] = useState({});

  const handleSubmit = (e) => {
    e.preventDefault();
    let newErrors = {};

    // Required Field
    if (!email.trim()) newErrors.email = "Email is required";

    // Email Format Validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (email && !emailRegex.test(email))
      newErrors.email = "Invalid email format";

    // If errors → show toast
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      toast.error(Object.values(newErrors)[0]); // First error
      return;
    }

    // Check if email exists in registered members
    const members = JSON.parse(localStorage.getItem("members") || "[]");
    const memberExists = members.find((u) => u.email === email);

    const isAdmin = email === "admin@society.com";

    if (!memberExists && !isAdmin) {
      toast.error("Email not found. Please register.");
      return;
    }

    // Simulate sending reset link
    toast.success("Reset link sent to your email!");

    setTimeout(() => navigate("/"), 2000); // redirect to login
  };

  return (
    <div className="auth-page">
      <div className="auth-card">

        <h2 className="auth-title">Forgot Password</h2>
        <p className="auth-subtitle">
          Enter your email to receive reset instructions
        </p>

        <form className="auth-form" onSubmit={handleSubmit}>

          {/* EMAIL */}
          <label className="auth-label">Email *</label>
          <input
            className="auth-input"
            type="email"
            placeholder="Enter your registered email"
            value={email}
            onChange={(e) => {
              setEmail(e.target.value);
              setErrors({});
            }}
          />
          {errors.email && <p className="auth-error">{errors.email}</p>}

          {/* SUBMIT BUTTON */}
          <button className="auth-button" type="submit">
            Send Reset Link
          </button>

          {/* BACK TO LOGIN */}
          <p className="auth-footer">
            Remember your password?
            <Link to="/" className="auth-link">
              Login
            </Link>
          </p>

        </form>
      </div>
    </div>
  );
}
