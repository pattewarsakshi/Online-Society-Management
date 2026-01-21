import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useAuth } from "../../contexts/AuthContext";
import "./Login.css";

export default function Login() {
  const navigate = useNavigate();
  const { login, user } = useAuth();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({});

  // Redirect if already logged in
  useEffect(() => {
    if (user) {
      if (user.role === "admin") {
        navigate("/admin/dashboard", { replace: true });
      } else if (user.role === "member" && user.status === "approved") {
        navigate("/member/dashboard", { replace: true });
      }
    }
  }, [user, navigate]);

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
    if (email === "admin@urbannest.com") {
      // Default admin password is "admin123" (can be changed)
      if (password !== "admin123") {
        toast.error("Incorrect password");
        return;
      }

      const adminUser = {
        id: "admin-1",
        name: "Admin",
        email: "admin@urbannest.com",
        role: "admin",
        status: "approved",
      };

      login(adminUser);
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

    // Password check
    if (exists.password !== password) {
      toast.error("Incorrect password");
      return;
    }

    // Check if member status is pending
    if (exists.status === "pending") {
      toast.error("Your account is pending approval. Please wait for admin approval.");
      return;
    }

    // MEMBER LOGIN SUCCESS - only if status is approved
    if (exists.status === "approved") {
      const memberUser = {
        id: exists.id,
        name: exists.name,
        email: exists.email,
        role: "member",
        status: "approved",
      };

      login(memberUser);
      toast.success("Login successful!");
      setTimeout(() => navigate("/member/dashboard"), 1500);
    } else {
      toast.error("Your account is not approved. Please contact admin.");
    }
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
          <p><strong>Admin Login:</strong> admin@urbannest.com / admin123</p>
          <p><strong>Member Login:</strong> Use any registered email (must be approved)</p>
        </div>

      </div>
    </div>
  );
}
