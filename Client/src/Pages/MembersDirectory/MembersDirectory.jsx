import { useState, useEffect } from "react";
import DashboardLayout from "../../layouts/DashboardLayout";
import Card from "../../Components/Card";
import "./MembersDirectory.css";

export default function MembersDirectory() {
  const [members, setMembers] = useState([]);
  const [search, setSearch] = useState("");

  useEffect(() => {
    loadMembers();
  }, []);

  const loadMembers = () => {
    const stored = localStorage.getItem("members");
    if (stored) {
      try {
        const parsed = JSON.parse(stored);
        const allMembers = Array.isArray(parsed) ? parsed : [];
        
        // Filter: Only show approved members
        const approvedMembers = allMembers.filter(
          (member) => member.status === "approved"
        );
        
        setMembers(approvedMembers);
      } catch (error) {
        console.error("Error loading members:", error);
        setMembers([]);
      }
    } else {
      setMembers([]);
    }
  };

  const filteredMembers = members.filter((member) => {
    const searchLower = search.toLowerCase();
    return (
      member.name?.toLowerCase().includes(searchLower) ||
      member.apartment?.toLowerCase().includes(searchLower) ||
      member.phone?.includes(search)
    );
  });

  return (
    <DashboardLayout>
      <div className="members-directory">
        <div className="directory-header">
          <h1 className="page-title">Member Directory</h1>
          <p className="page-subtitle">Know Your Neighbour</p>
        </div>

        <Card className="search-card">
          <input
            type="text"
            placeholder="Search by name, flat number, or phone..."
            className="search-input"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
        </Card>

        {filteredMembers.length === 0 ? (
          <Card className="empty-state-card">
            <p className="empty-message">
              {search ? "No members found matching your search" : "No members available"}
            </p>
          </Card>
        ) : (
          <Card className="table-card">
            <div className="table-container">
              <table className="members-table">
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Flat Number</th>
                    <th>Contact Number</th>
                  </tr>
                </thead>
                <tbody>
                  {filteredMembers.map((member) => (
                    <tr key={member.id}>
                      <td className="member-name-cell">
                        <span className="member-name">{member.name || "N/A"}</span>
                      </td>
                      <td className="member-flat-cell">
                        <span className="member-flat">{member.apartment || "N/A"}</span>
                      </td>
                      <td className="member-phone-cell">
                        <span className="member-phone">{member.phone || "N/A"}</span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </Card>
        )}

        <div className="directory-footer">
          <p className="footer-note">
            Showing {filteredMembers.length} of {members.length} approved members
          </p>
        </div>
      </div>
    </DashboardLayout>
  );
}
