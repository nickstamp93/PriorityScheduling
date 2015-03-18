package priorityscheduling;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Objects of this class represent a single task
 *
 * @author Nikos Stampoulis
 */
public class MyTask extends Thread {

    private final int priority;   //priority
    private final int pid;    //proccess id
    private final int arrivalTime;    //arrival time of proccess after start of the programm (seconds)
    private final Semaphore s; //the common semaphore object among the tasks
    private final int executionTime;  // time using the resource (seconds)

    MyTask(int pid, int priority, int arrivalTime, int executionTime, Semaphore s) {
        this.priority = priority;
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.s = s;
        this.executionTime = executionTime;
    }

    public void setSemaphore(Semaphore sem) {
    }

    public int getTaskPriority() {
        return priority;
    }

    public int getID() {
        return pid;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Simulate the use of the resource from the current task . It does not do a
     * thing , it just throws the task to sleep for as many seconds as the
     * execution time variable
     *
     * @throws InterruptedException
     */
    public void simulateExecution() throws InterruptedException {
        sleep(1000 * executionTime);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000 * arrivalTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(MyTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Proccess " + pid + " just arrived (time = " + arrivalTime + ")");
        s.requestResource(this);
    }

}
