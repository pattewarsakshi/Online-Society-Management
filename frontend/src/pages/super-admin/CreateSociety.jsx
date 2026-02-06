import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createSociety } from "../../api/societyApi";

const CreateSociety = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    societyName: "",
    address: "",
    city: "",
    state: "",
    pincode: "",
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
      await createSociety(form);
      alert("Society created successfully");
      navigate("/super-admin/societies");
    } catch (err) {
      console.error("Create society failed", err);
      alert("Failed to create society");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div style={{ padding: 24, maxWidth: 600 }}>
      <h2>Create Society</h2>

      <form onSubmit={handleSubmit}>
        <div>
          <label>Society Name</label><br />
          <input
            name="societyName"
            value={form.societyName}
            onChange={handleChange}
            required
          />
        </div><br />

        <div>
          <label>Address</label><br />
          <input
            name="address"
            value={form.address}
            onChange={handleChange}
            required
          />
        </div><br />

        <div>
          <label>City</label><br />
          <input
            name="city"
            value={form.city}
            onChange={handleChange}
            required
          />
        </div><br />

        <div>
          <label>State</label><br />
          <input
            name="state"
            value={form.state}
            onChange={handleChange}
            required
          />
        </div><br />

        <div>
          <label>Pincode</label><br />
          <input
            name="pincode"
            value={form.pincode}
            onChange={handleChange}
            required
            maxLength={6}
          />
        </div><br />

        <button type="submit" disabled={submitting}>
          {submitting ? "Creating..." : "Create Society"}
        </button>
      </form>
    </div>
  );
};

export default CreateSociety;
