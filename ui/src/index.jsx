import React, { useState, useEffect } from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import App2 from "./App2.jsx";
import Login from "./Login.jsx";
import Register from "./Register.jsx";
import "./styles.css";

function Root() {
  const [token, setToken] = useState(null);
  const [userId, setUserId] = useState(null);

  // Check localStorage on mount
  useEffect(() => {
    const savedToken = localStorage.getItem("access_token");
    const savedUserId = localStorage.getItem("user_id");
    if (savedToken && savedUserId) {
      setToken(savedToken);
      setUserId(savedUserId);
    }
  }, []);

  const handleLogin = (accessToken, userId) => {
    setToken(accessToken);
    setUserId(userId);
  };

  const handleLogout = () => {
    localStorage.removeItem("access_token");
    localStorage.removeItem("user_id");
    setToken(null);
    setUserId(null);
  };

  return (
    <BrowserRouter>
      <Routes>
        {token && userId ? (
          <Route
            path="/*"
            element={<App2 token={token} userId={userId} onLogout={handleLogout} />}
          />
        ) : (
          <>
            <Route path="/login" element={<Login onLogin={handleLogin} />} />
            <Route path="/register" element={<Register />} />
            <Route path="*" element={<Login onLogin={handleLogin} />} />
          </>
        )}
      </Routes>
    </BrowserRouter>
  );
}

ReactDOM.createRoot(document.getElementById("root")).render(<Root />);
