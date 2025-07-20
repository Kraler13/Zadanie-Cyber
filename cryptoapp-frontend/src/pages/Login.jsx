import { useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

function Login() {
  const [form, setForm] = useState({ username: "", password: "" });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post("http://localhost:8081/api/auth/login", form);
      localStorage.setItem("token", res.data.token);
      navigate("/home");
    } catch (err) {
      alert("Niepoprawne dane logowania");
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Logowanie</h2>
      <form onSubmit={handleSubmit} className="w-100" style={{ maxWidth: "400px" }}>
        <div className="mb-3">
          <input className="form-control" name="username" value={form.username} onChange={handleChange} placeholder="Nazwa użytkownika" />
        </div>
        <div className="mb-3">
          <input className="form-control" name="password" type="password" value={form.password} onChange={handleChange} placeholder="Hasło" />
        </div>
        <button className="btn btn-primary w-100" type="submit">Zaloguj</button>
        <p className="mt-3 text-center">Nie masz konta? <Link to="/register">Zarejestruj się</Link></p>
      </form>
    </div>
  );
}

export default Login;
