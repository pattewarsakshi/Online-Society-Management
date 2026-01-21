import DashboardLayout from "../../layouts/DashboardLayout";
import { useEffect, useState } from "react";
import "./DocumentList.css";

export default function DocumentList() {
  const [documents, setDocuments] = useState([]);
  const [newDocumentName, setNewDocumentName] = useState("");
  const [file, setFile] = useState(null);

  useEffect(() => {
    loadDummyDocuments();
    setDocuments(JSON.parse(localStorage.getItem("documents") || "[]"));
  }, []);

  const saveDocuments = (updated) => {
    setDocuments(updated);
    localStorage.setItem("documents", JSON.stringify(updated));
  };

  const handleUpload = (e) => {
    e.preventDefault();
    if (!newDocumentName || !file) {
      alert("Please enter document name and choose file!");
      return;
    }

    const updatedDocs = [
      ...documents,
      {
        id: Date.now(),
        name: newDocumentName,
        uploaded: new Date().toISOString().slice(0, 10),
        url: URL.createObjectURL(file),
      },
    ];

    saveDocuments(updatedDocs);
    setNewDocumentName("");
    setFile(null);
  };

  return (
    <DashboardLayout>
      <div className="doc-container">
        <h2 className="page-title">Documents</h2>
        <p className="page-subtext">View, download and upload society documents</p>

        {/* Upload Form */}
        <form className="upload-card" onSubmit={handleUpload}>
          <input
            type="text"
            placeholder="Enter document name"
            value={newDocumentName}
            onChange={(e) => setNewDocumentName(e.target.value)}
            className="doc-input"
          />

          <input
            type="file"
            onChange={(e) => setFile(e.target.files[0])}
            className="upload-input"
            accept=".pdf,.jpg,.png,.xlsx,.docx"
          />

          <button type="submit" className="upload-btn">
            Upload
          </button>
        </form>

        {/* Document Table */}
        <div className="table-card">
          <table className="doc-table">
            <thead>
              <tr>
                <th>Document Name</th>
                <th>Uploaded Date</th>
                <th>Download</th>
              </tr>
            </thead>

            <tbody>
              {documents.length === 0 ? (
                <tr>
                  <td colSpan="3" className="empty-row">
                    No Documents Found
                  </td>
                </tr>
              ) : (
                documents.map((d) => (
                  <tr key={d.id}>
                    <td>{d.name}</td>
                    <td>{d.uploaded}</td>
                    <td>
                      <a href={d.url} download className="download-btn">
                        Download
                      </a>
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

// Dummy Data if Empty
const loadDummyDocuments = () => {
  if (!localStorage.getItem("documents")) {
    localStorage.setItem(
      "documents",
      JSON.stringify([
        {
          id: 1,
          name: "Society Meeting Minutes",
          uploaded: "2025-01-06",
          url: "#",
        },
        {
          id: 2,
          name: "Financial Budget 2024",
          uploaded: "2025-01-08",
          url: "#",
        },
      ])
    );
  }
};
