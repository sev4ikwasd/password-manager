import {
  Box,
  Button,
  Checkbox,
  Container,
  Divider,
  FormControlLabel,
  Slider,
  TextField,
  Typography
} from "@mui/material";
import {useEffect, useState} from "react";

const upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
const lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
const numbers = "1234567890";
const specialSymbols = "!@#$%^&*";

function Generator() {
  const [password, setPassword] = useState(null);
  const [length, setLength] = useState(14);
  const [upperCaseLettersChecked, setUpperCaseLettersChecked] = useState(true);
  const [lowerCaseLettersChecked, setLowerCaseLettersChecked] = useState(true);
  const [numbersChecked, setNumbersChecked] = useState(true);
  const [specialSymbolsChecked, setSpecialSymbolsChecked] = useState(false);

  useEffect(() => {
    if (!upperCaseLettersChecked
      && !lowerCaseLettersChecked
      && !numbersChecked
      && !specialSymbolsChecked) {
      setLowerCaseLettersChecked(true);
    }
    generateNewPassword();
  }, [length, upperCaseLettersChecked, lowerCaseLettersChecked, numbersChecked, specialSymbolsChecked]);

  function generateNewPassword() {
    const options = [upperCaseLettersChecked, lowerCaseLettersChecked, numbersChecked, specialSymbolsChecked]
    const checkedOptions = options.filter((o) => o);
    if (checkedOptions.length === 0)
      return;
    let newPassword = "";
    for (let i = 0; i < length; i++) {
      let choseOption = false;
      let option;
      while (!choseOption) {
        option = Math.floor(Math.random() * options.length);
        if (options[option]) {
          choseOption = true;
        }
      }

      let letter;
      switch (option) {
        case 0:
          letter = upperCaseLetters[Math.floor(Math.random() * upperCaseLetters.length)];
          break;
        case 1:
          letter = lowerCaseLetters[Math.floor(Math.random() * lowerCaseLetters.length)];
          break;
        case 2:
          letter = numbers[Math.floor(Math.random() * numbers.length)];
          break;
        case 3:
          letter = specialSymbols[Math.floor(Math.random() * specialSymbols.length)];
          break;
      }
      newPassword += letter;
    }

    setPassword(newPassword);
  }

  return (
    <Container maxWidth="md">
      <Box sx={{
        mt: 8,
        display: "flex",
        flexDirection: "column",
        alignItems: "left",
      }}>
        <Typography variant="h5">
          Generator
        </Typography>
        <Divider/>
        <Typography sx={{
          textAlign: "center",
          borderColor: "primary.dark",
          borderWidth: 1,
          borderStyle: "solid",
          borderRadius: 1,
          px: 1,
          py: 1,
          mt: 2,
        }}>
          {password}
        </Typography>
        <Box sx={{
          mt: 2,
          display: "flex",
          flexDirection: "column",
          alignItems: "left",
        }}>
          <Box sx={{mt: 1}}>
            <Typography>Length</Typography>
            <Slider
              min={1} max={60} valueLabelDisplay="auto"
              value={length}
              onChange={(e) => setLength(e.target.value)}/>
          </Box>
          <FormControlLabel control={<Checkbox name="upper-case-letters"
                                               checked={upperCaseLettersChecked}
                                               onChange={(e) => setUpperCaseLettersChecked(e.target.checked)}/>}
                            label="A-Z"/>
          <FormControlLabel control={<Checkbox name="lower-case-letters"
                                               checked={lowerCaseLettersChecked}
                                               onChange={(e) => setLowerCaseLettersChecked(e.target.checked)}/>}
                            label="a-z"/>
          <FormControlLabel control={<Checkbox name="numbers"
                                               checked={numbersChecked}
                                               onChange={(e) => setNumbersChecked(e.target.checked)}/>}
                            label="0-9"/>
          <FormControlLabel control={<Checkbox name="special-symbols"
                                               checked={specialSymbolsChecked}
                                               onChange={(e) => setSpecialSymbolsChecked(e.target.checked)}/>}
                            label="!@#$%^&*"/>
          <Box sx={{mt: 1}}>
            <Button variant="contained" onClick={generateNewPassword} sx={{
              mr: 2,
            }}>
              Generate new password
            </Button>
            <Button variant="outlined" onClick={() => navigator.clipboard.writeText(password)}>
              Copy password
            </Button>
          </Box>
        </Box>
      </Box>
    </Container>
  );
}

export default Generator;