import java.util.*;
import java.util.concurrent.atomic.*;

class Main {
  static CountingNetwork bitonic;
  static AtomicInteger[] counts;
  static int WIDTH = 16;
  static int OPS = 1000;

  static Thread thread(int id, boolean balance) {
    return new Thread(() -> {
      for (int i=0; i<OPS; i++) {
        int c = (int) (WIDTH * Math.random());
        if (balance) c = bitonic.traverse(c);
        counts[c].incrementAndGet();
        Thread.yield();
      }
      log(id+": done");
    });
  }

  static void setup() {
    bitonic = new BitonicNetwork(WIDTH);
    counts = new AtomicInteger[WIDTH];
    for (int i=0; i<WIDTH; i++)
      counts[i] = new AtomicInteger(0);
  }

  static void testThreads(boolean balance) {
    setup();
    Thread[] t = new Thread[WIDTH];
    for (int i=0; i<WIDTH; i++) {
      t[i] = thread(i, balance);
      t[i].start();
    }
    try {
    for (int i=0; i<WIDTH; i++)
      t[i].join();
    }
    catch(InterruptedException e) {}
  }

  static boolean isBalanced() {
    int v = counts[0].get();
    for (int i=0; i<WIDTH; i++)
      if (v-counts[i].get() > 1) return false;
    return true;
  }

  public static void main(String[] args) {
    log("Starting unbalanced threads ...");
    testThreads(false);
    log(Arrays.deepToString(counts));
    log("Counts balanced? "+isBalanced());
    log("");
    log("Starting balanced threads ...");
    testThreads(true);
    log(Arrays.deepToString(counts));
    log("Counts balanced? "+isBalanced());
  }

  static void log(String x) {
    System.out.println(x);
  }
}
