import { useEffect, useState, useCallback } from "react";
import { api, money, MONTHS } from "../api.js";
import { useAuth } from "../App.jsx";
import TransactionTable from "../components/TransactionTable.jsx";
import ExpenseDonut from "../components/ExpenseDonut.jsx";
import AddTransactionModal from "../components/AddTransactionModal.jsx";

export default function Dashboard() {
  const { user } = useAuth();
  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;
  const [rows, setRows] = useState([]);
  const [chart, setChart] = useState([]);
  const [error, setError] = useState("");
  const [modal, setModal] = useState(false);

  const load = useCallback(() => {
    Promise.all([api.transactions(year, month), api.expenseChart(year, month)])
      .then(([t, c]) => { setRows(t || []); setChart(c || []); })
      .catch((e) => setError(e.message));
  }, [year, month]);
  useEffect(load, [load]);

  return (
    <>
      <h2 className="greet">Hey, {user?.name?.split("")}</h2>
      <p className="muted">Here's your overview for {MONTHS[month - 1]} {year}.</p>
      <div className="card total">
        <div>
          <span className="muted small">Current balance</span>
          <div className="big">Total: {money(user?.balance)}</div>
        </div>
        <button className="btn yellow" onClick={() => setModal(true)}>+ Add transaction</button>
      </div>
      {error && <p className="error">{error}</p>}
      <div className="grid">
        <div className="card">
          <h4>Recent transactions</h4>
          <TransactionTable rows={rows} />
        </div>
        <div className="card">
          <h4>Spending by category</h4>
          <ExpenseDonut data={chart} />
        </div>
      </div>
      {modal && <AddTransactionModal onClose={() => setModal(false)} onSaved={load} />}
    </>
  );
}
