
# Missionaries and cannibals problem

In this problem, N missionaries and N cannibals want to cross the river using a boat of M capacity while the maximum number of crossings is K. In each state in which there is at least one missionary on the boat and M>2 condition is true, the number of cannibals must no be greater than the number of the missionaries. The main algoritmh that is used for the solution of this program is the A* algoritmh with closed set.


## Running Tests

To run tests, run the following command

```bash
  java MC N M K
```


## Usage/Examples

```javascript
Input : N = 5, M = 3, K = 100
Output : 
Total Time : 915500 nanoseconds
Step 0:
Left: M=5, C=5 | Right: M=0, C=0 | Boat on Left
Step 1:
Left: M=5, C=2 | Right: M=0, C=3 | Boat on Right
Step 2:
Left: M=5, C=3 | Right: M=0, C=2 | Boat on Left
Step 3:
Left: M=5, C=0 | Right: M=0, C=5 | Boat on Right
Step 4:
Left: M=5, C=2 | Right: M=0, C=3 | Boat on Left
Step 5:
Left: M=2, C=2 | Right: M=3, C=3 | Boat on Right
Step 6:
Left: M=3, C=3 | Right: M=2, C=2 | Boat on Left
Step 7:
Left: M=0, C=3 | Right: M=5, C=2 | Boat on Right
Step 8:
Left: M=0, C=4 | Right: M=5, C=1 | Boat on Left
Step 9:
Left: M=0, C=1 | Right: M=5, C=4 | Boat on Right
Step 10:
Left: M=0, C=2 | Right: M=5, C=3 | Boat on Left
Step 11:
Left: M=0, C=0 | Right: M=5, C=5 | Boat on Right
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Input : N = 4, M = 2, K = 100
Output : There is no solution for these values.
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Input : N = 10, M = 4, K = 100
Output : 
Total Time : 1770100 nanoseconds
Step 0:
Left: M=10, C=10 | Right: M=0, C=0 | Boat on Left
Step 1:
Left: M=10, C=6 | Right: M=0, C=4 | Boat on Right
Step 2:
Left: M=10, C=7 | Right: M=0, C=3 | Boat on Left
Step 3:
Left: M=10, C=3 | Right: M=0, C=7 | Boat on Right
Step 4:
Left: M=10, C=6 | Right: M=0, C=4 | Boat on Left
Step 5:
Left: M=6, C=6 | Right: M=4, C=4 | Boat on Right
Step 6:
Left: M=7, C=7 | Right: M=3, C=3 | Boat on Left
Step 7:
Left: M=5, C=5 | Right: M=5, C=5 | Boat on Right
Step 8:
Left: M=6, C=6 | Right: M=4, C=4 | Boat on Left
Step 9:
Left: M=4, C=4 | Right: M=6, C=6 | Boat on Right
Step 10:
Left: M=5, C=5 | Right: M=5, C=5 | Boat on Left
Step 11:
Left: M=3, C=3 | Right: M=7, C=7 | Boat on Right
Step 12:
Left: M=4, C=4 | Right: M=6, C=6 | Boat on Left
Step 13:
Left: M=0, C=4 | Right: M=10, C=6 | Boat on Right
Step 14:
Left: M=0, C=5 | Right: M=10, C=5 | Boat on Left
Step 15:
Left: M=0, C=3 | Right: M=10, C=7 | Boat on Right
Step 16:
Left: M=0, C=4 | Right: M=10, C=6 | Boat on Left
Step 17:
Left: M=0, C=0 | Right: M=10, C=10 | Boat on Right
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Input : N = 3, M = 2, K = 100
Output :
Total Time : 443700 nanoseconds
Step 0:
Left: M=3, C=3 | Right: M=0, C=0 | Boat on Left
Step 1:
Left: M=2, C=2 | Right: M=1, C=1 | Boat on Right
Step 2:
Left: M=3, C=2 | Right: M=0, C=1 | Boat on Left
Step 3:
Left: M=3, C=0 | Right: M=0, C=3 | Boat on Right
Step 4:
Left: M=3, C=1 | Right: M=0, C=2 | Boat on Left
Step 5:
Left: M=1, C=1 | Right: M=2, C=2 | Boat on Right
Step 6:
Left: M=2, C=2 | Right: M=1, C=1 | Boat on Left
Step 7:
Left: M=0, C=2 | Right: M=3, C=1 | Boat on Right
Step 8:
Left: M=0, C=3 | Right: M=3, C=0 | Boat on Left
Step 9:
Left: M=0, C=1 | Right: M=3, C=2 | Boat on Right
Step 10:
Left: M=1, C=1 | Right: M=2, C=2 | Boat on Left
Step 11:
Left: M=0, C=0 | Right: M=3, C=3 | Boat on Right
## Authors

- [@Stamadianou_Maria | p3220194]
- [@Giannakopoulou_Stavroula | p3220027]

