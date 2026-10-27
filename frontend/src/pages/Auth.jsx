import { useState } from "react";
import { useSearchParams, useNavigate, Link } from "react-router-dom";
import { api } from "../api.js";
import { useAuth } from "../App.jsx";

export default function Auth() {
  const [params, setParams] = useSearchParams();
  const mode = params.get("mode") === "signup" ? "signup" : "login";
  const { login } = useAuth();
  const nav = useNavigate();
  const [f, setF] = useState({ name: "", email: "", password: "", confirm: "" });
  const [error, setError] = useState("");
  const [busy, setBusy] = useState(false);
  const set = (k) => (e) => setF({ ...f, [k]: e.target.value });

  const submit = async (e) => {
    e.preventDefault();
    setError("");
    if (f.password.length < 8) return setError("Password must be at least 8 characters");
    if (mode === "signup" && f.password !== f.confirm) return setError("Passwords don't match");
    setBusy(true);
    try {
      if (mode === "signup") await api.register(f.name, f.email, f.password);
      const res = await api.login(f.email, f.password);
      login(res);
      nav("/dashboard");
    } catch (err) {
      setError(err.message);
    } finally {
      setBusy(false);
    }
  };

  return (
    <div className="auth">
      <div className="auth-side dollars">
        <Link to="/" className="display auth-title">Expense Tracker</Link>
        <p>See where your money goes, month by month.</p>
      </div>
      <div className="auth-main">
        <div className="card auth-card">
          <div className="tabs">
            <button className={mode === "login" ? "active" : ""} onClick={() => setParams({ mode: "login" })}>Log in</button>
            <button className={mode === "signup" ? "active" : ""} onClick={() => setParams({ mode: "signup" })}>Sign up</button>
          </div>
          <form onSubmit={submit} className="form">
            {mode === "signup" && <label>Name<input value={f.name} onChange={set("name")} required maxLength={50} /></label>}
            <label>Email<input type="email" value={f.email} onChange={set("email")} required placeholder="you@example.com" /></label>
            <label>Password<input type="password" value={f.password} onChange={set("password")} required /></label>
            {mode === "signup" && <label>Confirm password<input type="password" value={f.confirm} onChange={set("confirm")} required /></label>}
            {error && <p className="error">{error}</p>}
            <button className="btn yellow full" disabled={busy}>
              {busy ? "Please wait..." : mode === "login" ? "Log in" : "Create account"}
            </button>
          </form>
          <p className="switch">
            {mode === "login" ? "Don't have an account? " : "Already have an account? "}
            <button className="link" onClick={() => setParams({ mode: mode === "login" ? "signup" : "login" })}>
              {mode === "login" ? "Sign up" : "Log in"}
            </button>
          </p>
        </div>
      </div>
    </div>
  );
}
