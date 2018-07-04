package com.kimonik.utilsmodule.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * * ===============================================================
 * name:             ThreadUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/2/26
 * description：  未考虑线程同步问题,大量并发时可能会创建多实例
 * history：
 * *==================================================================
 */

public class ThreadUtils {

//    public static final int CASH_THREAD_POOL_FLAG = 1;
//    public static final int FIXED_THREAD_POOL = 2;
//    public static final int SINGLE_THREAD_POOL = 3;
//    public static final int SCHEDULED_THREAD_POOL = 4;

    private static ExecutorService cachedThreadPool;
    private static ExecutorService fixedThreadPool;
    private static ExecutorService singleThreadExecutor;
    private static ExecutorService scheduledThreadPool;
    private static ExecutorService threadPoolExecutor;

    private ThreadUtils() {
        throw new UnsupportedOperationException("不允许使用new构造实例!!");
    }


    /**
     * CachedThreadPool中是没有核心线程的，但是它的最大线程数却为Integer.MAX_VALUE，
     * 另外，它是有线程超时机制的，超时时间为60秒，它使用了SynchronousQueue作为线程队列，
     * 提交到CachedThreadPool消息队列中的任务在执行的过程中由于最大线程数为无限大，
     * 所以每当我们添加一个新任务进来的时候，如果线程池中有空闲的线程，
     * 则由该空闲的线程执行新任务，如果没有空闲线程，则创建新线程来执行任务。根据CachedThreadPool的特点，
     * 可以在有大量任务请求的时候使用CachedThreadPool，因为当CachedThreadPool中没有新任务的时候，
     * 它里边所有的线程都会因为超时而被终止
     *
     * @return ExecutorService
     */
    public static ExecutorService getCashThreadPoolInstance() {
        if (cachedThreadPool == null) {
            cachedThreadPool = Executors.newCachedThreadPool();
        }
        return cachedThreadPool;
    }

    /**
     * 固定核心线程数线程池,线程池中没有非核心线程,核心线程永远不会被销毁,当核心线程无空闲时
     * 剩余任务进入到workQueue中等待，当有空闲的核心线程时就执行任务队列中的任务。
     * 多个核心线程时会乱序执行,按照线程的提交顺序依次提交给核心线程执行,单个核心线程时会按序执行
     *
     * @param nThreads 核心线程数的数量(注意:当已经有实例时,再次设置核心线程数无效,
     *                 只有首次设置核心线程数时才有效)
     * @return ExecutorService
     */
    public static ExecutorService getFixedThreadPoolInstance(int nThreads) {
        if (fixedThreadPool == null) {
            fixedThreadPool = Executors.newFixedThreadPool(nThreads);
        }
        return fixedThreadPool;
    }

    /**
     * 核心线程数只有1的固定线程池,没有非核心线程,不需要处理线程同步问题
     * 按照线程提交顺序执行线程,一个线程执行完后才会执行下一个线程
     *
     * @return ExecutorService
     */
    public static ExecutorService getSingleThreadPoolInstance() {
        if (singleThreadExecutor == null) {
            singleThreadExecutor = Executors.newSingleThreadExecutor();
        }
        return singleThreadExecutor;
    }

    /**
     * 核心线程数量是固定的（我们在构造的时候传入的），但是非核心线程是无穷大，
     * 当非核心线程闲置时，则会被立即回收。
     *
     * @param nThreads 核心线程数的数量
     * @return ExecutorService
     */
    public static ExecutorService getScheduledThreadPoolInstance(int nThreads) {
        if (scheduledThreadPool == null) {
            scheduledThreadPool = Executors.newScheduledThreadPool(nThreads);
        }
        return scheduledThreadPool;
    }

    /**
     * 获取自定义线程池,不包含创建线程池的工厂类和线程超出处理策略
     * workQueue是一个BlockingQueue类型,它是一个特殊的队列，当从BlockingQueue中取数据时，
     * 如果BlockingQueue是空的， 则取数据的操作会进入到阻塞状态，当BlockingQueue中有了新数据时，
     * 这个取数据的操作又会被重新唤醒。同理，如果BlockingQueue中的数据已经满了，
     往BlockingQueue中存数据的操作又会进入阻塞状态，直到BlockingQueue中又有新的空间，
     存数据的操作又会被重新唤醒。BlockingQueue有多种不同的实现类，如下：

     1.ArrayBlockingQueue：表示一个规定了大小的BlockingQueue，
     ArrayBlockingQueue的构造函数接受一个int类型的数据，该数据表示BlockingQueue的大小，
     存储在ArrayBlockingQueue中的元素按照FIFO（先进先出）的方式来进行存取。

     2.LinkedBlockingQueue：这个表示一个大小不确定的BlockingQueue，
     在LinkedBlockingQueue的构造方法中可以传一个int类型的数据，
     这样创建出来的LinkedBlockingQueue是有大小的，也可以不传，
     不传的话，LinkedBlockingQueue的大小就为Integer.MAX_VALUE，

     3.PriorityBlockingQueue：这个队列和LinkedBlockingQueue类似，不同的是PriorityBlockingQueue中的元素不是按照FIFO来排序的，
     而是按照元素的Comparator来决定存取顺序的（这个功能也反映了存入PriorityBlockingQueue中的数据必须实现了Comparator接口）。

     4.SynchronousQueue：这个是同步Queue，属于线程安全的BlockingQueue的一种，在SynchronousQueue中，
     生产者线程的插入操作必须要等待消费者线程的移除操作，Synchronous内部没有数据缓存空间，
     因此无法对SynchronousQueue进行读取或者遍历其中的数据，元素只有在你试图取走的时候才有可能存在。
     我们可以理解为生产者和消费者互相等待，等到对方之后然后再一起离开。

     1.execute一个线程之后，如果线程池中的线程数未达到核心线程数，则会立马启用一个核心线程去执行

     2.execute一个线程之后，如果线程池中的线程数已经达到核心线程数，且workQueue未满，则将新线程放入workQueue中等待执行

     3.execute一个线程之后，如果线程池中的线程数已经达到核心线程数但未超过非核心线程数，且workQueue已满，则开启一个非核心线程来执行任务

     4.execute一个线程之后，如果线程池中的线程数已经超过非核心线程数，则拒绝执行该任务


     注意:工作队列中的线程由核心线程执行,当核心线程有空闲时,会从工作队列中取出新的线程执行,当工作队列中
     的线程数已满,但可以创建非核心线程时,会立即创建非核心线程执行工作队列满了之后提交的线程,所以可能造成
     前面提交的线程会晚于后面提交的线程执行,当提交的线程数量大于线程池可容纳的总线程数量后,会抛出
     java.util.concurrent.RejectedExecutionException异常


     void shutdown();//顺次地关闭ExecutorService,停止接收新的任务，等待所有已经提交的任务执行完毕之后
     ，关闭ExecutorService


     List<Runnable> shutdownNow();//阻止等待任务启动并试图停止当前正在执行的任务，停止接收新的任务，
     返回处于等待的任务列表


     boolean isShutdown();//判断线程池是否已经关闭

     boolean isTerminated();//如果关闭后所有任务都已完成，则返回 true。注意，
     除非首先调用 shutdown 或 shutdownNow，否则 isTerminated 永不为 true。


     boolean awaitTermination(long timeout, TimeUnit unit)//等待（阻塞）直到关闭或最长等待时间或发生中断,
     timeout - 最长等待时间 ,unit - timeout 参数的时间单位  如果此执行程序终止，则返回 true；
     如果终止前超时期满，则返回 false


     * @param corePoolSize   核心线程的个数
     * @param maximumPoolSize   线程池中最多容纳的线程数,包含核心线程和非核心线程
     * @param keepAliveTime      非核心线程存活时间
     * @param unit     非核心线程存活时间的单位,秒,毫秒,分等 @see TimeUnit
     * @param workQueue    工作队列,SynchronousQueue,LinkedBlockingQueue,ArrayBlockingQueue等,不能为null
     *                     初始化工作队列容量不能为0
     * @return  ExecutorService
     */
    public static ExecutorService getThreadPoolInstance(int corePoolSize,
                                                        int maximumPoolSize,
                                                        long keepAliveTime,
                                                        TimeUnit unit,
                                                        @NonNull BlockingQueue<Runnable> workQueue) {

        return new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue);
//        if (threadPoolExecutor==null){
//            threadPoolExecutor=new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue);
//        }
//        return threadPoolExecutor;
    }

}
