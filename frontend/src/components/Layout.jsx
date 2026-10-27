import { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../App.jsx";

const links = [
  { to: "/dashboard", label: "Dashboard" },
  { to: "/history", label: "History" },
  { to: "/savings", label: "Savings" },
];

export default function Layout({ children }) {
  const { user, logout } = useAuth();
  const [open, setOpen] = useState(false);
  const nav = useNavigate();
  const initial = (user?.name || "?").charAt(0).toUpperCase();

  return (
    <div className="app">
      <header className="topbar">
        <span className="brand">expense tracker</span>
        <nav className="toplinks">
          {links.map((l) => (
            <NavLink key={l.to} to={l.to}>{l.label}</NavLink>
          ))}
        </nav>
        <div className="usermenu">
          <button className="userbtn" onClick={() => setOpen(!open)}>
            <span className="avatar">{initial}</span>
            <span className="hide-sm">{user?.name}</span> ▾
          </button>
          {open && (
            <div className="dropdown" onMouseLeave={() => setOpen(false)}>
              <div className="dropdown-email">{user?.email}</div>
              <button onClick={() => { logout(); nav("/"); }}>Log out</button>
            </div>
          )}
        </div>
      </header>
      <main className="content">{children}</main>
      <nav className="bottomnav">
        {links.map((l) => (
          <NavLink key={l.to} to={l.to}>{l.label}</NavLink>
        ))}
      </nav>
    </div>
  );
}
