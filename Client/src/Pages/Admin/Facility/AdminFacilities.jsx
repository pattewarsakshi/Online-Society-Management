import { useEffect, useState } from "react";
import AdminLayout from "../../../layouts/AdminLayout";
import api from "../../../api/api";
import "./AdminFacilityBookings.css";

export default function AdminFacilityBookings() {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadBookings();
  }, []);

  /* ================= LOAD BOOKINGS ================= */
  const loadBookings = async () => {
    try {
      setLoading(true);
      const res = await api.get("/api/admin/bookings");
      setBookings(res.data || []);
    } catch (err) {
      console.error("Failed to load bookings", err);
      alert("Failed to load bookings");
    } finally {
      setLoading(false);
    }
  };

  /* ================= APPROVE ================= */
  const approveBooking = async (bookingId) => {
    try {
      await api.put(`/api/admin/bookings/${bookingId}/approve`);
      alert("Booking approved");
      loadBookings();
    } catch (err) {
      console.error("Approve failed", err);
      alert("Failed to approve booking");
    }
  };

  /* ================= REJECT ================= */
  const rejectBooking = async (bookingId) => {
    try {
      await api.put(`/api/admin/bookings/${bookingId}/reject`);
      alert("Booking rejected");
      loadBookings();
    } catch (err) {
      console.error("Reject failed", err);
      alert("Failed to reject booking");
    }
  };

  return (
    <AdminLayout>
      <div className="admin-booking-container">
        <h2>Facility Booking Requests</h2>

        {loading ? (
          <p>Loading bookings...</p>
        ) : (
          <table className="booking-table">
            <thead>
              <tr>
                <th>Resident</th>
                <th>Facility</th>
                <th>Date</th>
                <th>Time</th>
                <th>Purpose</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {bookings.length === 0 ? (
                <tr>
                  <td colSpan="7" style={{ textAlign: "center" }}>
                    No booking requests
                  </td>
                </tr>
              ) : (
                bookings.map((b) => (
                  <tr key={b.bookingId}>
                    <td>{b.userName}</td>
                    <td>{b.facilityName.replace("_", " ")}</td>
                    <td>{b.bookingDate}</td>
                    <td>
                      {b.startTime} - {b.endTime}
                    </td>
                    <td>{b.purpose}</td>
                    <td>
                      <span className={`status ${b.status.toLowerCase()}`}>
                        {b.status}
                      </span>
                    </td>
                    <td>
                      {b.status === "PENDING" ? (
                        <>
                          <button
                            className="approve-btn"
                            onClick={() => approveBooking(b.bookingId)}
                          >
                            Approve
                          </button>
                          <button
                            className="reject-btn"
                            onClick={() => rejectBooking(b.bookingId)}
                          >
                            Reject
                          </button>
                        </>
                      ) : (
                        "-"
                      )}
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        )}
      </div>
    </AdminLayout>
  );
}
