import { useEffect, useState } from "react";
import { api, MONTHS } from "../api.js";
import TransactionTable from "../components/TransactionTable.jsx";
import ExpenseDonut from "../components/ExpenseDonut.jsx";

export default function History() {
  const now = new Date();
  const [year, setYear] = useState(now.getFullYear());
  const [month, setMonth] = useState(now.getMonth() + 1);
  const [rows, setRows] = useState([]);
  const [chart, setChart] = useState([]);
  const [error, setError] = useState("");
  const years = Array.from({ length: 6 }, (_, i) => now.getFullYear() - i);

  useEffect(() => {
    const m = month === "" ? null : month;
    setError("");
    Promise.all([api.transactions(year, m), api.expenseChart(year, m)])
      .then(([t, c]) => { setRows(t || []); setChart(c || []); })
      .catch((e) => setError(e.message));
  }, [year, month]);

  return (
    <>
      <div className="filters">
        <select value={month} onChange={(e) => setMonth(e.target.value === "" ? "" : Number(e.target.value))}>
          <option value="">All months</option>
          {MONTHS.map((m, i) => <option key={m} value={i + 1}>{m}</option>)}
        </select>
        <select value={year} onChange={(e) => setYear(Number(e.target.value))}>
          {years.map((y) => <option key={y}>{y}</option>)}
        </select>
      </div>
      {error && <p className="error">{error}</p>}
      <div className="grid">
        <div className="card">
          <h4>Transactions</h4>
          <TransactionTable rows={rows} pageSize={10} />
        </div>
        <div className="card">
          <h4>Spending by category</h4>
          <ExpenseDonut data={chart} />
        </div>
      </div>
    </>
  );
}
