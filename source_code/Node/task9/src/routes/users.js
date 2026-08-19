import express from "express";
import User from "../models/User.js";
import jwt from "jsonwebtoken";
import bcrypt from "bcryptjs";



const router = express.Router();

router.get("/users", async (req, res) => {
    const users = await User.find();
    res.json(users);
});

router.post("/users", async (req, res) => {
    try {
        const { name, email, password } = req.body;

        const user = new User({
            name,
            email,
            password
        });

        await user.save();

        res.status(201).json({
            message: "User registered successfully",
            user
        });
    } catch (error) {
        res.status(400).json({
            error: error.message

        });

    }
});


router.post("/login", async (req, res) => {
    try {
        const { email, password } = req.body;

        const user = await User.findOne({ email });

        if (!user) {
            return res.status(401).json({
                message: "Invalid email or password"
            });
        }

        const validPassword = await bcrypt.compare(
            password,
            user.password

        );

        if (!validPassword) {
            return res.status(401).json({
                message: "Invalid email or password"
            });
        }

        const token = jwt.sign(

            { userId: user._id },
            process.env.JWT_SECRET,
            { expiresIn: "1h" }
        );

        res.json({
            message: "Login successful",

            token
        });

    } catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
});


export default router;
