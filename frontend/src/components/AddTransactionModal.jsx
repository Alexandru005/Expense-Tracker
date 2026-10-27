import { useState } from "react";
import Modal from "./Modal.jsx";
import { api, CATEGORIES, label } from "../api.js";
import { useAuth } from "../App.jsx";

export default function AddTransactionModal({ onClose, onSaved }) {
  const { applyTransaction } = useAuth();
  const [type, setType] = useState("EXPENSE");
  const [title, setTitle] = useState("");
  const [amount, setAmount] = useState("");
  const [category, setCategory] = useState("FOOD");
  const [error, setError] = useState("");
  const [busy, setBusy] = useState(false);

  const submit = async (e) => {
    e.preventDefault();
    if (!title.trim() || !(Number(amount) > 0)) return setError("Enter a title and an amount above 0");
    setBusy(true);
    setError("");
    try {
      const cat = type === "EXPENSE" ? category : "OTHER";
      await api.addTransaction({ title: title.trim(), type, category: cat, amount: Number(amount) });
      applyTransaction(type, amount);
      onSaved?.();
      onClose();
    } catch (err) {
      setError(err.message);
    } finally {
      setBusy(false);
    }
  };

  return (
    <Modal title="Add transaction" onClose={onClose}>
      <form onSubmit={submit} className="form">
        <div className="tabs">
          {["EXPENSE", "INCOME", "SAVINGS_DEPOSIT"].map((t) => (
            <button type="button" key={t} className={type === t ? "active" : ""} onClick={() => setType(t)}>
              {t === "SAVINGS_DEPOSIT" ? "Savings" : label(t)}
            </button>
          ))}
        </div>
        <label>Title<input value={title} onChange={(e) => setTitle(e.target.value)} placeholder="e.g. Groceries" /></label>
        <label>Amount<input type="number" step="0.01" min="0" value={amount} onChange={(e) => setAmount(e.target.value)} placeholder="0.00" /></label>
        {type === "EXPENSE" && (
          <label>Category
            <select value={category} onChange={(e) => setCategory(e.target.value)}>
              {CATEGORIES.map((c) => <option key={c} value={c}>{label(c)}</option>)}
            </select>
          </label>
        )}
        {error && <p className="error">{error}</p>}
        <div className="modal-actions">
          <button type="button" className="btn ghost" onClick={onClose}>Cancel</button>
          <button className="btn yellow" disabled={busy}>{busy ? "Saving..." : "Add"}</button>
        </div>
      </form>
    </Modal>
  );
}
