// import { Navigate } from "react-router-dom";
// import { useEffect, useState } from "react";
// import api from "../api/api";

// export default function ProtectedRoute({ children, role }) {
//   const [loading, setLoading] = useState(true);
//   const [allowed, setAllowed] = useState(false);

//   useEffect(() => {
//     const checkAuth = async () => {
//       try {
//         const res = await api.get("/api/profile");

//         if (!role || res.data.role === role) {
//           setAllowed(true);
//         } else {
//           setAllowed(false);
//         }
//       } catch (err) {
//         setAllowed(false);
//       } finally {
//         setLoading(false);
//       }
//     };

//     checkAuth();
//   }, [role]);

//   if (loading) return null;

//   if (!allowed) {
//     return <Navigate to="/login" />;
//   }

//   return children;
// }
import { Navigate } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../api/api";

export default function ProtectedRoute({ children, role }) {
  const [status, setStatus] = useState("loading");

  useEffect(() => {
    api.get("/api/profile")
      .then((res) => {
        if (!role || res.data.role === role) {
          setStatus("allowed");
        } else {
          setStatus("denied");
        }
      })
      .catch(() => {
        setStatus("denied");
      });
  }, [role]);

  if (status === "loading") return null;
  if (status === "denied") return <Navigate to="/login" replace />;

  return children;
}

