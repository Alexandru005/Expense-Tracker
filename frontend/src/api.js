const KEY = "et_auth";

export function getAuth() {
  try {
    return JSON.parse(localStorage.getItem(KEY));
  } catch {
    return null;
  }
}
export function setAuth(a) {
  if (a) localStorage.setItem(KEY, JSON.stringify(a));
  else localStorage.removeItem(KEY);
}

async function request(path, { method = "GET", body, params } = {}) {
  const auth = getAuth();
  let url = path;
  if (params) {
    const q = new URLSearchParams();
    Object.entries(params).forEach(([k, v]) => v != null && v !== "" && q.append(k, v));
    url += "?" + q.toString();
  }
  const res = await fetch(url, {
    method,
    headers: {
      "Content-Type": "application/json",
      ...(auth?.token ? { Authorization: `Bearer ${auth.token}` } : {}),
    },
    body: body ? JSON.stringify(body) : undefined,
  });
  if (res.status === 401) {
    if (auth?.token) {
      setAuth(null);
      window.location.href = "/auth";
    }
    throw new Error("Invalid email or password");
  }
  if (!res.ok) {
    let msg = "Something went wrong";
    try {
      const d = await res.json();
      msg = d.message || d.error || Object.values(d)[0] || msg;
    } catch {}
    throw new Error(msg);
  }
  const text = await res.text();
  return text ? JSON.parse(text) : null;
}

export const api = {
  login: (email, password) => request("/api/auth/login", { method: "POST", body: { email, password } }),
  register: (name, email, password) =>
    request("/api/auth/register", { method: "POST", body: { name, email, password } }),
  addTransaction: (t) => request("/api/transactions", { method: "POST", body: t }),
  transactions: (year, month) => request("/api/transactions/list", { params: { year, month } }),
  savings: () => request("/api/transactions/savings/list"),
  expenseChart: (year, month) => request("/api/transactions/chart/expenses", { params: { year, month } }),
  savingsChart: (year) => request("/api/transactions/chart/savings/yearly", { params: { year } }),
};

export const CATEGORIES = [
  "EDUCATION", "ENTERTAINMENT", "FOOD", "HEALTH", "OTHER", "RENT",
  "SHOPPING", "SUBSCRIPTION", "TRANSPORT", "TRAVEL", "UTILITIES",
];
export const MONTHS = ["January","February","March","April","May","June","July","August","September","October","November","December"];
export const CHART_COLORS = ["#1f6b4a", "#f2c94c", "#3a7ca5", "#c4553a", "#7fb685", "#8a6fb0", "#e8964a", "#4a9c9c", "#b5838d", "#6d6875", "#9bb53a", "#555"];

export const money = (n) =>
  "$" + Number(n || 0).toLocaleString("en-US", { minimumFractionDigits: 0, maximumFractionDigits: 2 });
export const label = (s) => (s ? s.charAt(0) + s.slice(1).toLowerCase().replace(/_/g, " ") : "");
export const fmtDate = (d) =>
  d ? new Date(d).toLocaleDateString("en-US", { month: "short", day: "numeric", year: "numeric" }) : "";
export const isPositive = (type) => type === "INCOME" || type === "SAVINGS_WITHDRAWAL";
