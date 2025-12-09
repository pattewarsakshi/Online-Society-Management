import DashboardLayout from "../../layouts/DashboardLayout";
import { useEffect, useState } from "react";
import "./ParkingList.css";

export default function ParkingList() {
  const [parkingData, setParkingData] = useState([]);
  const [vehicle, setVehicle] = useState("");
  const [slot, setSlot] = useState("");
  const [searchText, setSearchText] = useState("");

  useEffect(() => {
    loadDummyParking();
    setParkingData(JSON.parse(localStorage.getItem("parking") || "[]"));
  }, []);

  const saveParking = (updatedData) => {
    setParkingData(updatedData);
    localStorage.setItem("parking", JSON.stringify(updatedData));
  };

  const handleAdd = (e) => {
    e.preventDefault();

    if (!vehicle || !slot) {
      alert("Vehicle number and slot are required!");
      return;
    }

    const updatedData = [
      ...parkingData,
      {
        id: Date.now(),
        vehicle,
        slot,
        assignedOn: new Date().toISOString().slice(0, 10),
      },
    ];

    saveParking(updatedData);

    setVehicle("");
    setSlot("");
  };

  const deleteParking = (id) => {
    if (!window.confirm("Are you sure you want to delete?")) return;
    const updated = parkingData.filter((p) => p.id !== id);
    saveParking(updated);
  };

  const filteredData = parkingData.filter(
    (item) =>
      item.vehicle.toLowerCase().includes(searchText.toLowerCase()) ||
      item.slot.toLowerCase().includes(searchText.toLowerCase())
  );

  return (
    <DashboardLayout>
      <div className="park-container">
        <h2 className="page-title">Parking Management</h2>
        <p className="page-subtext">Assign, view and manage parking slots</p>

        {/* Add Parking */}
        <form className="parking-form" onSubmit={handleAdd}>
          <input
            type="text"
            placeholder="Vehicle Number (e.g. MH12AB1234)"
            className="park-input"
            value={vehicle}
            onChange={(e) => setVehicle(e.target.value)}
          />

          <input
            type="text"
            placeholder="Slot No (e.g. P-09)"
            className="park-input"
            value={slot}
            onChange={(e) => setSlot(e.target.value)}
          />

          <button className="park-btn">Add Parking</button>
        </form>

        {/* Search */}
        <input
          type="text"
          className="search-box"
          placeholder="Search by vehicle or slot"
          value={searchText}
          onChange={(e) => setSearchText(e.target.value)}
        />

        {/* Parking Table */}
        <div className="table-card">
          <table className="park-table">
            <thead>
              <tr>
                <th>Vehicle Number</th>
                <th>Slot</th>
                <th>Assigned Date</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {filteredData.length === 0 ? (
                <tr>
                  <td colSpan={4} className="empty-text">
                    No parking records found
                  </td>
                </tr>
              ) : (
                filteredData.map((item) => (
                  <tr key={item.id}>
                    <td>{item.vehicle}</td>
                    <td>{item.slot}</td>
                    <td>{item.assignedOn}</td>
                    <td>
                      <button
                        className="delete-btn"
                        onClick={() => deleteParking(item.id)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>
    </DashboardLayout>
  );
}

const loadDummyParking = () => {
  if (!localStorage.getItem("parking")) {
    localStorage.setItem(
      "parking",
      JSON.stringify([
        {
          id: 1,
          vehicle: "MH12AB1234",
          slot: "P-09",
          assignedOn: "2025-01-06",
        },
        {
          id: 2,
          vehicle: "MH14XY8899",
          slot: "P-12",
          assignedOn: "2025-01-08",
        },
      ])
    );
  }
};
