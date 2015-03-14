package priorityscheduling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikos Stampoulis
 */
public class PriorityScheduling {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //the input file
        File file = new File("input.txt");

        Scanner scanner;
        ArrayList<Integer> priorities = new ArrayList<>();	//task priorities
        ArrayList<Integer> arrivalTimes = new ArrayList<>();	//task arrival times (seconds)
        ArrayList<Integer> executionTimes = new ArrayList<>();	//task execution times
        MyTask[] tasks;	//array with all tasks

        //input file contains all the tasks
        //every row is another task
        //every row is : [priority] [arrival time] [execution time]
        try {
            scanner = new Scanner(file);
            //read each line
            while (scanner.hasNext()) {
                //and add its values to the arrays
                priorities.add(scanner.nextInt());
                arrivalTimes.add(scanner.nextInt());
                executionTimes.add(scanner.nextInt());
                System.out.print("Priority:" + priorities.get(priorities.size()-1));
                System.out.print(" Arrival:" + arrivalTimes.get(priorities.size()-1));
                System.out.println(" Execution Time:" + executionTimes.get(priorities.size()-1));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("\"input.txt\" not found");
            Logger.getLogger(PriorityScheduling.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        tasks = new MyTask[priorities.size()];
        
        //create the semaphore object
        Semaphore s = new Semaphore(priorities.size());
        
        //create one task for each row that has been read
        for (int i = 0; i < priorities.size(); i++) {
            MyTask t = new MyTask(i, priorities.get(i), arrivalTimes.get(i), executionTimes.get(i), s);
            tasks[i] = t;
            //and start the task immediately
            t.start();
        }

    }

}
