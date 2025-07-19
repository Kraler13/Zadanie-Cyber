import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

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
    <form onSubmit={handleSubmit}>
      <h2>Logowanie</h2>
      <input name="username" value={form.username} onChange={handleChange} placeholder="Nazwa użytkownika" />
      <input name="password" type="password" value={form.password} onChange={handleChange} placeholder="Hasło" />
      <button type="submit">Zaloguj</button>
      <p>Nie masz konta? <Link to="/register">Zarejestruj się</Link></p>
    </form>
  );
}

export default Login;