import mongoose from 'mongoose';

async function connectDB() {
    await mongoose.connect("mongodb://127.0.0.1:46663/");
    console.log("MongoDB connected");
}

export default connectDB;
