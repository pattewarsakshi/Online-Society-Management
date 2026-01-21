import { useAuth } from "../../contexts/AuthContext";
import { useEffect, useState } from "react";
import "./Maintenance.css"  // ✅ Correct when CSS is in same folder
import DashboardLayout from "../../layouts/DashboardLayout";


const maintenanceData = [
  {
    "flat": "A-101",
    "month": "January 2025",
    "amount": 2500,
    "dueDate": "2025-01-10",
    "status": "Paid",
    "fine": 0
  },
  {
    "flat": "A-102",
    "month": "January 2025",
    "amount": 2500,
    "dueDate": "2025-01-10",
    "status": "Pending",
    "fine": 100
  },
  {
    "flat": "A-103",
    "month": "January 2025",
    "amount": 2500,
    "dueDate": "2025-01-10",
    "status": "Overdue",
    "fine": 150
  },
  {
    "flat": "B-201",
    "month": "February 2025",
    "amount": 2600,
    "dueDate": "2025-02-10",
    "status": "Paid",
    "fine": 0
  },
  {
    "flat": "B-202",
    "month": "February 2025",
    "amount": 2600,
    "dueDate": "2025-02-10",
    "status": "Pending",
    "fine": 50
  },
  {
    "flat": "B-203",
    "month": "February 2025",
    "amount": 2600,
    "dueDate": "2025-02-10",
    "status": "Overdue",
    "fine": 200
  },
  {
    "flat": "C-301",
    "month": "March 2025",
    "amount": 2700,
    "dueDate": "2025-03-10",
    "status": "Paid",
    "fine": 0
  },
  {
    "flat": "C-302",
    "month": "March 2025",
    "amount": 2700,
    "dueDate": "2025-03-10",
    "status": "Pending",
    "fine": 0
  },
  {
    "flat": "C-303",
    "month": "March 2025",
    "amount": 2700,
    "dueDate": "2025-03-10",
    "status": "Overdue",
    "fine": 120
  },
  {
    "flat": "D-401",
    "month": "April 2025",
    "amount": 2800,
    "dueDate": "2025-04-10",
    "status": "Paid",
    "fine": 0
  },
  {
    "flat": "D-402",
    "month": "April 2025",
    "amount": 2800,
    "dueDate": "2025-04-10",
    "status": "Pending",
    "fine": 0
  },
  {
    "flat": "D-403",
    "month": "April 2025",
    "amount": 2800,
    "dueDate": "2025-04-10",
    "status": "Overdue",
    "fine": 180
  },
  {
    "flat": "E-501",
    "month": "May 2025",
    "amount": 3000,
    "dueDate": "2025-05-10",
    "status": "Paid",
    "fine": 0
  },
  {
    "flat": "E-502",
    "month": "May 2025",
    "amount": 3000,
    "dueDate": "2025-05-10",
    "status": "Pending",
    "fine": 20
  },
  {
    "flat": "E-503",
    "month": "May 2025",
    "amount": 3000,
    "dueDate": "2025-05-10",
    "status": "Overdue",
    "fine": 210
  },
  {
    "flat": "F-601",
    "month": "June 2025",
    "amount": 3100,
    "dueDate": "2025-06-10",
    "status": "Paid",
    "fine": 0
  },
  {
    "flat": "F-602",
    "month": "June 2025",
    "amount": 3100,
    "dueDate": "2025-06-10",
    "status": "Pending",
    "fine": 0
  },
  {
    "flat": "F-603",
    "month": "June 2025",
    "amount": 3100,
    "dueDate": "2025-06-10",
    "status": "Overdue",
    "fine": 250
  },
  {
    "flat": "G-701",
    "month": "July 2025",
    "amount": 3200,
    "dueDate": "2025-07-10",
    "status": "Paid",
    "fine": 0
  },
  {
    "flat": "G-702",
    "month": "July 2025",
    "amount": 3200,
    "dueDate": "2025-07-10",
    "status": "Pending",
    "fine": 30
  }
]

export default function Maintenance() {
  const { user } = useAuth();
  const role = user?.role;

  // const [maintenanceData, setMaintenanceData] = useState([]);

  // useEffect(() => {
  //   const data = JSON.parse(localStorage.getItem("maintenanceData") || "[]");
  //   setMaintenanceData(data);
  // }, []);

  return (
   <DashboardLayout>
     <div className="maintenance-container">

      <h2 className="maintenance-title">
        {role === "admin" ? "Maintenance Management" : "My Maintenance"}
      </h2>

      {role === "admin" && (
        <div style={{ margin: "10px 0" }}>
          <button className="generate-btn">Create Monthly Maintenance</button>
          <button className="pdf-btn" style={{ marginLeft: "10px" }}>
            Export PDF Report
          </button>
        </div>
      )}

      <table className="maintenance-table">
        <thead>
          <tr>
            <th>Flat</th>
            <th>Month</th>
            <th>Amount</th>
            <th>Due Date</th>
            <th>Status</th>
            <th>Fine</th>
            {role === "admin" && <th>Action</th>}
          </tr>
        </thead>

        <tbody>
          {maintenanceData.length === 0 ? (
            <tr>
              <td colSpan={role === "admin" ? 7 : 6}>
                No records found.
              </td>
            </tr>
          ) : (
            maintenanceData.map((item, i) => (
              <tr key={i}>
                <td>{item.flat}</td>
                <td>{item.month}</td>
                <td>{item.amount}</td>
                <td>{item.dueDate}</td>

                  <td>
                    <span className={`status-tag ${item.status.toLowerCase()}`}>
                      {item.status}
                    </span>
                  </td>

                <td>{item.fine}</td>

                {role === "admin" && (
                  <td>
                    <button className="edit-btn">Edit</button>
                    <button className="delete-btn" style={{ marginLeft: "5px" }}>
                      Delete
                    </button>
                  </td>
                )}
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
   </DashboardLayout>
  );
}