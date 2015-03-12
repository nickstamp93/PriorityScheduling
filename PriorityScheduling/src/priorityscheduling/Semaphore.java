
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

    Semaphore(int numThreads){
        isBusy = false;
        isWaiting = new boolean[numThreads];
        priorities = new int[numThreads];
        
        for(boolean b : isWaiting){
            b=false;
        }
        sleepTasks = new MyTask[numThreads];
    }
    
    
    
}
