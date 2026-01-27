import { useEffect, useState } from "react";
import api from "../../../api/api";
import "./ResidentBookFacility.css";

export default function ResidentFacilityBooking() {
  const [facilities, setFacilities] = useState([]);
  const [facilityId, setFacilityId] = useState("");
  const [bookingDate, setBookingDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [purpose, setPurpose] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadFacilities();
  }, []);

  const loadFacilities = async () => {
    try {
      const res = await api.get("/api/facilities/active");
      setFacilities(res.data || []);
    } catch (err) {
      alert("Failed to load facilities. Please login again.");
    }
  };

  const bookFacility = async (e) => {
    e.preventDefault();

    // 🔒 FRONTEND VALIDATION
    if (!facilityId || !bookingDate || !startTime || !endTime || !purpose) {
      alert("Please fill all fields");
      return;
    }

    if (startTime >= endTime) {
      alert("End time must be after start time");
      return;
    }

    // 🔥 FINAL PAYLOAD (TYPE SAFE)
    const payload = {
      facilityId: Number(facilityId), // 🔴 THIS FIXES THE BUG
      bookingDate,                    // yyyy-MM-dd
      startTime,                      // HH:mm
      endTime,                        // HH:mm
      purpose: purpose.trim(),
    };

    try {
      setLoading(true);
      await api.post("/api/bookings", payload);
      alert("Booking request submitted successfully");

      // RESET
      setFacilityId("");
      setBookingDate("");
      setStartTime("");
      setEndTime("");
      setPurpose("");
    } catch (err) {
      console.error("Booking failed:", err);
      alert(
        err.response?.data ||
        "Booking failed. Ensure flat is assigned and facility is active."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="resident-booking-container">
      <h2>Book Facility</h2>

      <form className="booking-form" onSubmit={bookFacility}>
        <select
          value={facilityId}
          required
          onChange={(e) => setFacilityId(e.target.value)}
        >
          <option value="">Select Facility</option>
          {facilities.map((f) => (
            <option key={f.facilityId} value={f.facilityId}>
              {f.facilityName.replace("_", " ")}
            </option>
          ))}
        </select>

        <input
          type="date"
          value={bookingDate}
          required
          onChange={(e) => setBookingDate(e.target.value)}
        />

        <input
          type="time"
          value={startTime}
          required
          onChange={(e) => setStartTime(e.target.value)}
        />

        <input
          type="time"
          value={endTime}
          required
          onChange={(e) => setEndTime(e.target.value)}
        />

        <input
          type="text"
          placeholder="Purpose"
          value={purpose}
          required
          onChange={(e) => setPurpose(e.target.value)}
        />

        <button type="submit" disabled={loading}>
          {loading ? "Booking..." : "Request Booking"}
        </button>
      </form>
    </div>
  );
}
