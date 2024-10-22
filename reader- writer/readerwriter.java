import java.util.concurrent.Semaphore;

class ReaderWriterProblem {
    private static int readCount = 0; // Count of readers currently accessing the shared resource
    private static final Semaphore resource = new Semaphore(1);  // Semaphore to give exclusive access to writers
    private static final Semaphore readCountAccess = new Semaphore(1);  // Semaphore to protect readCount variable
    private static final Semaphore serviceQueue = new Semaphore(1);  // Semaphore to manage the queue order

    // Reader class
    static class Reader implements Runnable {
        private final int readerId;

        Reader(int readerId) {
            this.readerId = readerId;
        }

        @Override
        public void run() {
            try {
                // Reader trying to read the shared resource
                serviceQueue.acquire();   // Ensure fair order of arriving readers and writers
                readCountAccess.acquire(); // Reader is modifying the readCount
                if (readCount == 0) {
                    resource.acquire(); // If this is the first reader, lock the resource for reading
                }
                readCount++;
                readCountAccess.release(); // Done modifying readCount
                serviceQueue.release();   // Allow other readers or writers to proceed

                // Simulate reading the resource
                System.out.println("Reader " + readerId + " is reading.");
                Thread.sleep(1000); // Simulate time taken to read
                System.out.println("Reader " + readerId + " finished reading.");

                // After reading
                readCountAccess.acquire(); // Reader is modifying the readCount
                readCount--;
                if (readCount == 0) {
                    resource.release(); // If no more readers, release the resource for writers
                }
                readCountAccess.release(); // Done modifying readCount
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Writer class
    static class Writer implements Runnable {
        private final int writerId;

        Writer(int writerId) {
            this.writerId = writerId;
        }

        @Override
        public void run() {
            try {
                // Writer trying to write to the shared resource
                serviceQueue.acquire();   // Ensure fair order of arriving readers and writers
                resource.acquire();       // Writer locks the resource exclusively
                serviceQueue.release();   // Release the queue so other readers or writers can wait

                // Simulate writing to the resource
                System.out.println("Writer " + writerId + " is writing.");
                Thread.sleep(1000); // Simulate time taken to write
                System.out.println("Writer " + writerId + " finished writing.");

                resource.release();       // Release the resource after writing
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Creating threads for readers and writers
        Reader reader1 = new Reader(1);
        Reader reader2 = new Reader(2);
        Writer writer1 = new Writer(1);
        Reader reader3 = new Reader(3);
        Writer writer2 = new Writer(2);

        // Creating threads to simulate concurrent access
        Thread r1 = new Thread(reader1);
        Thread r2 = new Thread(reader2);
        Thread w1 = new Thread(writer1);
        Thread r3 = new Thread(reader3);
        Thread w2 = new Thread(writer2);

        // Start the threads
        r1.start();
        w1.start();
        r2.start();
        w2.start();
        r3.start();
    }
}
