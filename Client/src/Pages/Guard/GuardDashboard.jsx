import { useEffect, useState } from "react";
import GuardLayout from "../../layouts/GuardLayout";
import api from "../../api/api.js";

export default function GuardDashboard() {
  const [insideCount, setInsideCount] = useState(0);

  useEffect(() => {
    api.get("/api/visitors/society/1/inside")
      .then(res => setInsideCount(res.data.length));
  }, []);

  return (
    <GuardLayout>
      <h2>Guard Dashboard</h2>

      <div style={card}>
        <h3>{insideCount}</h3>
        <p>Visitors Currently Inside</p>
      </div>
    </GuardLayout>
  );
}

const card = {
  background: "white",
  padding: "20px",
  width: "220px",
  borderRadius: "8px",
  boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
};
