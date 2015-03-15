package priorityscheduling;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikos Stampoulis
 */
public class Semaphore {

    private static boolean isBusy;    //true , if resource is currently in use
    private static boolean[] isWaiting;    //isWaiting[i] = true if task i is currently waiting to use the resource
    private static int[] priorities;    //priorities[i] is the priority of task i

    //array with tasks that are in sleep mode
    private static MyTask[] sleepTasks;

    MyTask handler;

    Semaphore(int numThreads) {
        isBusy = false;
        isWaiting = new boolean[numThreads];
        priorities = new int[numThreads];

        for (boolean b : isWaiting) {
            b = false;
        }
        sleepTasks = new MyTask[numThreads];
    }

    public void requestResource(MyTask task) {
        //if task did not get the permission to use the resource , suspend it
        if (!acquire(task)) {
            task.suspend();
        }

        System.out.println("The resource is used by : Pid:" + task.getID() + "  Priority:" + task.getTaskPriority());

        try {
            //the task uses the resource
            task.simulateExecution();
        } catch (InterruptedException ex) {
            Logger.getLogger(Semaphore.class.getName()).log(Level.SEVERE, null, ex);
        }
        //free the resource from the task
        release(task);
        //after finishing its work , kill the task
        task.stop();
    }

    public synchronized boolean acquire(MyTask task) {
        //if resource is currently busy
        if (isBusy) {
            //add the current task to the waiting qeue
            isWaiting[task.getID()] = true;
            priorities[task.getID()] = task.getTaskPriority();
            sleepTasks[task.getID()] = task;

            return false;
        } else {
            //else the task gets the control of the resource
            handler = task;
            isBusy = true;
            return true;
        }
    }

    public synchronized void release(MyTask task) {
        System.out.println("Process with Pid :" + task.getID() + " just entered RELEASE");

        int maxPriority = 0;
        int maxPid = 0;

        //from the waiting qeue , find the task with the highest priority
        for (int i = 0; i < sleepTasks.length; i++) {
            if (isWaiting[i] == true && maxPriority < priorities[i]) {
                maxPriority = priorities[i];
                maxPid = i;
            }
        }

        //if task was found
        if (maxPriority != 0) {

            isWaiting[maxPid] = false;
            System.out.println("Waiting process found! Pid :" + maxPid + " has highest priority :" + maxPriority);

            //set the control of the resource to this task
            handler = sleepTasks[maxPid];
            sleepTasks[maxPid].resume();
        } else {
            //else , no wating processes found
            System.out.println("No waiting processes found!");
            isBusy = false;
        }

    }

}
