import { Link } from "react-router-dom";

export default function Home() {
  return (
    <div className="hero dollars">
      <div className="hero-inner">
        <h1 className="display">Expense Tracker</h1>
        <p>See where your money goes, month by month.</p>
        <div className="hero-actions">
          <Link to="/auth?mode=login" className="btn yellow">Log in</Link>
          <Link to="/auth?mode=signup" className="btn outline">Sign up</Link>
        </div>
      </div>
    </div>
  );
}
