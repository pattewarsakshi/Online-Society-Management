import api from "./api.js";

export async function logout(navigate) {
  try {
    await api.post("/api/auth/logout");
  } catch (e) {
    // ignore error, still redirect
  } finally {
    navigate("/login");
  }
}
