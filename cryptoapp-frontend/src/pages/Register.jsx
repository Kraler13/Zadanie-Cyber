import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

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
    <form onSubmit={handleSubmit}>
      <h2>Rejestracja</h2>
      <input name="username" value={form.username} onChange={handleChange} placeholder="Nazwa użytkownika" />
      <input name="password" type="password" value={form.password} onChange={handleChange} placeholder="Hasło" />
      <button type="submit">Zarejestruj</button>
      <p>Masz już konto? <Link to="/login">Zaloguj się</Link></p>
    </form>
    
  );
}

export default Register;
