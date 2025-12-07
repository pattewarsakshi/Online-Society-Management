import DashboardLayout from "../../layouts/DashboardLayout";

export default function AdminDashboard() {
  return (
    <DashboardLayout>
      <div style={{ padding: "20px" }}>
        <h1>Admin Dashboard (Test View)</h1>
        <p>
          If you can see the sidebar on the left and the navbar on top,
          your layout is working correctly.
        </p>

        <div style={{
          marginTop: "20px",
          background: "#fff",
          padding: "20px",
          borderRadius: "10px",
          boxShadow: "0 2px 6px rgba(0,0,0,0.1)"
        }}>
          <h3>Test Box</h3>
          <p>This is a placeholder box to check layout spacing.</p>
        </div>
      </div>
    </DashboardLayout>
  );
}
