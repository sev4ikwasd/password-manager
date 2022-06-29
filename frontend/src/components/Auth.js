import React from "react"
import {Navigate, useLocation} from "react-router-dom"
import {API} from "./API";

const AuthContext = React.createContext(null);

function AuthProvider({children}) {
  const [token, setToken] = React.useState(null);

  function login(user) {
    return API.login(user).then(result => {
      setToken(result.data.authToken);
    });
  }

  function logout() {
    setToken(null);
  }

  const value = {
    token,
    login,
    logout
  };

  return <AuthContext.Provider value={value}>
    {children}
  </AuthContext.Provider>
}

function useAuth() {
  return React.useContext(AuthContext);
}

function RequireAuth({children}) {
  const {token} = useAuth();
  const location = useLocation();

  if (!token) {
    return <Navigate to="/login" state={{from: location}} replace/>;
  }

  return children;
}

export {AuthProvider, RequireAuth, useAuth}