import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getMySocieties } from "../../api/societyApi";

const SocietiesList = () => {
  const [societies, setSocieties] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetchSocieties();
  }, []);

  const fetchSocieties = async () => {
    try {
      const res = await getMySocieties();
      setSocieties(res.data);
    } catch (err) {
      console.error("Failed to load societies", err);
      alert("Failed to load societies");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <p style={{ padding: 20 }}>Loading societies...</p>;
  }

  return (
    <div style={{ padding: 24 }}>
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <h2>My Societies</h2>
        <button onClick={() => navigate("/super-admin/societies/create")}>
          + Create Society
        </button>
      </div>

      {societies.length === 0 ? (
        <p>No societies created yet.</p>
      ) : (
        <table
          border="1"
          cellPadding="10"
          style={{ marginTop: 20, width: "100%" }}
        >
          <thead>
            <tr>
              <th>Name</th>
              <th>City</th>
              <th>Pincode</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
  {societies.map((society) => (
    <tr key={society.societyId}>
      <td>{society.societyName}</td>
      <td>{society.city}</td>
      <td>{society.pincode}</td>
      <td>
        <button
          onClick={() =>
            navigate(
              `/super-admin/societies/${society.societyId}/create-admin`
            )
          }
        >
          Create Admin
        </button>
      </td>
    </tr>
  ))}
</tbody>



        </table>
      )}
    </div>
  );
};

export default SocietiesList;
