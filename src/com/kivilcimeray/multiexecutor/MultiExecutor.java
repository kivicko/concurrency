package com.kivilcimeray.multiexecutor;

import java.util.List;

/**
 * In this exercise we are going to implement a&nbsp; <code>MultiExecutor</code>
 * <p>The client of this class will create a list of <code>Runnable</code> tasks and provide that list into <code>MultiExecutor</code>'s constructor.</p>
 * <p>When the client runs the&nbsp; <code>executeAll()</code>,&nbsp; the <code>MultiExecutor</code>,&nbsp; will execute all the given tasks.</p>
 * <p>To take full advantage of our multicore CPU, we would like the <code>MultiExecutor</code> to execute all the tasks in parallel, by passing each task to a different thread.</p>,
 * <p><br></p><p>Please implement the <code>MultiExecutor</code> below</p>
 */


public class MultiExecutor {
    private List<Runnable> tasks;
    // Add any necessary member variables here

    /*
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        // Complete your code here
        this.tasks = tasks;
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        for (Runnable task : tasks) {
            Thread thread = new Thread(task);
            thread.run();
        }
    }
}
