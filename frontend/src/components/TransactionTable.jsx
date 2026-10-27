import { useState } from "react";
import { money, label, fmtDate, isPositive } from "../api.js";

export default function TransactionTable({ rows, pageSize = 6 }) {
  const [page, setPage] = useState(0);
  const pages = Math.max(1, Math.ceil(rows.length / pageSize));
  const p = Math.min(page, pages - 1);
  const shown = rows.slice(p * pageSize, p * pageSize + pageSize);

  if (!rows.length) return <p className="empty">No transactions in this period.</p>;

  return (
    <>
      <table className="table">
        <thead>
          <tr>
            <th>Title</th>
            <th className="hide-sm">Category</th>
            <th className="hide-sm">Date</th>
            <th className="right">Amount</th>
          </tr>
        </thead>
        <tbody>
          {shown.map((t, i) => (
            <tr key={i}>
              <td>
                <div className="t-title">{t.title}</div>
                <div className="t-sub">{label(t.category)} · {fmtDate(t.date)}</div>
              </td>
              <td className="hide-sm"><span className="pill">{label(t.category)}</span></td>
              <td className="hide-sm">{fmtDate(t.date)}</td>
              <td className={"right " + (isPositive(t.type) ? "pos" : "neg")}>
                {isPositive(t.type) ? "+" : "-"}{money(t.amount)}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {pages > 1 && (
        <div className="pager">
          <button disabled={p === 0} onClick={() => setPage(p - 1)}>‹</button>
          <span>{p + 1} / {pages}</span>
          <button disabled={p >= pages - 1} onClick={() => setPage(p + 1)}>›</button>
        </div>
      )}
    </>
  );
}
