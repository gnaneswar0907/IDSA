```Group Members
   Gnaneswar Gandu - gxg170000
   Rutuj Ravindra Puranik - rxp180014```

 Steps to Run the program -
    1. Extract the contents of the zip file into a folder.
    2. After changing the working directory to current directory where we unzipped the files, compile the file DoubleHashing.java in the terminal using `javac DoubleHashing.java`.
    3. After that compile the file Driver.java file using `javac Driver.java`.
    4. Next run the program by running the command `java Driver`.
    5. Enter the choice of Hashing Implementation.
    6. It will add the elements and Run Time and Memory will be calculated and displayed

 Inference:
    1. We have started building hash table with initial capacity = 1024, (unlike Java HashMap/ HashSet capacity = 16).
       This can be attributed to the fact that this implementation 'look' is efficient as compared to Java HashSet.
    2. I've tested these implementations only for n <= 10 Million. You can always compare the performances for larger n.
    3. With load factor = 0.75, DoubleHashing was taking very long time. Hence was unable to write the Run time and Memory results for n > 2 Million.
    4. Due to time constraints, we were not able to implement the other hashing implementations, but will try to implement them in practising.

    The Table of Results -

    +------------+-----------------------------+------------------------------+----------------------------+
    |            |         Java HashSet        |        Double Hashing        |       Double Hashing       |
    | Array Size |                             |      (Load Factor = 0.5)     |    (Load Factor = 0.75)    |
    |            +-----------+-----------------+-----------+------------------+----------+-----------------+
    |            |    Time   |      Memory     |    Time   |      Memory      |   Time   |      Memory     |
    +------------+-----------+-----------------+-----------+------------------+----------+-----------------+
    |  1 Million |  270 mSec |  77 MB/ 207 MB  |  330 mSec |  180 MB/ 260 MB  | 357 mSec |  162 MB/ 388 MB |
    +------------+-----------+-----------------+-----------+------------------+----------+-----------------+
    |  2 Million |  736 mSec |  298 MB/ 805 MB |  841 mSec |  515 MB/ 747 MB  | 924 mSec | 533 MB/ 1152 MB |
    +------------+-----------+-----------------+-----------+------------------+----------+-----------------+
    |  3 Million | 1236 mSec |  293 MB/ 825 MB | 1215 mSec |  220 MB/ 524 MB  |     Time Limit Exceeded    |
    +------------+-----------+-----------------+-----------+------------------+----------------------------+
    |  5 Million | 2259 mSec |  287 MB/ 657 MB | 2329 mSec |  345 MB/ 1065 MB |     Time Limit Exceeded    |
    +------------+-----------+-----------------+-----------+------------------+----------------------------+
    |  8 Million | 3463 mSec | 485 MB/ 1217 MB | 4023 mSec |  627 MB/ 1552 MB |     Time Limit Exceeded    |
    +------------+-----------+-----------------+-----------+------------------+----------------------------+
    | 10 Million | 4615 mSec | 985 MB/ 1733 MB | 5154 mSec | 1136 MB/ 1828 MB |     Time Limit Exceeded    |
    +------------+-----------+-----------------+-----------+------------------+----------------------------+