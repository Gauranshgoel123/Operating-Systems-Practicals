import java.util.Scanner;

public class MemoryManagement {
    public static void main(String[] args) {
        int[] frag = new int[25], b = new int[25], f = new int[25];
        int nb, nf, temp;
        int[] bf = new int[25], ff = new int[25];

        Scanner sc = new Scanner(System.in);

        System.out.println("\n\tMemory Management Scheme - First Fit");
        System.out.print("\nEnter the number of blocks: ");
        nb = sc.nextInt();

        System.out.print("Enter the number of files: ");
        nf = sc.nextInt();

        System.out.println("\nEnter the size of the blocks:-");
        for (int i = 1; i <= nb; i++) {
            System.out.print("Block " + i + ": ");
            b[i] = sc.nextInt();
        }

        System.out.println("Enter the size of the files:-");
        for (int i = 1; i <= nf; i++) {
            System.out.print("File " + i + ": ");
            f[i] = sc.nextInt();
        }

        for (int i = 1; i <= nf; i++) {
            for (int j = 1; j <= nb; j++) {
                if (bf[j] != 1) {
                    temp = b[j] - f[i];
                    if (temp >= 0) {
                        ff[i] = j;
                        frag[i] = temp;
                        bf[j] = 1; // Mark the block as allocated
                        break;
                    }
                }
            }
        }

        System.out.println("\nFile_no\tFile_size\tBlock_no\tBlock_size\tFragment");
        for (int i = 1; i <= nf; i++) {
            System.out.println(i + "\t\t" + f[i] + "\t\t" + ff[i] + "\t\t" + b[ff[i]] + "\t\t" + frag[i]);
        }

        sc.close();
    }
}
