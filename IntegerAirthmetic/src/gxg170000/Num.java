package gxg170000;

public class Num {
    static long defaultBase = 1000;  // Change as needed
    long base = 1000;  // Change as needed
    long[] arr;  // array to store arbitrarily large integers
    boolean isNegative;  // boolean flag to represent negative numbers
    int len;  // actual number of elements of array that are used;  number is stored in arr[0..len-1]

    public Num(String s) {
        int count = 0;
        long temp = base;
        while(temp>1){
            count++;
            temp = temp/10;
        }

        String reversedString = reverse(s);

        arr = new long[(s.length()/count) +1];

        int j=0;

        for (int i = 0; i < reversedString.length(); i += count) {

            arr[j] =Long.parseLong(reverse(reversedString.substring(i, Math.min(s.length(), i+count))));
            j++;
        }

        len = arr.length;
    }

    public static String reverse(String s){
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        return sb.reverse().toString();
    }

    public Num(long x) {
        this(Long.toString(x));
    }

    public static Num add(Num a, Num b) {
        int l1 = a.len;
        int l2 = b.len;
        long temp = 0;
        long[] res = new long[Math.max(l1,l2)+1];
        for(int i=0;i<Math.max(l1,l2);i++){
            if(i<Math.min(l1,l2)){
                temp = temp + a.arr[i]+b.arr[i];
            }
            else{
                if(i>=l1){
                    temp = temp + b.arr[i];
                }
                else{
                    temp = temp + a.arr[i];
                }
            }
            if(temp>defaultBase-1){
                res[i] = temp - defaultBase;//Change 1000 to base
                temp = 1;
            }
            else{
                res[i] = temp;
                temp = 0;
            }
        }

        long result = 0;

        for(int i=0;i<res.length;i++){
            result = result + (res[i]* ((long)(Math.pow(a.base,i)))); // might not work for large numbers
        }
        return new Num(Long.toString(result));
    }

    public static Num subtract(Num a, Num b) {
//        int l1 = a.len;
//        int l2 = b.len;
//        long temp = 0;
//        long[] res = new long[Math.max(l1,l2)];
//        for(int i=0;i<Math.max(l1,l2);i++){
//            if(a.arr[i]<b.arr[i]){
//                for(int j=i-1; j>0;j--){
//
//                }
//            }
//            else {
//                res[i] = a.arr[i] - b.arr[i];
//                temp = 0;
//            }
//        }
//
//        System.out.print("over");
        return null;
    }

    public static Num product(Num a, Num b) {
        int l1 = a.len;
        int l2 = b.len;
        int l3 = Math.max(l1,l2);
        int l4 = Math.min(l1,l2);
        long temp = 0;
        long multiSum = 0;
        String multi = "";
        for(int i=0;i<l3;i++){
            for(int j=0;j<l4;j++){
                long sum1 = 0;
                if(l3 == l2){
                    sum1 = (a.arr[j]*b.arr[i]) + temp;
                }
                else{
                    sum1 = (b.arr[j]*a.arr[i]) + temp;
                }
                if(j<l4-1){
                    temp = sum1/defaultBase; // change 1000 to base
                    sum1 = sum1%defaultBase; // change 1000 to base
                }
                multi = sum1 + multi;
            }
            temp=0;
            multiSum = multiSum + ((Long.parseLong(multi) ) * (long) Math.pow(defaultBase,(double) i));
            multi = "";
        }
        return new Num(Long.toString(multiSum));
    }

    // Use divide and conquer
    public static Num power(Num a, long n) {
        return null;
    }

    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) {
        return null;
    }

    // return a%b
    public static Num mod(Num a, Num b) {
        return null;
    }

    // Use binary search
    public static Num squareRoot(Num a) {
        return null;
    }


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
        return 0;
    }

    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    public void printList() {
        String s ="";
        System.out.print(base +": ");
        for(long i:arr){
//            System.out.print(i+s);
            s = i +s;
        }
        System.out.print(s);
    }

    // Return number to a string in base 10
    public String toString() {
        return null;
    }

    public long base() { return base; }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
        return null;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
        long temp = 0;
        long[] res = new long[len];
        int j = 0;
        for(int i=len-1;i>=0;i--){
            res[j] = (arr[i] + (temp * (long)(Math.pow(1000, (double)(i+1)))))/2;
            temp = arr[i]%2;
            j++;
        }
        temp = 0;
        j=0;
        for(int i=res.length-1;i>=0;i--){
            if(res[i]!=0){
                temp = temp + (res[i]* (long)(Math.pow(1000, (long)j)));
                j++;
            }
        }
        return new Num(Long.toString(temp));
    }

    // Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluatePostfix(String[] expr) {
        return null;
    }

    // Evaluate an expression in infix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "(", ")", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluateInfix(String[] expr) {
        return null;
    }


    public static void main(String[] args) {
        Num x = new Num("12345678912123456");
        Num y = new Num("12345678912123456");
//        System.out.println(1313%2139);
//        x.by2();
//        System.out.print(Math.pow(2, 31));
//        Num sum = Num.product(x,y);
//        sum.printList();
//        long result = 0;
//

//        System.out.print("oo");
        Num z = Num.product(x, y);
//        System.out.println(z);
//        Num a = Num.power(x, 8);
//        System.out.println(a);
        if(z != null) z.printList();
    }
}
