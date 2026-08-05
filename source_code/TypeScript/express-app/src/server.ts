import express from "express";

const app = express();

const PORT = 3000;

app.get("/", (req, res) => {
  res.send("Hello world");
});

app.listen(PORT,"0.0.0.0", () => {
  console.log(`Server running at http://localhost:${PORT}`);
});



