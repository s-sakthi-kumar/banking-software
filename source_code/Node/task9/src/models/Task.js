import mongoose from "mongoose";

const taskSchema = new mongoose.Schema({
    title: {
        type: String,
        required: true
    },
    description: {
        type: String,
        default: ""
    },
    completed: {
        type: Boolean,
        default: false
    },
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "User",
        required: true
    },
    updated_at: {
        type: Date,
        default: Date.now
    }
}, {
    timestamps: true
});

taskSchema.pre("save", async function() {
    this.updated_at = new Date();
});

taskSchema.pre("findOneAndUpdate", function() {
    this.set({
        updated_at: new Date()
    });
});

const Task = mongoose.model("Task", taskSchema);

export default Task;
