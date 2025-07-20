import { useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

function Register() {
  const [form, setForm] = useState({ username: "", password: "" });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8081/api/auth/register", form);
      navigate("/login");
    } catch (err) {
      alert("Rejestracja nieudana");
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Rejestracja</h2>
      <form onSubmit={handleSubmit} className="w-100" style={{ maxWidth: "400px" }}>
        <div className="mb-3">
          <input className="form-control" name="username" value={form.username} onChange={handleChange} placeholder="Nazwa użytkownika" />
        </div>
        <div className="mb-3">
          <input className="form-control" name="password" type="password" value={form.password} onChange={handleChange} placeholder="Hasło" />
        </div>
        <button className="btn btn-success w-100" type="submit">Zarejestruj</button>
        <p className="mt-3 text-center">Masz już konto? <Link to="/login">Zaloguj się</Link></p>
      </form>
    </div>
  );
}

export default Register;
