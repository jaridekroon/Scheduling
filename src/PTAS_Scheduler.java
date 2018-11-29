import java.util.ArrayList;
import java.util.Random;

public class PTAS_Scheduler {

    int m;
    double eps;
    int[] times;
    int[] load;
    int T;

    ArrayList<Integer> large;
    ArrayList<Integer> small;

    public PTAS_Scheduler(int m, double eps, int[] times) {
        this.m = m;
        this.eps = eps;
        this.times = times;
        load = new int[m];
        for(int i = 0; i < m; i++) {
            load[i] = 0;
        }
        //compute total processing time
        T = 0;
        for(int j = 0; j < times.length; j++) {
            T += times[j];
        }
        large = new ArrayList<>();
        small = new ArrayList<>();
        //split large from small jobs
        System.out.println("eps times total load: " + eps * T);
        for(int j = 0; j < times.length; j++) {
            if(times[j] >= eps * T) {
                large.add(times[j]);
            }
            else {
                small.add(times[j]);
            }
        }
        //compute optimal schedule for large jobs (m^|large| options)
        scheduleLarge();
        //schedule small jobs greedily
        scheduleSmall();
        //compute makespan
        System.out.println(makespan(load));
    }

    public void scheduleLarge() {
        int[] temp = load.clone();
        load = optSchedule(0,temp);
    }

    public int[] optSchedule(int i, int[] loads) {
        if (i == large.size())  { //all jobs scheduled
            return loads;
        }
        else {
            //assign job i to each machine, take min
            int min = Integer.MAX_VALUE;
            int[] result = loads;
            for(int j = 0; j < loads.length; j++) {
                int[] temp = loads.clone();
                temp[j] += large.get(i);
                int[] call = optSchedule(i+1, temp);
                if(makespan(call) < min) {
                    min = makespan(call);
                    result = call;
                }
            }
            return result;
        }
    }



    public void scheduleSmall() {
        //for every job
        for(int j = 0; j < small.size(); j++) {
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
            load[index] += small.get(j);
        }
    }

    public int makespan(int[] loads) {
        int max = 0;
        for(int i = 0; i < m; i++) {
            if(loads[i] > max) {
                max = loads[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int m = 3;
        int n = 100;
        double eps = 0.05;
        int range = 10;
        System.out.println(m + " " + n + " " + eps);
        Random rand = new Random();
        int[] times = new int[n];
        for(int i = 0; i < times.length; i++) {
            times[i] = rand.nextInt(range) + 1; //min 1, max range
            System.out.println(times[i]);
        }
        PTAS_Scheduler ps = new PTAS_Scheduler(m,eps,times);
    }

}
