package gxg170000;

import java.util.*;

public class Num {
    static long defaultBase = 1000000000;  // Change as needed
    long base = 1000000000;  // Change as needed
    long[] arr;  // array to store arbitrarily large integers
    boolean isNegative;  // boolean flag to represent negative numbers
    int len;  // actual number of elements of array that are used;  number is stored in arr[0..len-1]

    public Num(){}

    public Num(String s) {
        int count = 0;
        long temp = base;
        while(temp>1){
            count++;
            temp = temp/10;
        }

        if(s.charAt(0)=='-'){
            s = s.substring(1);
            this.isNegative = true;
        }

        String reversedString = reverse(s);

        arr = new long[(int)Math.ceil((float)s.length()/count)];

        int j=0;

        for (int i = 0; i < reversedString.length(); i += count) {

            arr[j] =Long.parseLong(reverse(reversedString.substring(i, Math.min(s.length(), i+count))));
            j++;
        }
        len = arr.length;
        initializeOperatorWithPrecedence();
    }

    public Num(String s, long base){
        this.base = base;
        int count = 0;
        long temp = base;
        while(temp>1){
            count++;
            temp = temp/10;
        }

        if(s.charAt(0)=='-'){
            s = s.substring(1);
            this.isNegative = true;
        }

        String reversedString = reverse(s);

        arr = new long[(int)Math.ceil((float)s.length()/count)];

        int j=0;

        for (int i = 0; i < reversedString.length(); i += count) {

            arr[j] =Long.parseLong(reverse(reversedString.substring(i, Math.min(s.length(), i+count))));
            j++;
        }
        len = arr.length;
        initializeOperatorWithPrecedence();
    }

    public static String reverse(String s){
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        return sb.reverse().toString();
    }

    public Num(long x) {
        this(Long.toString(x));
    }

    private static Num checkSignAndDo(Num a, Num b, String op){
        if (op == "add") {
            if(!a.isNegative && b.isNegative){
                return _subtract(a,b);
            }
            else if(a.isNegative && !b.isNegative){
                return _subtract(b,a);
            }
            else if(a.isNegative && b.isNegative){
                Num res = _add(a, b);
                res.isNegative = true;
                return res;
            }
            else{
                return _add(a, b);
            }
        }
        else {
            if(!a.isNegative && b.isNegative){
                return _add(a,b);
            }
            else if(a.isNegative && !b.isNegative){
                Num res = _add(a, b);
                res.isNegative = true;
                return res;
            }
            else if(a.isNegative && b.isNegative){
                return _subtract(b,a);
            }
            else {
                return _subtract(a,b);
            }
        }
    }

    private static Num _add(Num a, Num b){
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

        Num result = new Num();
        result.len = res.length;
        result.arr = res;
        result.base = a.base;

        return result;
    }

    private static Num _subtract(Num a, Num b){
        boolean negative = false;
        long carry = 0;
        Num big, small;
        if(compareWithoutSign(a,b)==-1){
            negative = true;
            big = b;
            small = a;
        }
        else{
            big = a;
            small = b;
        }
        long[] res = new long[big.len];
        int k = 0;
        for(int i=0;i<big.len;i++){
            long temp = 0;
            if(i<small.len){
                temp = big.arr[i] - small.arr[i] - carry;
            }
            else {
                temp = big.arr[i] - carry;
            }
            if(temp>=0){
                res[k] = temp;
                carry = 0;
            }
            else{
                temp = temp + a.base;
                res[k] = temp;
                carry = 1;
            }
            k++;
        }
        Num subtractResult = new Num();
        subtractResult.base = a.base;
        subtractResult.len = res.length;
        subtractResult.arr = res;
        subtractResult.isNegative = negative;
        return subtractResult;
    }

    public static Num add(Num a, Num b) {
        if(a.base() != b.base()){
            throw new BaseDifferException("Base of both numbers must be same");
        }
        return checkSignAndDo(a, b, "add");
    }

    public static Num subtract(Num a, Num b) {
        if(a.base() != b.base()){
            throw new BaseDifferException("Base of both numbers must be same");
        }
        return checkSignAndDo(a, b, "subtract");
    }

    public static Num product(Num a, Num b) {
        if(a.base() != b.base()){
            throw new BaseDifferException("Base of both numbers must be same");
        }
        int l1 = a.len;
        int l2 = b.len;
        int l3 = Math.max(l1,l2);
        int l4 = Math.min(l1,l2);
        long temp = 0;
        long[] result = new long[l1+l2];
        Num res = new Num();
        res.base = a.base;
        int k=0;
        for(int i=0;i<l3;i++){
            for(int j=0;j<l4;j++){
                long sum1 = 0;
                if(l3 == l2){
                    sum1 = (a.arr[j]*b.arr[i]) + temp;
                }
                else{
                    sum1 = (b.arr[j]*a.arr[i]) + temp;
                }
                temp = sum1/defaultBase; // change 1000 to base
                sum1 = sum1%defaultBase; // change 1000 to base
                result[i+j] = result[i+j] + sum1;
                temp = temp + (result[i+j]/defaultBase);
                result[i+j] = result[i+j]%defaultBase;
                k = i + j;
            }
            result[k+1] = result[k+1] + temp;
            temp=0;
        }
        res.arr = result;
        res.len = result.length;
        if((a.isNegative && !b.isNegative)||(!a.isNegative && b.isNegative)){
            res.isNegative = true;
        }
        return res;
    }

    // Use divide and conquer
    public static Num power(Num a, long n) {
        if(n>1){
            long low = n/2;
            long high = n - low;
            return product(power(a,low), power(a, high));
        }
        return a;
    }

    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) {
        if(a.base() != b.base()){
            throw new BaseDifferException("Base of both numbers must be same");
        }
        Num start = new Num("0");
        Num end = a;
        boolean resSign = false;
        if((a.isNegative && !b.isNegative)||(!a.isNegative && b.isNegative)){
            resSign = true;
        }
        boolean asign = a.isNegative;
        boolean bsign = b.isNegative;
        a.isNegative = false;
        b.isNegative = false;

        while(start.compareTo(end)==-1){
            Num mid = Num.add(start, end).by2();
            Num comparator = Num.product(b, mid);
            if(comparator.compareTo(a)==1){
                end = mid;
            }
            else {
                start = Num.add(mid, new Num("1"));
            }
        }

        Num res =  Num.subtract(start, new Num("1"));
        res.isNegative = resSign;
        a.isNegative = asign;
        b.isNegative = bsign;
        return res;
    }

    // return a%b
    public static Num mod(Num a, Num b) {
        if(a.base() != b.base()){
            throw new BaseDifferException("Base of both numbers must be same");
        }
        Num quotient = Num.divide(a, b);
        if(quotient.isNegative && !b.isNegative){
            quotient.isNegative = false;
            return Num.add(a, Num.product(b, Num.add(quotient, new Num("1"))));
        }
        else if(quotient.isNegative && b.isNegative){
            return Num.subtract(a, Num.product(b, Num.subtract(quotient, new Num("1"))));
        }
        return Num.subtract(a, Num.product(b, quotient));
    }

    // Use binary search
    public static Num squareRoot(Num a) {
        if(a.isNegative) return new Num("0");
        Num start = new Num("0");
        Num answer = new Num();
        Num end = a;
        while (start.compareTo(end)==-1){
            Num mid = Num.add(start, end).by2();
            int comparator = Num.power(mid, 2).compareTo(a);
            if(comparator==0){
                return mid;
            }
            else if(comparator == -1){
                start = Num.add(mid, new Num("1"));
                answer.arr = mid.arr;
                answer.base = mid.base;
                answer.len = mid.len;
                answer.isNegative = mid.isNegative;
            }
            else {
                end = Num.subtract(mid, new Num("1"));
            }
        }
        return answer;
    }

    public static Num fibonacci(int n) {
        Num a = new Num(0);
        Num b = new Num(1);
        for(int i=0; i<n; i++) {
            Num c = Num.add(a,b);
            a = b;
            b = c;
        }
        return a;
    }


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
        return Num.compare(this, other);
    }

    public static int compare(Num a, Num b){
        a.removeTrailingZeros();
        b.removeTrailingZeros();
        if(a.isNegative && !b.isNegative) return -1;
        if(!a.isNegative && b.isNegative) return 1;
        if(a.isNegative && b.isNegative){
            if(a.len<b.len) return 1;
            if(a.len > b.len) return -1;
            for(int i=a.len-1;i>=0;i--){
                if(a.arr[i]< b.arr[i]){
                    return 1;
                }
                else if(a.arr[i]> b.arr[i]){
                    return -1;
                }
            }
            return 0;
        }
        else {
            return compareWithoutSign(a, b);
        }

    }

    private static int compareWithoutSign(Num a, Num b){
        a.removeTrailingZeros();
        b.removeTrailingZeros();
        if(a.len<b.len) return -1;
        if(a.len > b.len) return 1;
        for(int i=a.len-1;i>=0;i--){
            if(a.arr[i]< b.arr[i]){
                return -1;
            }
            else if(a.arr[i]> b.arr[i]){
                return 1;
            }
        }
        return 0;
    }


    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    public void printList() {
        if(this!=null){
            String s ="";
            System.out.print(base +": ");
            for(long i:arr){
                System.out.print(s+" " + i);
            }
            System.out.println();
        }
        else {
            System.out.println(0);
        }
    }

    public void removeTrailingZeros(){
        while (this.len>1 && this.arr[this.len-1]==0){
            this.len--;
        }
    }

    // Return number to a string in base 10
    public String toString() {
        String result = "";
        for(long i:this.arr){
            result = i + result;
        }
        StringBuffer sb = new StringBuffer(result);
        int i=0;
        while (i < result.length() && result.charAt(i) == '0'){
            i++;
        }
        if(result.length()>1){
            sb.replace(0, i, "");
        }
        if(this.isNegative){
            return "-"+sb.toString();
        }
        return sb.toString();
    }

    public long base() { return base; }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
        Num res = new Num(this.toString(), newBase);
        res.isNegative = this.isNegative;
        return res;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
        long carry = 0;
        long[] res = new long[this.len];
        Num result = new Num();
        for(int i=this.len-1;i>=0;i--){
            res[i] = (this.arr[i] + (this.base*carry))/2;
            carry = (this.arr[i])%2;
        }

        result.len = res.length;
        result.base = this.base;
        result.isNegative = this.isNegative;
        result.arr = res;
        return result;
    }

    static HashMap<String, Integer> operators = new HashMap<>();

    public static void initializeOperatorWithPrecedence(){
        operators.put("*",2);operators.put("+",1);operators.put("-",1);operators.put("/",2);
    }

    public static Num evaluate(Num x, Num y, String s){
        if(s=="*"){
            return product(x,y);
        }
        else if(s=="+"){
            return add(x,y);
        }
        else if(s=="-"){
            return subtract(x,y);
        }
        else if(s=="/"){
            return divide(x,y);
        }

        return null;
    }

    // Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluatePostfix(String[] expr) {
        Stack<Num> numberStack = new Stack<>();
        for(String s : expr){
            if(operators.containsKey(s)){
                if(!numberStack.isEmpty()){
                    Num y = numberStack.pop();
                    Num x = numberStack.pop();
                    Num result = evaluate(x,y, s);
                    if(result!=null){
                     numberStack.push(result);
                    }
                }

            }
            else{
                numberStack.push(new Num(s));
            }
        }
        return numberStack.pop();
    }

    // Evaluate an expression in infix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "(", ")", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluateInfix(String[] expr) {
        Stack<Num> numberStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();
        for(String s : expr){
            if(operators.containsKey(s)){

                while (!operatorStack.isEmpty() && operatorStack.peek()!="(" && operators.get(operatorStack.peek())>operators.get(s)){
                    String op = operatorStack.pop();
                    Num y = numberStack.pop();
                    Num x = numberStack.pop();
                    Num result = evaluate(x,y,op);
                    if(result!=null){
                        numberStack.push(result);
                    }
                }

                operatorStack.push(s);

            }
            else if(s=="("){
                operatorStack.push(s);
            }
            else if(s==")"){
                while(operatorStack.peek()!="("){
                    String op = operatorStack.pop();
                    Num x = numberStack.pop();
                    Num y = numberStack.pop();
                    Num result = evaluate(x,y,op);
                    if(result!=null){
                        numberStack.push(result);
                    }
                }
                operatorStack.pop();
            }
            else{
                numberStack.push(new Num(s));
            }
        }
        while (!operatorStack.isEmpty()){
            String op = operatorStack.pop();
            if(!numberStack.isEmpty()){
                Num x = numberStack.pop();
                Num y = numberStack.pop();
                Num result = evaluate(x,y,op);
                if(result!=null){
                    numberStack.push(result);
                }
            }

        }
        return numberStack.peek();
    }


    public static void main(String[] args) {
        Num x = new Num("1571");
        Num y = new Num("12");
        Num zz = new Num("1912991252611125159670459142926165956543555293");
        Num xx = Num.evaluatePostfix(new String[] { "98765432109876543210987654321",  "5432109876543210987654321", "345678901234567890123456789012", "*", "+", "246801357924680135792468013579", "*", "12345678910111213141516171819202122", "191817161514131211109876543210", "13579", "24680", "*", "-", "*", "+", "7896543", "*", "157984320", "+" });
        System.out.println();
        zz.convertBase(87654321).printList();
        System.out.println("y : "+ y.toString());

        System.out.println(x.isNegative);
        System.out.println(y.isNegative);

        System.out.println("Sum is : " + Num.add(x,y).toString());
        System.out.println("Difference is : " + Num.subtract(x,y).toString());
        System.out.println("Product is : " + Num.product(x,y).toString());
        System.out.println("Quotient is : " + Num.divide(x,y).toString());
        System.out.println("Remainder is : " + Num.mod(x,y).toString());

        System.out.println("Infix expression: "+ "100 * ( 2 + 12 ) / 14");
        System.out.println("Result: "+Num.evaluateInfix(new String[] {"100", "*", "(", "2", "+", "12", ")", "/", "14"}));

        System.out.println("Postfix expression: "+ "13 12 * 48 3 / - 66 *");
        System.out.println("PostFix Result : "+ Num.evaluatePostfix(new String[] {"13", "12", "*", "48", "3", "/", "-", "66", "+"}));
    }
}
