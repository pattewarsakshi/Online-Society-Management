import { useState } from "react";
import api from "../../api/axios";
import { toast } from "react-toastify";

export default function EditNoticeModal({ notice, onClose, onSaved }) {
  const [form, setForm] = useState({
    title: notice.title,
    description: notice.description,
  });

  const save = async () => {
    try {
      await api.put(`/notices/${notice.noticeId}`, form);
      toast.success("Notice updated");
      onSaved();
      onClose();
    } catch {
      toast.error("Update failed");
    }
  };

  return (
    <div className="modal">
      <h3>Edit Notice</h3>

      <input
        value={form.title}
        onChange={e => setForm({ ...form, title: e.target.value })}
      />

      <textarea
        value={form.description}
        onChange={e => setForm({ ...form, description: e.target.value })}
      />

      <button onClick={save}>Save</button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
}
