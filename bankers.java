import java.util.Scanner;

public class BankersAlgorithm {

    // Method to check if the system is in a safe state
    public static boolean isSafe(int[][] allocation, int[][] max, int[] available) {
        int numProcesses = allocation.length;
        int numResources = available.length;

        // Calculate the need matrix
        int[][] need = new int[numProcesses][numResources];
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        // Initialize finish array and safe sequence
        boolean[] finish = new boolean[numProcesses];
        int[] safeSequence = new int[numProcesses];
        int count = 0;

        // Check for a safe sequence
        while (count < numProcesses) {
            boolean found = false;
            for (int p = 0; p < numProcesses; p++) {
                if (!finish[p]) {
                    boolean canAllocate = true;
                    for (int r = 0; r < numResources; r++) {
                        if (need[p][r] > available[r]) {
                            canAllocate = false;
                            break;
                        }
                    }

                    // If the process can be allocated resources
                    if (canAllocate) {
                        // Simulate allocation
                        for (int r = 0; r < numResources; r++) {
                            available[r] += allocation[p][r];
                        }
                        safeSequence[count++] = p;
                        finish[p] = true;
                        found = true;
                    }
                }
            }

            // If no safe sequence is found, system is in an unsafe state
            if (!found) {
                System.out.println("System is in an unsafe state.");
                return false;
            }
        }

        // Output the safe sequence
        System.out.print("System is in a safe state.\nSafe sequence is: ");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("P" + safeSequence[i] + " ");
        }
        System.out.println();
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of processes and resources
        System.out.print("Enter number of processes: ");
        int numProcesses = scanner.nextInt();
        System.out.print("Enter number of resources: ");
        int numResources = scanner.nextInt();

        int[][] allocation = new int[numProcesses][numResources];
        int[][] max = new int[numProcesses][numResources];
        int[] available = new int[numResources];

        // Input allocation matrix
        System.out.println("Enter allocation matrix (processes x resources):");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Process " + i + ": ");
            for (int j = 0; j < numResources; j++) {
                allocation[i][j] = scanner.nextInt();
            }
        }

        // Input maximum matrix
        System.out.println("Enter maximum matrix (processes x resources):");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Process " + i + ": ");
            for (int j = 0; j < numResources; j++) {
                max[i][j] = scanner.nextInt();
            }
        }

        // Input available resources
        System.out.print("Enter available resources: ");
        for (int i = 0; i < numResources; i++) {
            available[i] = scanner.nextInt();
        }

        // Run the safety algorithm
        isSafe(allocation, max, available);

        scanner.close();
    }
}
