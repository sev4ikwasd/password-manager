import {Alert, Avatar, Box, Button, Container, Link, Snackbar, TextField, Typography} from "@mui/material";
import {LockOutlined} from "@mui/icons-material";
import {useAuth} from "./Auth";
import {useLocation, useNavigate} from "react-router-dom";
import {useState} from "react";
import {API} from "./API";

function Signup() {
  const [errorState, setErrorState] = useState({
    errorOpen: false,
    errorMessage: "",
  });

  const auth = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  function handleSubmit(event) {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    API.signup({
      username: data.get("username"),
      password: data.get("password")
    }).then(() => {
      auth.login({
        username: data.get("username"),
        password: data.get("password")
      }).then(() => {
        const origin = location.state?.from?.pathname || "/";
        navigate(origin, {replace: true});
      })
    }, (reason) => {
      if (reason.response.data.code === "USER_EXISTS_ERROR") {
        handleError("User with given username already exists!");
      }
      else {
        handleError("Unknown server error occurred!");
        console.log(reason);
      }
    });
  }

  const handleError = (message) => {
    setErrorState({
      errorMessage: message,
      errorOpen: true,
    })
  };

  const handleErrorClose = () => {
    setErrorState({
      errorMessage: "",
      errorOpen: false,
    })
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
          Sign up
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
            autoFocus/>
          <TextField
            margin="normal"
            required
            fullWidth
            id="password"
            label="Password"
            name="password"
            autoComplete="current-password"/>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{mt: 3, mb: 2}}>
            Sign up
          </Button>
          <Link href="/login" variant="body2" display="flex" justifyContent="flex-end">
            {"Already have an account? Log in"}
          </Link>
        </Box>
      </Box>
      <Snackbar open={errorState.errorOpen} autoHideDuration={6000} onClose={handleErrorClose}>
        <Alert onClose={handleErrorClose} severity="error" sx={{width: '100%'}}>
          {errorState.errorMessage}
        </Alert>
      </Snackbar>
    </Container>
  )
}

export default Signup;
