import { useEffect, useState } from "react";
import api from "../../../api/api";
import "./ResidentFacilityBooking.css";

export default function ResidentFacilityBooking() {
  const [facilities, setFacilities] = useState([]);
  const [facilityId, setFacilityId] = useState("");
  const [bookingDate, setBookingDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [purpose, setPurpose] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    api.get("/api/facilities/active")
      .then(res => setFacilities(res.data))
      .catch(() => alert("Session expired"));
  }, []);

  const bookFacility = async (e) => {
    e.preventDefault();

    if (startTime >= endTime) {
      alert("End time must be after start time");
      return;
    }

    try {
      setLoading(true);

      await api.post("/api/bookings", {
        facilityId,
        bookingDate,
        startTime,
        endTime,
        purpose,
      });

      alert("Booking request submitted");
      setFacilityId("");
      setBookingDate("");
      setStartTime("");
      setEndTime("");
      setPurpose("");
    } catch (err) {
      alert(err.response?.data || "Booking failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={bookFacility}>
      <select value={facilityId} onChange={e => setFacilityId(e.target.value)} required>
        <option value="">Select Facility</option>
        {facilities.map(f => (
          <option key={f.facilityId} value={f.facilityId}>
            {f.facilityName}
          </option>
        ))}
      </select>

      <input type="date" value={bookingDate} onChange={e => setBookingDate(e.target.value)} required />
      <input type="time" value={startTime} onChange={e => setStartTime(e.target.value)} required />
      <input type="time" value={endTime} onChange={e => setEndTime(e.target.value)} required />
      <input value={purpose} onChange={e => setPurpose(e.target.value)} required />
      <button type="submit">{loading ? "Booking..." : "Book Facility"}</button>
    </form>
  );
}
