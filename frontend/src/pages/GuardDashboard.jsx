import { useEffect, useState } from "react";
import api from "../api/axios";

export default function GuardDashboard() {
  return (
    <div style={{ padding: "40px" }}>
      <h1>Guard Dashboard</h1>
      <p>Register visitors, deliveries, and check-ins.</p>
    </div>
  );
}
