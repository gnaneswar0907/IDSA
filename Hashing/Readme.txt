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