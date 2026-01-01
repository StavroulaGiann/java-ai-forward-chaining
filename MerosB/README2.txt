# Forward Chaining In Propositional Logic

In this problem, we insert a knowledge base from a txt file and a premise (in double quotes) and the program returns if we can produce it from the knowledge base or not.


## Running Tests

To run tests, run the following command

```bash
  java ForwardChaining <file.txt(in following examples im putting the actual knowledge base instead of file.txt)> "query"
```


## Usage/Examples

```javascript
Input : <A -> B
	B -> C
	-> A   >   "C"
Output :Is "C" entailed? true

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Input : <X -> Y
	Z -> W
	-> X   >    "W"
Output :Is "W" entailed? false

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Input :<A -> B
	B -> C
	-> A   >   "D"
Output :Is "D" entailed? false

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Input :<A,B -> C
	C,D -> E
	-> A
	-> B
	-> D     > "E"
Output :Is "E" entailed? true

# Forward Chaining In First Order Categorical Logic

In this problem, we insert a knowledge base from a txt file and a premise (in double quotes) and the program returns if we can produce it from the knowledge base or not.


## Running Tests

To run tests, run the following command

```bash
  java ForwardChainingFOL <file.txt(in following examples im putting the actual knowledge base instead of file.txt)> "query"
```


## Usage/Examples

```javascript
Input : <Fever(John)
	Fever(x) => Sick(x)
	Cough(John)
	Fever(x) ^ Cough(x) => Flu(x)> "Flu(John)"
Output :Can we infer "Flu(John)"? YES

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Input : <Fever(John)
	Fever(x) => Sick(x)
	Cough(John)
	Fever(x) ^ Cough(x) => Flu(x)> "Flu(John)"
Output :Can we infer "Flu(Alice)"? NO

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Input :<Bird(Tweety)
	Bird(x) => CanFly(x)
	Penguin(Pingu)
	Penguin(x) => Bird(x)
	Penguin(x) ^ Bird(x) => -CanFly(x)> "CanFly(Pingu)"
Output :Can we infer "CanFly(Pingu)"? NO

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Input :<Bird(Tweety)
	Bird(x) => CanFly(x)
	Penguin(Pingu)
	Penguin(x) => Bird(x)
	Penguin(x) ^ Bird(x) => -CanFly(x)> "CanFly(Pingu)"
Output :Can we infer "CanFly(Tweety)"? YES

## Authors

- [@Stamadianou_Maria | p3220194]
- [@Giannakopoulou_Stavroula | p3220027]