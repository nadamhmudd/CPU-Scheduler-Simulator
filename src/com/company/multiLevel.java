package com.company;
import java.util.*;

public class multiLevel {

    Scanner in = new Scanner(System.in);
    int quantumTime;
    Vector<Integer> executionOrder= new Vector();
    Vector<Integer> RRQueue= new Vector<>();
    Vector<Integer> FCFSQueue= new Vector<>();

    multiLevel() {
        System.out.println("\n-----------------Welcome To Multi level Scheduling-------------------");
        //Take Input
        System.out.print("Enter Number of process: ");
        int numberOfProcess = in.nextInt();
        Process processes[] = new Process[numberOfProcess];

        System.out.println("Enter=> ProcessName,ArrivalTime,BurstTime,QueueNumber");
        System.out.println("QueueNumber:\n1- Round Robin\n2- preemptive FCFS");

        for (int i = 0; i < numberOfProcess; i++) {
            Process p = new Process();
            System.out.print("P" + (i + 1) + "=> ");
            String x = in.next();
            String[] data = x.split(",");

            p.id = i;
            p.name = data[0];
            p.arrivalTime = Integer.parseInt(data[1]);
            p.burstTime = Integer.parseInt(data[2]);
            p.tempForBurstTime = p.burstTime;
            p.queue = Integer.parseInt(data[3]);
            p.flag = false;
            processes[i] = p;
        }
        System.out.print("Enter Time Quantum: " ) ; //fixed time to execute
        quantumTime= in.nextInt();
        System.out.println();

        Run(processes);
    }

    public void Run(Process[] processes)  {
        int systemTime=0, finished= 0, currProcess=-1, itr=0;

        while (true) {
            if (finished == processes.length)
                break;

            //select all processes arrived
            for (int i = 0; i < processes.length; i++) {
                if ((processes[i].arrivalTime <= systemTime) && (!processes[i].flag)) {
                    if(processes[i].queue==1 && !RRQueue.contains(processes[i].id))
                        RRQueue.add(processes[i].id);
                    else if(processes[i].queue==2 && !FCFSQueue.contains(processes[i].id))
                        FCFSQueue.add(processes[i].id);
                }
            }

            if(!RRQueue.isEmpty()){
                if(itr < quantumTime){

                    if (!executionOrder.contains(RRQueue.firstElement())){
                        processes[RRQueue.firstElement()].startTime= systemTime;
                    }

                    if (currProcess != RRQueue.firstElement())
                        executionOrder.add(RRQueue.firstElement());

                    currProcess= RRQueue.firstElement();
                    processes[currProcess].burstTime--;
                    systemTime++;
                    itr++;

                    if (processes[currProcess].burstTime == 0)
                    {
                        processes[currProcess].completeTime = systemTime;
                        processes[currProcess].flag = true;
                        finished++;

                        itr=0;
                        RRQueue.remove(RRQueue.indexOf(currProcess));
                    }
                }
                else{
                    itr=0;
                    RRQueue.remove(RRQueue.indexOf(currProcess));
                    RRQueue.add(currProcess); //add in last queue
                }
            }
            else if(!FCFSQueue.isEmpty()){

                if (!executionOrder.contains(FCFSQueue.firstElement())){
                    processes[FCFSQueue.firstElement()].startTime= systemTime;
                }

                if (currProcess != FCFSQueue.firstElement())
                    executionOrder.add(FCFSQueue.firstElement());

                currProcess= FCFSQueue.firstElement();
                processes[currProcess].burstTime--;
                systemTime++;

                if (processes[currProcess].burstTime == 0) {
                    processes[currProcess].completeTime = systemTime;
                    processes[currProcess].flag = true;
                    finished++;

                    FCFSQueue.remove(FCFSQueue.indexOf(currProcess));
                }
            }
            else{
                systemTime++;
            }
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

        System.out.printf("%1s  %9s  %15s  %12s  %15s  %17s  %15s", "Name", "QueueNumber", "ArrivalTime", "BurstTime", "CompleteTime", "TurnAroundTime","WaitingTime");
        System.out.println();
        for(int i = 0 ; i < processes.length ; i++ ) {
            System.out.format("%1s  %9s  %15s  %12s  %15s  %15s  %15s", processes[i].name, processes[i].queue, processes[i].arrivalTime, processes[i].tempForBurstTime, processes[i].completeTime, processes[i].turnaroundTime, processes[i].waitingTime);
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