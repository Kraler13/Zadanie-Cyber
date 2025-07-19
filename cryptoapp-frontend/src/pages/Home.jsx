import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Home() {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [decrypted, setDecrypted] = useState({});
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  const axiosInstance = axios.create({
    baseURL: "http://localhost:8081/api",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  const fetchMessages = async () => {
    try {
      const res = await axiosInstance.get("/crypto/all");
      setMessages(res.data);
    } catch (err) {
      alert("Sesja wygasła. Zaloguj się ponownie.");
      navigate("/login");
    }
  };

  useEffect(() => {
    fetchMessages();
  }, []);

  const handleEncrypt = async () => {
    if (!newMessage) return;
    try {
      await axiosInstance.post("/crypto/encrypt", { message: newMessage });
      setNewMessage("");
      fetchMessages();
    } catch (err) {
      alert("Błąd przy szyfrowaniu wiadomości.");
    }
  };

  const handleDecrypt = async (id, encrypted) => {
    try {
      const res = await axiosInstance.post("/crypto/decrypt", { encrypted });
      setDecrypted((prev) => ({ ...prev, [id]: res.data }));
    } catch (err) {
      alert("Błąd przy deszyfrowaniu.");
    }
  };

  const handleDelete = async (id) => {
    try {
      await axiosInstance.delete(`/crypto/${id}`);
      fetchMessages();
    } catch (err) {
      alert("Nie udało się usunąć wiadomości.");
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <div className="container py-5">
      <div className="d-flex justify-content-between mb-4">
        <h2>Witaj!</h2>
        <button className="btn btn-outline-danger" onClick={handleLogout}>
          Wyloguj
        </button>
      </div>

      <div className="input-group mb-3">
        <input
          className="form-control"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          placeholder="Wpisz wiadomość do zaszyfrowania"
        />
        <button className="btn btn-primary" onClick={handleEncrypt}>
          Zaszyfruj
        </button>
      </div>

      <h4 className="mb-3">Twoje wiadomości:</h4>
      <div className="row">
        {messages.map((msg) => (
          <div key={msg.id} className="col-md-6 mb-3">
            <div className="card p-3">
              <p><strong>Zaszyfrowana:</strong> {msg.encrypted}</p>
              {decrypted[msg.id] && (
                <p><strong>Odszyfrowana:</strong> {decrypted[msg.id]}</p>
              )}
              <div className="d-flex gap-2">
                <button
                  className="btn btn-sm btn-success"
                  onClick={() => handleDecrypt(msg.id, msg.encrypted)}
                >
                  Odszyfruj
                </button>
                <button
                  className="btn btn-sm btn-danger"
                  onClick={() => handleDelete(msg.id)}
                >
                  Usuń
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Home;
