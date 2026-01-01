import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class MC {
    public static void main(String[] args) {
        
        /*Checking if the arguments were entered properly. */
        if (args.length != 3) {
            System.out.println("Enter values for Cannibals and Missionaries (N), boat capacity (M) and maximum number of crossings (K)");
            return;
        }

        int N = Integer.parseInt(args[0]);
        int M = Integer.parseInt(args[1]);
        int K = Integer.parseInt(args[2]);
        boolean flag = false;


        /*Checking the validity of the arguments. */
        if (M < 1 || N < 1 || K < 1) {
            System.out.println("Invalid input. All values must be >= 1.");
            return;
        }
         /*Checking if there is any solution with the arguments given. */
        else if ((M==2 && N>=4 || M==3 && N >5)  ){
            System.out.println("There is no solution for these values.");
            return;
        }

        /*State object initialization. */
        State start = new State(N, N, 0, 0, true, M);

        start.evaluate();
        
        /*Priority queue initialization for Α* */
        PriorityQueue<State> open = new PriorityQueue<>();

        /*HashSet  initialization for closed set implementation.*/
        Set<State> closed = new HashSet<>();

        /*Add start state to the priority queue.*/
        open.add(start);

        long start2 = System.nanoTime();

        /*While there are elements in the priority queue */
        while (!open.isEmpty()) {

            /* Choose the state with minimum f*/
            State current = open.poll();

            /*Check if the current state is final, if so print a message*/
            if (current.isFinal()) {
                long end2 = System.nanoTime();
                current.setTotalTime((int)(end2-start2));
                printSolution(current);
                return;
            }

            /* Add current state to closed set*/
            closed.add(current);

            /* Generate current state's chlidren leaving out states that belong to the closed set*/
            for (State ch : current.getChildren()) {
                if (closed.contains(ch)) {
                    continue;
                }

                /* Check if the state surpasses the K argument,if so don't add it to the priority queue*/
                if (ch.getG() > K) {
                    flag = true;
                    continue;
                }

                open.add(ch);
            }
            
        }

        /*If there is no solution,  print a proper message that also explains the reason */
        if (flag)
            System.out.println("The K parameter is not enough for the crossings required.");
        else
            System.out.println("No solution found within the maximum allowed crossings.");
        
    }
    /* Prints the solution with a specific format */
    private static void printSolution(State state) {
        List<State> p = new ArrayList<>();
        System.out.println("Total Time : " + state.getTotalTime() + " nanoseconds");
        while (state != null) {
            p.add(0, state);
            state = state.getFather();
        }
        for (int i = 0; i < p.size(); i++) {
            System.out.println("Step " + i + ":");
            p.get(i).print();
            
        }

    }
}
