package gxg170000;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class ShuntingYard {

    Stack<Character> operatorStack = new Stack<>();

    Queue<Character> outputQueue = new LinkedList<>();

    public enum priority {
        EXP,FACT,MULT,DIV,ADD,SUB
    }

    public void popPush(char c){
        char popped = operatorStack.pop();
        outputQueue.offer(popped);
        parseOperator(c);
        operatorStack.push(c);
    }

    public void parseOperator(char c){
        if(operatorStack.isEmpty() || c =='(' || c =='^'){
            operatorStack.push(c);
        }
        else if(c == ')'){
            char popped = operatorStack.pop();
            while (popped!='('){
                outputQueue.offer(popped);
                popped = operatorStack.pop();
            }
        }
        else {
            char head = operatorStack.peek();
            if(c=='!' && head == '^'){
                popPush(c);
            }
            else if(c == '*' && (head == '^' || head == '!')){

            }
            switch (c){
                case '!':
                    if(head == '^'){
                        popPush(c);
                    }
                    else{
                        operatorStack.push(c);
                    }
                    break;
                case '*':
                    if(head == '^' || head == '!'){
                        popPush(c);
                    }
                    else{
                        operatorStack.push(c);
                    }
                    break;
                case '/':
                    if(head == '^' || head == '!' || head == '*'){
                        popPush(c);
                    }
                    else{
                        operatorStack.push(c);
                    }
                    break;
            }
        }
    }

    public String parseExpression(String token){
        if(token == "") return token;
        for(char c : token.toCharArray()){
            if(Character.isDigit(c)){
                outputQueue.offer(c);
            }
            else {
                parseOperator(c);
            }
        }
        String postfix = "abc";
        return postfix;
    }

    public static void main(String[] args) {
        ShuntingYard shuntingYard = new ShuntingYard();
        Scanner sc = new Scanner(System.in);
        System.out.println();
    }
}
