package com.company;

import java.util.*;

public class SRTF { //Preemptive SHF && Shortest-Remaining-Time-First
    Scanner in = new Scanner(System.in);
    int ContextSwitching = 0;
    Vector<Integer> executionOrder= new Vector();

    SRTF(){
        System.out.println("\n-----------------Welcome To Preemptive Shortest Job First with context switching Scheduling-----------------");
        //Take Input
        System.out.print("Enter Number of process: ");
        int numberOfProcess = in.nextInt();
        Process processes[]= new Process[numberOfProcess];

        System.out.println ("Enter=> ProcessName,ArrivalTime,BurstTime");
        for(int i=0; i<numberOfProcess; i++){
            Process p= new Process();
            System.out.print("P"+(i+1)+"=> ") ;
            String x= in.next();
            String[] data= x.split(",");

            p.id=i;
            p.name= data[0];
            p.arrivalTime= Integer.parseInt(data[1]);
            p.burstTime= Integer.parseInt(data[2]);
            p.tempForBurstTime= p.burstTime;
            p.flag=false;
            processes[i]=p;
        }

        System.out.print("Enter Context Switching: " ) ; //overhead time
        ContextSwitching= in.nextInt();
        System.out.println();

        Run(processes);

    }

    public void Run(Process[] processes)  {
        int systemTime=0, finished= 0 ,currProcess=-1;

        while (true) {
            int min = 1000000000;
            int selectedProcess=-1;

            if (finished == processes.length)
                break;

            //select process
            for (int i = 0; i < processes.length; i++) {
                if ((processes[i].arrivalTime <= systemTime) && (!processes[i].flag) && (processes[i].burstTime < min)) {
                    min = processes[i].burstTime;
                    selectedProcess = i;
                }
            }

            if (selectedProcess != -1) { //process arrived
                if(currProcess == -1) {  //CPU start
                    currProcess = selectedProcess;
                    executionOrder.add(processes[currProcess].id);
                }
                else if (selectedProcess != currProcess){ //add context switch overhead
                    systemTime += ContextSwitching;
                    currProcess = selectedProcess;
                    executionOrder.add(processes[currProcess].id);

                    //System.out.println("turned to " + processes[currProcess].name + ", time " + systemTime);
                }
                //add current process
                if(processes[currProcess].startTime == -1)
                    processes[currProcess].startTime= systemTime;

                systemTime++;
                processes[currProcess].burstTime--;

                if (processes[currProcess].burstTime == 0) {
                    processes[currProcess].completeTime = systemTime + ContextSwitching; //context switch cost
                    processes[currProcess].flag= true;
                    finished++;
                }
            }
            else //no process arrived
                systemTime++;
        }

       //print output
        output(processes);
    }

    private void output(Process[] processes){

        //calculate average
        float AverageWaitingTime = 0, AverageTurnAroundTime = 0;
        for(int i=0; i<executionOrder.size(); i++){
            if (processes[executionOrder.get(i)].waitingTime != -1) //process waited more than once
                processes[executionOrder.get(i)].waitingTime=  processes[executionOrder.get(i)].completeTime - processes[executionOrder.get(i)].arrivalTime - processes[executionOrder.get(i)].tempForBurstTime;
            else{
                processes[executionOrder.get(i)].waitingTime=  processes[executionOrder.get(i)].startTime - processes[executionOrder.get(i)].arrivalTime;
            }
        }

        for (int i = 0; i < processes.length; i++) {
            processes[i].turnaroundTime= processes[i].completeTime -  processes[i].arrivalTime;
            AverageWaitingTime +=  processes[i].waitingTime;
            AverageTurnAroundTime +=  processes[i].turnaroundTime;
        }

        System.out.printf("%1s  %15s  %12s  %15s  %17s  %15s", "Name", "ArrivalTime", "BurstTime", "CompleteTime", "TurnAroundTime","WaitingTime");
        System.out.println();
        for(int i = 0 ; i < processes.length ; i++ ) {
            System.out.format("%1s  %15s  %12s  %15s  %15s  %15s", processes[i].name, processes[i].arrivalTime, processes[i].tempForBurstTime, processes[i].completeTime, processes[i].turnaroundTime, processes[i].waitingTime);
            System.out.println();
        }
        System.out.print("\nExecution Order: ");
        for (int i=0; i<executionOrder.size(); i++) {
            if(i== executionOrder.size()-1)
                System.out.println(processes[executionOrder.get(i)].name);
            else
                System.out.print(processes[executionOrder.get(i)].name+ " -> ");
        }
        System.out.println ("Average TurnAround Time = "+ (AverageTurnAroundTime/processes.length));
        System.out.println ("Average Waiting Time = "+ (AverageWaitingTime/processes.length));
    }
}




