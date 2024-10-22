import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Process {
    String name;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int completionTime;
    int waitingTime;
    int turnaroundTime;

    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class SRTF_CPU_Scheduling {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();
        Process[] processes = new Process[n];

        // Input process details
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name, arrival time, and burst time for Process " + (i + 1) + ": ");
            String name = scanner.next();
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            processes[i] = new Process(name, arrivalTime, burstTime);
        }

        calculateSRTF(processes);
        scanner.close();
    }

    public static void calculateSRTF(Process[] processes) {
        int n = processes.length;
        int time = 0;
        boolean[] isCompleted = new boolean[n];
        int completed = 0;

        // Sort processes by arrival time
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        // Process execution loop
        while (completed < n) {
            int shortestIndex = -1;
            int shortestTime = Integer.MAX_VALUE;

            // Find the process with the shortest remaining time
            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= time && !isCompleted[i] && processes[i].remainingTime < shortestTime) {
                    shortestTime = processes[i].remainingTime;
                    shortestIndex = i;
                }
            }

            if (shortestIndex != -1) {
                // Execute the process
                processes[shortestIndex].remainingTime--;
                time++;

                // If the process is completed
                if (processes[shortestIndex].remainingTime == 0) {
                    processes[shortestIndex].completionTime = time;
                    processes[shortestIndex].turnaroundTime = processes[shortestIndex].completionTime - processes[shortestIndex].arrivalTime;
                    processes[shortestIndex].waitingTime = processes[shortestIndex].turnaroundTime - processes[shortestIndex].burstTime;
                    isCompleted[shortestIndex] = true;
                    completed++;
                }
            } else {
                // If no process is ready to execute, increment time
                time++;
            }
        }

        // Print the results
        System.out.printf("%-10s %-15s %-15s %-15s %-15s%n", "Process", "Arrival Time", "Burst Time", "Completion Time", "Waiting Time", "Turnaround Time");
        System.out.println("----------------------------------------------------------------------------");
        
        for (Process process : processes) {
            System.out.printf("%-10s %-15d %-15d %-15d %-15d %-15d%n",
                    process.name, process.arrivalTime, process.burstTime,
                    process.completionTime, process.waitingTime, process.turnaroundTime);
        }
    }
}
