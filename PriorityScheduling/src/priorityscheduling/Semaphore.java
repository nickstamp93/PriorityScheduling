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

    //The task object that is currently using the resource
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

    /**
     * A method that a task calls in order to request use of the resource
     *
     * @param task the MyTask object that requests to use the resource
     */
    public void requestResource(MyTask task) {
        //if task did not get the permission to use the resource , suspend it
        if (!acquire(task)) {
            task.suspend();
        }

        //System.out.println("Reource acquired by proccess " + task.getID() + "(priority : " + task.getTaskPriority() + ")");

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

    /**
     * This method is called by a task in order to acquire the resource
     *
     * @param task the MyTask object
     * @return true if the task got control of the resource else false
     */
    public synchronized boolean acquire(MyTask task) {
        System.out.println("Process " + task.getID() + " requests the resource");
        //if resource is currently busy
        if (isBusy) {
            //add the current task to the waiting qeue
            isWaiting[task.getID()] = true;
            priorities[task.getID()] = task.getTaskPriority();
            sleepTasks[task.getID()] = task;
            System.out.println("Process " + task.getID() + " fails to take , control , enters the waiting qeue");
            return false;
        } else {
            //else the task gets the control of the resource
            handler = task;
            isBusy = true;
            System.out.println("Process " + task.getID() + " acquired the resource");
            return true;
        }
    }

    /**
     * This method is called by a task which has already control of the resource
     * and the method releases the resource from it . If there are other tasks
     * waiting it gives the control to the task with the highest priority .
     *
     * @param task MyTask object
     */
    public synchronized void release(MyTask task) {
        System.out.println("Process :" + task.getID() + " just released the resource");

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
            System.out.println("Waiting processes found! Choosing process " + maxPid + " with highest priority (" + maxPriority + ")");

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
