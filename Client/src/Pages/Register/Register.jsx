import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "./Register.css";

export default function Register() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    apartment: "",
    password: "",
    confirmPassword: "",
  });

  const [errors, setErrors] = useState({});

  // Handle input change
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    setErrors({});
  };

  const handleRegister = (e) => {
    e.preventDefault();
    let newErrors = {};

    const { name, email, phone, apartment, password, confirmPassword } = formData;

    // REQUIRED FIELD VALIDATIONS
    if (!name.trim()) newErrors.name = "Full name is required";
    if (!email.trim()) newErrors.email = "Email is required";
    if (!phone.trim()) newErrors.phone = "Phone number is required";
    if (!apartment.trim()) newErrors.apartment = "Apartment / Flat number is required";
    if (!password.trim()) newErrors.password = "Password is required";
    if (!confirmPassword.trim()) newErrors.confirmPassword = "Confirm password is required";

    // EMAIL FORMAT
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (email && !emailRegex.test(email)) newErrors.email = "Invalid email format";

    // BLOCK ADMIN EMAIL
    if (email === "admin@society.com")
      newErrors.email = "This email is reserved for admin.";

    // PHONE VALIDATION → ONLY DIGITS
    if (phone && !/^[0-9]+$/.test(phone)) {
      newErrors.phone = "Phone number must contain digits only";
    }

    // PHONE VALIDATION → MUST BE EXACTLY 10 DIGITS
    if (phone && phone.length !== 10) {
      newErrors.phone = "Phone number must be exactly 10 digits";
    }

    // PASSWORD VALIDATION
    if (password && password.length < 6)
      newErrors.password = "Password must be at least 6 characters";

    if (password !== confirmPassword)
      newErrors.confirmPassword = "Passwords do not match";

    // IF ANY ERRORS FOUND → SHOW ERROR MESSAGE
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);

      // Show first error in toast
      const firstError = Object.values(newErrors)[0];
      toast.error(firstError);

      return;
    }

    // CHECK IF EMAIL ALREADY REGISTERED
    const members = JSON.parse(localStorage.getItem("members") || "[]");
    const exists = members.find((m) => m.email === email);

    if (exists) {
      toast.error("Email already registered. Please login.");
      return;
    }

    // SAVE MEMBER
    const newMember = {
      id: `member-${Date.now()}`,
      name,
      email,
      phone,
      apartment,
      password, 
      createdAt: new Date().toISOString(),
    };

    members.push(newMember);
    localStorage.setItem("members", JSON.stringify(members));

    toast.success("Registration successful! Redirecting to login...");

    setTimeout(() => navigate("/"), 1800);
  };

  return (
    <div className="auth-page">
      <div className="auth-card">

        <h2 className="auth-title">Register</h2>
        <p className="auth-subtitle">Create your account</p>

        <form className="auth-form" onSubmit={handleRegister}>

          {/* FULL NAME */}
          <label className="auth-label">Full Name *</label>
          <input
            name="name"
            className="auth-input"
            type="text"
            placeholder="Enter full name"
            value={formData.name}
            onChange={handleChange}
          />
          {errors.name && <p className="auth-error">{errors.name}</p>}

          {/* EMAIL */}
          <label className="auth-label">Email *</label>
          <input
            name="email"
            className="auth-input"
            type="email"
            placeholder="Enter email"
            value={formData.email}
            onChange={handleChange}
          />
          {errors.email && <p className="auth-error">{errors.email}</p>}

          {/* PHONE */}
          <label className="auth-label">Phone Number *</label>
          <input
            name="phone"
            className="auth-input"
            type="text"
            placeholder="Enter 10-digit phone number"
            value={formData.phone}
            onChange={handleChange}
          />
          {errors.phone && <p className="auth-error">{errors.phone}</p>}

          {/* APARTMENT */}
          <label className="auth-label">Apartment / Flat No *</label>
          <input
            name="apartment"
            className="auth-input"
            type="text"
            placeholder="e.g., A-101"
            value={formData.apartment}
            onChange={handleChange}
          />
          {errors.apartment && <p className="auth-error">{errors.apartment}</p>}

          {/* PASSWORD */}
          <label className="auth-label">Password *</label>
          <input
            name="password"
            className="auth-input"
            type="password"
            placeholder="Enter password"
            value={formData.password}
            onChange={handleChange}
          />
          {errors.password && <p className="auth-error">{errors.password}</p>}

          {/* CONFIRM PASSWORD */}
          <label className="auth-label">Confirm Password *</label>
          <input
            name="confirmPassword"
            className="auth-input"
            type="password"
            placeholder="Re-enter password"
            value={formData.confirmPassword}
            onChange={handleChange}
          />
          {errors.confirmPassword && (
            <p className="auth-error">{errors.confirmPassword}</p>
          )}

          {/* REGISTER BUTTON */}
          <button className="auth-button" type="submit">
            Register
          </button>

          {/* REDIRECT */}
          <p className="auth-footer">
            Already have an account?
            <Link to="/" className="auth-link">
              Login
            </Link>
          </p>

        </form>
      </div>
    </div>
  );
}
