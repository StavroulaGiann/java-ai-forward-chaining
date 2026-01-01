import java.util.*;

public class State implements Comparable<State>
{
    private int f, h, g;
    private State father;
    private int totalTime;
    private int leftMissionaries, leftCannibals, rightMissionaries, rightCannibals;
    private boolean boatOnLeft; 
    private int boatCapacity; 

    //constructor - fill with arguments if necessary
    public State(int leftMissionaries, int leftCannibals, int rightMissionaries, int rightCannibals,
                 boolean boatOnLeft, int boatCapacity) 
    {
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.father = null;
        this.totalTime = 0;
        this.leftMissionaries = leftMissionaries;
        this.leftCannibals = leftCannibals;
        this.rightMissionaries = rightMissionaries;
        this.rightCannibals = rightCannibals;
        this.boatOnLeft = boatOnLeft;
        this.boatCapacity = boatCapacity;
    }

    /*copy constructor*/
    public State(State s)
    {
        this.f = s.f;
        this.g = s.g;
        this.h = s.h;
        this.father = s.father;
        this.totalTime = s.totalTime;
        this.leftMissionaries = s.leftMissionaries;
        this.leftCannibals = s.leftCannibals;
        this.rightMissionaries = s.rightMissionaries;
        this.rightCannibals = s.rightCannibals;
        this.boatOnLeft = s.boatOnLeft;
        this.boatCapacity = s.boatCapacity;
    }

    /* Returns the value f = g + h */
    public int getF() 
    {
        return this.f;
    }

    /* Returns the cost from the root to a node n */
    public int getG() 
    {
        return this.g;
    }

    /* Returns the value of the heuristic */
    public int getH() 
    {
        return this.h;
    }

    /* Returns the father of a node */
    public State getFather()
    {
        return this.father;
    }

    /* Setter for f value */
    public void setF(int f)
    {
        this.f = f;
    }

    /* Setter for g value */
    public void setG(int g)
    {
        this.g = g;
    }

    /* Setter for heuristic */
    public void setH(int h)
    {
        this.h = h;
    }

    /* Setter for f value */
    public void setFather(State f)
    {
        this.father = f;
    }

    /* Returns the execution time of the program */
    public int getTotalTime() 
    {
        return this.totalTime;
    }

    /* Sets the execution time of the program */
    public void setTotalTime(int time)
    {
        this.totalTime = time;
    }

    /* Calculates the value of h */
    public void evaluate() 
    {
        int leftPeople = leftMissionaries + leftCannibals;

        /* Estimates the number of crossings remaining based on the people on the left side   */
        this.h = (int) Math.ceil((double) leftPeople / boatCapacity); 

       /* Heuristic estimation */
        this.f = this.g + this.h;
    }    
    
   /* Prints data for every state */
    public void print() 
    {
        System.out.println("Left: Missionaries=" + leftMissionaries + ", Cannibals=" + leftCannibals +
                " | Right: Missionaries=" + rightMissionaries + ", Cannibals=" + rightCannibals +
                " | Boat on " + (boatOnLeft ? "Left" : "Right"));
    }


    /* Generates all possible and valid child states for the current state */
    public ArrayList<State> getChildren() 
    {
        ArrayList<State> children = new ArrayList<>();
    
        for (int m = 0; m <= boatCapacity; m++) {
            for (int c = 0; c <= boatCapacity - m; c++) {
                if (m + c >= 1 && m + c <= boatCapacity) { 
                    if (m == 0 || m >= c) { // Valid move in the boat
                        State child;
                        if (boatOnLeft) {
                            if (leftMissionaries >= m && leftCannibals >= c) {
                                child = new State(leftMissionaries - m, leftCannibals - c, rightMissionaries + m, rightCannibals + c, false, boatCapacity);
                            } else {
                                continue;
                            }
                        } else {
                            if (rightMissionaries >= m && rightCannibals >= c) {
                                child = new State(leftMissionaries + m, leftCannibals + c, rightMissionaries - m, rightCannibals - c, true, boatCapacity);
                            } else {
                                continue;
                            }
                        }
    
                        // Check if the child state is valid
                        if ((child.leftMissionaries == 0 || child.leftMissionaries >= child.leftCannibals) &&
                            (child.rightMissionaries == 0 || child.rightMissionaries >= child.rightCannibals)) {
                            
                            // Set parent, g, and evaluate the child
                            child.setFather(this);
                            child.setG(this.g + 1);
                            child.evaluate();
                            children.add(child);
                        }
                    }
                }
            }
        }
    
        return children;
    }
    

    /* Checks if all the people are on the right side */
    public boolean isFinal() 
    {
        return leftMissionaries == 0 && leftCannibals == 0 && !boatOnLeft;
    }

    /* Checks the equality between two states */
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false; // If not in the same class 
        State state = (State) obj; // Casting obj into State
        // Check all fields of State
        return leftMissionaries == state.leftMissionaries &&
                leftCannibals == state.leftCannibals &&
                rightMissionaries == state.rightMissionaries &&
                rightCannibals == state.rightCannibals &&
                boatOnLeft == state.boatOnLeft;
    }

    /* Estimates a unique integer for every state object */
    @Override
    public int hashCode() 
    {
        return Objects.hash(leftMissionaries, leftCannibals, rightMissionaries, rightCannibals, boatOnLeft);
    }

    /* Compares based on heuristic */
    @Override
    public int compareTo(State s)
    {
        return Integer.compare(this.f, s.getF());
    }
}
