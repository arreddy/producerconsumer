package com.adi.pc;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrentConsumerImpl implements Consumer {

    private BlockingQueue< Item > itemQueue = new LinkedBlockingQueue<>();

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private List< ItemProcessor > jobList = new LinkedList<>();

    private volatile boolean shutdownCalled = false;


    public ConcurrentConsumerImpl(int poolSize) {


        for(int i = 0; i < poolSize; i++)  {
            ItemProcessor jobThread =
                    new ItemProcessor(itemQueue);

            jobList.add(jobThread);
            executorService.submit(jobThread);



        }

    }

    public boolean consume(Item j)
    {
        if(!shutdownCalled)
        {
            try
            {
                itemQueue.put(j);
            }
            catch(InterruptedException ie)
            {
                Thread.currentThread().interrupt();
                return false;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    public void finishConsumption()
    {
        for(ItemProcessor j : jobList)
        {
            j.cancelExecution();
        }

        executorService.shutdown();
    }
}
