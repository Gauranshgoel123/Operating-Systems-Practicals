import java.util.Arrays;
import java.util.Comparator;

class Process {
    String name;
    int burstTime;

    public Process(String name, int burstTime) {
        this.name = name;
        this.burstTime = burstTime;
    }
}

public class SJF_CPU_Scheduling {
    public static void main(String[] args) {
        Process[] processes = {
            new Process("P1", 6),
            new Process("P2", 8),
            new Process("P3", 7),
            new Process("P4", 3)
        };

        calculateSJF(processes);
    }

    public static void calculateSJF(Process[] processes) {
        int n = processes.length;

        // Sort processes by burst time using a comparator
        Arrays.sort(processes, Comparator.comparingInt(p -> p.burstTime));

        // Arrays to store waiting time and turnaround time
        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n];

        // Calculate waiting time for each process
        waitingTime[0] = 0; // First process has no waiting time
        for (int i = 1; i < n; i++) {
            waitingTime[i] = processes[i - 1].burstTime + waitingTime[i - 1];
        }

        // Calculate turnaround time for each process
        for (int i = 0; i < n; i++) {
            turnaroundTime[i] = processes[i].burstTime + waitingTime[i];
        }

        // Calculate total waiting time and turnaround time
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        System.out.println("Process\tBurst Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < n; i++) {
            totalWaitingTime += waitingTime[i];
            totalTurnaroundTime += turnaroundTime[i];
            System.out.printf("%s\t%d\t\t%d\t\t%d%n", processes[i].name, processes[i].burstTime, waitingTime[i], turnaroundTime[i]);
        }

        System.out.printf("%nAverage waiting time: %.2f%n", totalWaitingTime / n);
        System.out.printf("Average turnaround time: %.2f%n", totalTurnaroundTime / n);
    }
}
