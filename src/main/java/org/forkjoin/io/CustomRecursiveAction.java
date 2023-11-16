package org.forkjoin.io;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CustomRecursiveAction extends RecursiveAction {
    private static int THRESHOLD = 4;
    private String workload;

    public CustomRecursiveAction(String workload) {
        this.workload = workload;
    }


    @Override
    protected void compute() {
        if(workload.length() > THRESHOLD){
            ForkJoinTask.invokeAll(createSubTask(workload));
        }else{
            processTask(workload);
        }
    }

    private List<CustomRecursiveAction> createSubTask(String workload){
        List<CustomRecursiveAction> subTask = new ArrayList<>();

        String partOne = workload.substring(0,workload.length()/2);
        String partTwo = workload.substring(workload.length()/2, workload.length());

        subTask.add(new CustomRecursiveAction(partOne));
        subTask.add(new CustomRecursiveAction(partTwo));

        return subTask;
    }

    private void processTask(String workload){
        String result = workload.toUpperCase();
        System.out.println("This result -"+result+"- was processed by "+Thread.currentThread().getName());
    }

}
