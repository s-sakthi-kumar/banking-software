import { MongoMemoryServer } from 'mongodb-memory-server';
let mongoServer;

async function startServer() {
  try {
    // Start the local database engine
    mongoServer = await MongoMemoryServer.create();
    const mongoUri = mongoServer.getUri();
    
    console.log(`local In-Memory DB at: ${mongoUri}`);
    // Connect Mongoose to it
    // await mongoose.connect(mongoUri);

    // Handle clean shutdowns (Ctrl+C)
    process.on('SIGINT', async () => {
      console.log('\nShutting down gracefully...');
      // server.close();
      // await mongoose.disconnect();
      // await mongoServer.stop();
      console.log('Database and server stopped.');
      process.exit(0);
    });

  } catch (error) {
    console.error('Startup failed:', error);
    process.exit(1);
  }
}

startServer();

