import {
  Alert,
  Avatar,
  Box,
  Button,
  Checkbox,
  Container,
  FormControlLabel,
  Link,
  Snackbar,
  TextField,
  Typography
} from "@mui/material";
import {LockOutlined} from "@mui/icons-material";
import {useAuth} from "./Auth";
import {useLocation, useNavigate} from "react-router-dom";
import {useState} from "react";

function Login() {
  const [username] = useState(() => {
    return localStorage.getItem("username") || undefined;
  });
  const [remember] = useState(() => {
    return Boolean(localStorage.getItem("remember"));
  });
  const [errorOpen, setErrorOpen] = useState(false);
  const auth = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  function handleSubmit(event) {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    auth.login({
      username: data.get("username"),
      password: data.get("password")
    }).then(() => {
      const origin = location.state?.from?.pathname || "/";
      if (data.get("remember")) {
        localStorage.setItem("username", data.get("username"));
        localStorage.setItem("remember", true);
      } else {
        localStorage.removeItem("username");
        localStorage.removeItem("remember");
      }
      navigate(origin, {replace: true});
    }, () => {
      handleError();
    });
  }

  const handleError = () => {
    setErrorOpen(true);
  };

  const handleErrorClose = () => {
    setErrorOpen(false);
  };

  return (
    <Container maxWidth="xs">
      <Box
        sx={{
          mt: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Avatar sx={{m: 1, bgcolor: "secondary.main"}}>
          <LockOutlined/>
        </Avatar>
        <Typography component="h1" variant="h5">
          Log in
        </Typography>
        <Box component="form" onSubmit={handleSubmit} sx={{mt: 1}}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="username"
            label="Username"
            name="username"
            autoComplete="username"
            autoFocus
            value={username}/>
          <TextField
            margin="normal"
            required
            fullWidth
            id="password"
            label="Password"
            name="password"
            autoComplete="current-password"/>
          <FormControlLabel control={<Checkbox id="remember" name="remember" color="primary" defaultChecked={remember}/>}
                            label="Remember username"
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{mt: 3, mb: 2}}>
            Log in
          </Button>
          <Link href="/signup" variant="body2" display="flex" justifyContent="flex-end">
            {"Don't have an account? Sign Up"}
          </Link>
        </Box>
      </Box>
      <Snackbar open={errorOpen} autoHideDuration={6000} onClose={handleErrorClose}>
        <Alert onClose={handleErrorClose} severity="error" sx={{width: '100%'}}>
          Given username or password is wrong!
        </Alert>
      </Snackbar>
    </Container>
  )
}

export default Login;