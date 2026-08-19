import express from "express";
import connectDB from "./db/mongoose.js";
import "dotenv/config";

import userRoutes from "./routes/users.js";
import taskRoutes from "./routes/tasks.js";

const app = express();

app.use(express.json());

await connectDB();

app.use("/api", userRoutes);
app.use("/api", taskRoutes);

app.listen(3000, () => {
    console.log("Server running on port 3000");
});
