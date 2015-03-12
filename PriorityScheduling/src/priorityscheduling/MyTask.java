
package priorityscheduling;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikos Stampoulis
 */
public class MyTask extends Thread{
    private int priority;   //priority
    private int pid;    //proccess id
    private int arrivalTime;    //arrival time of proccess after start of the programm (seconds)
    private Semaphore s; //the common semaphore object among the tasks
    private int executionTime;  // time using the resource (seconds)
    
    
    MyTask(int pid, int priority, int arrivalTime,int executionTime, Semaphore s){
        this.priority = priority;
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.s = s;
        this.executionTime = executionTime;
    }
    
    public void setSemaphore(Semaphore sem){
    }
    
    public int getTaskPriority(){
        return priority;
    }
    
    public int getID(){
        return pid;
    }
    public int getArrivalTime(){
        return arrivalTime;
    }
}
