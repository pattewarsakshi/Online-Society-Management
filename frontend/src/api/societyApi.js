import api from "./axios";

// SUPER_ADMIN – get societies created by me
export const getMySocieties = () => {
  return api.get("/api/societies/my");
};

//SUPER_ADMIN - create society
export const createSociety = (payload) => {
  return api.post("/api/societies", payload);
};


//SUPER_ADMIN - assigns to admin to society
export const createAdmin = (societyId, payload) => {
  return api.post(`/api/societies/${societyId}/admin`, payload);
};
