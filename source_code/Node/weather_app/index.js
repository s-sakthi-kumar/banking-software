import express from 'express';
import mongoose from 'mongoose';
import { MongoMemoryServer } from 'mongodb-memory-server';

const app = express();
app.use(express.json()); // Allows Express to read JSON body data

let mongoServer;

// --- 1. Mongoose Schema & Model ---
const ItemSchema = new mongoose.Schema({
  name: { type: String, required: true },
  quantity: { type: Number, default: 1 }
});
const Item = mongoose.model('Item', ItemSchema);

// --- 2. Express Routes ---

// GET: Fetch all items
app.get('/items', async (req, res) => {
  try {
    const items = await Item.find();
    res.json(items);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// POST: Add a new item
app.post('/items', async (req, res) => {
  try {
    const newItem = new Item({
      name: req.body.name,
      quantity: req.body.quantity
    });
    await newItem.save();
    res.status(201).json(newItem);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

// --- 3. Server Startup Lifecycle ---
async function startServer() {
  try {
    // Start the local database engine
    mongoServer = await MongoMemoryServer.create();
    const mongoUri = mongoServer.getUri();
    
    // Connect Mongoose to it
    await mongoose.connect(mongoUri);
    console.log(` Connected to local In-Memory DB at: ${mongoUri}`);

    // Start the Express web server
    const PORT = 3000;
    const server = app.listen(PORT, () => {
      console.log(` Express app is running on http://localhost:${PORT}`);
    });

    // Handle clean shutdowns (Ctrl+C)
    process.on('SIGINT', async () => {
      console.log('\nShutting down gracefully...');
      server.close();
      await mongoose.disconnect();
      await mongoServer.stop();
      console.log('Database and server stopped.');
      process.exit(0);
    });

  } catch (error) {
    console.error('Startup failed:', error);
    process.exit(1);
  }
}

startServer();
