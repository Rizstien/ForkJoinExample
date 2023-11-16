package org.forkjoin.io;

public class Main {
    public static void main(String[] args) {
        CustomRecursiveAction forkJoinAction = new CustomRecursiveAction("Hello world!");
        forkJoinAction.compute();

        CustomRecursiveTask forkJoinTask = new CustomRecursiveTask(new int[]{3, 34, 12, 9, 6, 10, 25, 14});
        System.out.println(forkJoinTask.compute());
    }
}