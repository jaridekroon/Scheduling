import java.util.Random;

public class GreedyScheduler {

    int m;
    int[] times;
    int[] load;

    public GreedyScheduler(int m, int[] times) {
        this.m = m;
        this.times = times;
        load = new int[m];
        for(int i = 0; i < m; i++) {
            load[i] = 0;
        }
        schedule();
        System.out.println(makespan());
    }

    public void schedule() {
        //for every job
        for(int j = 0; j < times.length; j++) {
            //find machine with minimum load
            int index = 0;
            int min = Integer.MAX_VALUE;
            for(int i = 0; i < m; i++) {
                if(load[i] < min) {
                    index = i;
                    min = load[i];
                }
            }
            //assign job j to machine index
            load[index] += times[j];
        }
    }

    public int makespan() {
        int max = 0;
        for(int i = 0; i < m; i++) {
            if(load[i] > max) {
                max = load[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int m = 4;
        int n = 100;
        int range = 100;
        System.out.println(m + " " + n);
        Random rand = new Random();
        int[] times = new int[n];
        for(int i = 0; i < times.length; i++) {
            times[i] = rand.nextInt(range) + 1; //min 1, max range
            System.out.println(times[i]);
        }
        GreedyScheduler gs = new GreedyScheduler(m,times);
    }
}
