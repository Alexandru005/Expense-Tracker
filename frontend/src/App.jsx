import { createContext, useContext, useState } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import { getAuth, setAuth } from "./api.js";
import Home from "./pages/Home.jsx";
import Auth from "./pages/Auth.jsx";
import Dashboard from "./pages/Dashboard.jsx";
import History from "./pages/History.jsx";
import Savings from "./pages/Savings.jsx";
import Layout from "./components/Layout.jsx";

const AuthCtx = createContext(null);
export const useAuth = () => useContext(AuthCtx);

export default function App() {
  const [user, setUser] = useState(getAuth());
  const save = (u) => {
    setAuth(u);
    setUser(u);
  };
  // Actualizeaza local soldul dupa o tranzactie (backend-ul nu are endpoint "me").
  const applyTransaction = (type, amount) => {
    const a = Number(amount);
    const u = { ...user, balance: Number(user.balance || 0), savings: Number(user.savings || 0) };
    if (type === "INCOME") u.balance += a;
    if (type === "EXPENSE") u.balance -= a;
    if (type === "SAVINGS_DEPOSIT") { u.balance -= a; u.savings += a; }
    if (type === "SAVINGS_WITHDRAWAL") { u.balance += a; u.savings -= a; }
    save(u);
  };

  const Private = ({ children }) => (user ? <Layout>{children}</Layout> : <Navigate to="/auth" replace />);

  return (
    <AuthCtx.Provider value={{ user, login: save, logout: () => save(null), applyTransaction }}>
      <Routes>
        <Route path="/" element={user ? <Navigate to="/dashboard" /> : <Home />} />
        <Route path="/auth" element={user ? <Navigate to="/dashboard" /> : <Auth />} />
        <Route path="/dashboard" element={<Private><Dashboard /></Private>} />
        <Route path="/history" element={<Private><History /></Private>} />
        <Route path="/savings" element={<Private><Savings /></Private>} />
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </AuthCtx.Provider>
  );
}
