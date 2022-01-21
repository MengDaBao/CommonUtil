package util;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProcessUtil {

    public static void exec(String command, File file, Long outTime) throws Exception {
        final boolean[] lost = {false};
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future<Process> future = threadPool.submit(() -> Runtime.getRuntime().exec(command, null, file));
        while (true) {
            if (future.isDone()) {
                Process process = future.get();
                printIn(process.getInputStream());
                printIn(process.getErrorStream());
                Thread lostThread = new Thread(() -> {
                    try {
                        for (long i = 0; i < (outTime / 100); i++) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                return;
                            }
                        }
                        process.getInputStream().close();
                        process.getErrorStream().close();
                        process.getOutputStream().close();
                        process.destroy();
                        lost[0] = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                lostThread.start();
                process.waitFor();
                if (lostThread.isAlive()) {
                    lostThread.interrupt();
                }
                break;
            } else {
                Thread.sleep(100);
            }
        }
        if (lost[0]) {
            future.cancel(true);
            threadPool.shutdown();
            throw new Exception("执行命令超时!");
        }
        threadPool.shutdown();
    }

    private static void printIn(InputStream in) {
        new Thread(() -> {
            BufferedReader br1 = new BufferedReader(new InputStreamReader(in));
            try {
                String line1 = null;
                while ((line1 = br1.readLine()) != null) {
                    System.out.println(line1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
