package org.forkjoin.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTask extends RecursiveTask<Integer> {
    private static int THRESHOLD = 20;
    private int[] workload;

    public CustomRecursiveTask(int[] workload) {
        this.workload = workload;
    }


    @Override
    protected Integer compute() {
        if(workload.length > THRESHOLD){
            return ForkJoinTask.invokeAll(createSubTask(workload))
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
        }else{
            return processTask(workload);
        }
    }

    private List<CustomRecursiveTask> createSubTask(int[] workload){
        List<CustomRecursiveTask> subTask = new ArrayList<>();

        int[] partOne = Arrays.copyOfRange(workload, 0,workload.length/2);
        int[] partTwo = Arrays.copyOfRange(workload,workload.length/2, workload.length);

        subTask.add(new CustomRecursiveTask(partOne));
        subTask.add(new CustomRecursiveTask(partTwo));

        return subTask;
    }

    private Integer processTask(int[] workload){
        Integer result = Arrays.stream(workload)
                .filter(a-> a>10 && a<27)
                .map(a -> a*10)
                .sum();
        System.out.println("This result -"+result+"- was processed by "+Thread.currentThread().getName());
        return result;
    }

}
