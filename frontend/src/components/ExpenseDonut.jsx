import { PieChart, Pie, Cell, ResponsiveContainer, Tooltip } from "recharts";
import { CHART_COLORS, label, money } from "../api.js";

export default function ExpenseDonut({ data }) {
  if (!data.length) return <p className="empty">No expenses to show.</p>;
  const d = data.map((x) => ({ ...x, amount: Number(x.amount), name: label(x.category) }));
  return (
    <div className="donut">
      <div className="donut-chart">
        <ResponsiveContainer width="100%" height={200}>
          <PieChart>
            <Pie data={d} dataKey="amount" nameKey="name" innerRadius={50} outerRadius={85} paddingAngle={2}>
              {d.map((_, i) => <Cell key={i} fill={CHART_COLORS[i % CHART_COLORS.length]} />)}
            </Pie>
            <Tooltip formatter={(v) => money(v)} />
          </PieChart>
        </ResponsiveContainer>
      </div>
      <ul className="legend">
        {d.map((x, i) => (
          <li key={i}>
            <span className="dot" style={{ background: CHART_COLORS[i % CHART_COLORS.length] }} />
            {x.name} · {Number(x.percentage).toFixed(0)}%
          </li>
        ))}
      </ul>
    </div>
  );
}
