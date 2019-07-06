package br.com.servico.votacao.testes;

import org.junit.Test;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    @Test
    public void givenUsingTimer_whenSchedulingTaskOnce_thenCorrect() {
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Task performed on: " + new Date() + "n" + "Thread's name: " + Thread.currentThread().getName());
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 60000L;
        timer.schedule(task, delay);
    }

    @Test
    public void givenUsingTimer_whenSchedulingRepeatedTask_thenCorrect() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                System.out.println("Task performed on " + new Date());
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        long period = 60000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);

    }

    @Test
    public void givenUsingTimer_whenStoppingThread_thenTimerTaskIsCancelled() throws InterruptedException {
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Task performed on " + new Date());
                System.out.println(Boolean.TRUE);
                // TODO: stop the thread here
            }
        };
        Timer timer = new Timer("Timer");

        timer.scheduleAtFixedRate(task, 1000L, 1000L);

        Thread.sleep(1000 * 2);
        System.out.println(Boolean.FALSE);
    }
}
