package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NumberFormatException, IOException {
        while (true) {
            System.out.println("\n-----------------------CPU Scheduling Simulator-----------------------");
            System.out.println("1- Preemptive Shortest Job First with context switching Scheduling.");
            System.out.println("2- Round Robin (RR) with context switching Scheduling.");
            System.out.println("3- Preemptive Priority Scheduling.");
            System.out.println("4- Multi level Scheduling.");
            System.out.println("0- Exit");
            System.out.print("\nPlease, Enter your choice: ");

            Scanner scan = new Scanner(System.in);
            int choice;
            choice = scan.nextInt();
            if (choice == 0)
                return;
            else if (choice == 1) {
                SRTF x = new SRTF();
            } else if (choice == 2) {
                RR x = new RR();
            } else if (choice == 3) {
                priority x = new priority();
            } else if (choice == 4) {
                multiLevel x = new multiLevel();
            }
        }
    }
}
