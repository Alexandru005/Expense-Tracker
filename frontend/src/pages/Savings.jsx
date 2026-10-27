import { useEffect, useState, useCallback } from "react";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer } from "recharts";
import { api, money, fmtDate } from "../api.js";
import { useAuth } from "../App.jsx";
import Modal from "../components/Modal.jsx";

export default function Savings() {
  const { user, applyTransaction } = useAuth();
  const now = new Date();
  const [year, setYear] = useState(now.getFullYear());
  const [list, setList] = useState([]);
  const [chart, setChart] = useState([]);
  const [error, setError] = useState("");
  const [modal, setModal] = useState(false);
  const [amount, setAmount] = useState("");
  const [title, setTitle] = useState("Savings withdrawal");
  const [mErr, setMErr] = useState("");
  const years = Array.from({ length: 6 }, (_, i) => now.getFullYear() - i);

  const load = useCallback(() => {
    Promise.all([api.savings(), api.savingsChart(year)])
      .then(([l, c]) => { setList(l || []); setChart((c || []).map((x) => ({ ...x, amount: Number(x.amount), month: String(x.month).slice(0, 3) }))); })
      .catch((e) => setError(e.message));
  }, [year]);
  useEffect(load, [load]);

  const yearTotal = chart.reduce((s, x) => s + x.amount, 0);

  const withdraw = async (e) => {
    e.preventDefault();
    const a = Number(amount);
    if (!(a > 0)) return setMErr("Enter an amount above 0");
    if (a > Number(user?.savings || 0)) return setMErr("You can't withdraw more than you've saved");
    try {
      await api.addTransaction({ title: title || "Savings withdrawal", type: "SAVINGS_WITHDRAWAL", category: "WITHDRAWAL", amount: a });
      applyTransaction("SAVINGS_WITHDRAWAL", a);
      setModal(false);
      setAmount("");
      load();
    } catch (err) {
      setMErr(err.message);
    }
  };

  return (
    <>
      <div className="card total">
        <div>
          <span className="muted small">Total savings</span>
          <div className="big">Savings: {money(user?.savings)}</div>
        </div>
        <button className="btn dark" onClick={() => { setMErr(""); setModal(true); }}>Withdraw</button>
      </div>
      {error && <p className="error">{error}</p>}
      <div className="grid">
        <div className="card">
          <h4>Savings activity</h4>
          {list.length ? (
            <table className="table">
              <thead><tr><th>Title</th><th className="hide-sm">Date</th><th className="right">Amount</th></tr></thead>
              <tbody>
                {list.map((s, i) => {
                  const dep = s.type === "SAVINGS_DEPOSIT";
                  return (
                    <tr key={i}>
                      <td><div className="t-title">{s.title}</div><div className="t-sub">{fmtDate(s.date)}</div></td>
                      <td className="hide-sm">{fmtDate(s.date)}</td>
                      <td className={"right " + (dep ? "pos" : "neg")}>{dep ? "+" : "-"}{money(s.amount)}</td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          ) : <p className="empty">No savings yet.</p>}
        </div>
        <div className="card">
          <div className="row-between">
            <h4>Saved per month</h4>
            <select value={year} onChange={(e) => setYear(Number(e.target.value))}>
              {years.map((y) => <option key={y}>{y}</option>)}
            </select>
          </div>
          <div className="big-sm">{money(yearTotal)} <span className="muted small">saved in {year}</span></div>
          <ResponsiveContainer width="100%" height={220}>
            <BarChart data={chart}>
              <XAxis dataKey="month" tick={{ fontSize: 11 }} axisLine={false} tickLine={false} />
              <YAxis hide />
              <Tooltip formatter={(v) => money(v)} cursor={{ fill: "rgba(0,0,0,.04)" }} />
              <Bar dataKey="amount" fill="#1f6b4a" radius={[4, 4, 0, 0]} />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>
      {modal && (
        <Modal title="Withdraw from savings" onClose={() => setModal(false)}>
          <p className="muted small">Available: {money(user?.savings)}</p>
          <form className="form" onSubmit={withdraw}>
            <label>Title<input value={title} onChange={(e) => setTitle(e.target.value)} /></label>
            <label>Amount<input type="number" step="0.01" min="0" value={amount} onChange={(e) => setAmount(e.target.value)} placeholder="0.00" /></label>
            <p className="muted small">The amount moves back into your main balance.</p>
            {mErr && <p className="error">{mErr}</p>}
            <div className="modal-actions">
              <button type="button" className="btn ghost" onClick={() => setModal(false)}>Cancel</button>
              <button className="btn yellow">Withdraw</button>
            </div>
          </form>
        </Modal>
      )}
    </>
  );
}
