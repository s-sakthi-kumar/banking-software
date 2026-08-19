import express from "express";
import Task from "../models/Task.js";
import auth from "../middleware/auth.js";

const router = express.Router();

// CREATE TASK
router.post("/tasks", auth, async (req, res) => {
    try {
        const { title, description } = req.body;

        const task = await Task.create({
            title,
            description,
            user: req.userId
        });

        res.status(201).json(task);
    } catch (error) {
        res.status(400).json({
            error: error.message
        });
    }
});


// GET ALL MY TASKS
router.get("/tasks", auth, async (req, res) => {
    try {
        const {
            completed,
            page = 1,
            limit = 10,
            sort = "created_at",
            order = "desc"
        } = req.query;

        // Filtering
        const filter = {
            user: req.userId
        };

        if (completed !== undefined) {
            filter.completed = completed === "true";
        }

        // Pagination
        const pageNumber = Number(page);
        const limitNumber = Number(limit);
        const skip = (pageNumber - 1) * limitNumber;

        // Sorting
        const sortOrder = order === "asc" ? 1 : -1;

        const tasks = await Task.find(filter)
            .sort({ [sort]: sortOrder })
            .skip(skip)
            .limit(limitNumber);

        const total = await Task.countDocuments(filter);

        res.json({
            page: pageNumber,
            limit: limitNumber,
            total,
            totalPages: Math.ceil(total / limitNumber),
            tasks
        });

    } catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
});


// GET ONE TASK
router.get("/tasks/:id", auth, async (req, res) => {
    try {
        const task = await Task.findOne({
            _id: req.params.id,
            user: req.userId
        });

        if (!task) {
            return res.status(404).json({
                message: "Task not found"
            });
        }

        res.json(task);
    } catch (error) {
        res.status(400).json({
            error: error.message
        });
    }
});


// UPDATE TASK
router.patch("/tasks/:id", auth, async (req, res) => {
    try {
        const task = await Task.findOneAndUpdate(
            {
                _id: req.params.id,
                user: req.userId
            },
            req.body,
            {
                new: true,
                runValidators: true
            }
        );

        if (!task) {
            return res.status(404).json({
                message: "Task not found"
            });
        }

        res.json(task);
    } catch (error) {
        res.status(400).json({
            error: error.message
        });
    }
});


// DELETE TASK
router.delete("/tasks/:id", auth, async (req, res) => {
    try {
        const task = await Task.findOneAndDelete({
            _id: req.params.id,
            user: req.userId
        });

        if (!task) {
            return res.status(404).json({
                message: "Task not found"
            });
        }

        res.json({
            message: "Task deleted successfully"
        });
    } catch (error) {
        res.status(400).json({
            error: error.message
        });
    }
});

export default router;
