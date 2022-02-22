package com.company;

public class Process {
    //for input:
    int id;
    String name;
    int burstTime, arrivalTime, tempForBurstTime,
        priority, //for P.Priority
        queue; //for multiLevel
    boolean flag; //checks if process is completed or not

    //for output:
    int waitingTime=-1, turnaroundTime, completeTime, startTime=-1;


}
