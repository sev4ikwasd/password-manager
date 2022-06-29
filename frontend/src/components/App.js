import React from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from "./Login";
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";
import {AuthProvider, RequireAuth} from "./Auth";
import NavBar from "./NavBar";
import Passwords from "./Passwords";
import Generator from "./Generator";
import Signup from "./Signup";

function App() {
  const theme = createTheme();

  return (
    <AuthProvider>
      <ThemeProvider theme={theme}>
        <CssBaseline/>
        <BrowserRouter>
          <Routes>
            <Route path="/login" element={<Login/>}/>
            <Route path="/signup" element={<Signup/>}/>
            <Route path="/" element={
              <RequireAuth>
                <NavBar/>
              </RequireAuth>
            }>
              <Route index element={<Passwords/>}/>
              <Route path="passwords" element={<Passwords/>}/>
              <Route path="generator" element={<Generator/>}/>
            </Route>
          </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </AuthProvider>
  );
}

export default App;
