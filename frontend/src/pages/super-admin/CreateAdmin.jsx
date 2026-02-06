import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { createAdmin } from "../../api/societyApi";

const CreateAdmin = () => {
  const { societyId } = useParams();
  const navigate = useNavigate();

  const [form, setForm] = useState({
    fullName: "",
    email: "",
    phone: "",
    password: "",
  });

  const [submitting, setSubmitting] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);

    try {
      await createAdmin(societyId, form);
      alert("Admin created successfully");
      navigate("/super-admin/societies");
    } catch (err) {
      if (err.response?.status === 409) {
        alert("Admin already exists for this society");
      } else {
        alert("Failed to create admin");
      }
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div style={{ padding: 24, maxWidth: 600 }}>
      <h2>Create Admin</h2>

      <form onSubmit={handleSubmit}>
        <div>
          <label>Full Name</label><br />
          <input
            name="fullName"
            value={form.fullName}
            onChange={handleChange}
            required
          />
        </div><br />

        <div>
          <label>Email</label><br />
          <input
            type="email"
            name="email"
            value={form.email}
            onChange={handleChange}
            required
          />
        </div><br />

        <div>
          <label>Phone</label><br />
          <input
  name="phone"
  value={form.phone}
  onChange={handleChange}
  required
  pattern="[6-9][0-9]{9}"
  title="Enter valid 10-digit Indian mobile number"
/>

        </div><br />

        <div>
          <label>Password</label><br />
          <input
            type="password"
            name="password"
            value={form.password}
            onChange={handleChange}
            required
          />
        </div><br />

        <button type="submit" disabled={submitting}>
          {submitting ? "Creating..." : "Create Admin"}
        </button>
      </form>
    </div>
  );
};

export default CreateAdmin;
