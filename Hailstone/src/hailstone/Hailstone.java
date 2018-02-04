/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hailstone;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roope Niemi
 */
public class Hailstone {

    private static String outputFile = "outputFile.html";

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        try {
            System.out.print("Number: ");
            int inputNumber = Integer.parseInt(inputScanner.nextLine());
            if (inputNumber <= 0 || inputNumber >= 1000000) {
                throw new Exception();
            }
            calculateHailstone(inputNumber);
        } catch (NumberFormatException e) {
            System.out.println("Input must be a number");
            System.exit(1);
        } catch (Exception ex) {
            System.out.println("Number must be between 1 and 999 999");
            System.exit(1);
        }
    }

    /*Creates an empty list which is used in mapping the steps done when calculating
    Hailstone sequence. Calls all the other functions, to calculate required values,
    and output the results to an HTML-file.*/
    public static void calculateHailstone(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(n);
        list = calculate(list, n);
        int steps = list.size() - 1;
        int secondHighest = findSecondHighest(list);
        createHTML(n, secondHighest, steps);
    }

    /*Finds the second highest number. Uses two integers to calculate this.
    Goes throught the list once. Everytime a number in the list is higher
    than the integer 'first', second integer takes the first integer's value, and
    first takes the list item's value as its value. If the number in the list 
    is between first and second integer's values, second integer get's that number's
    value. Returns the second integer*/
    public static int findSecondHighest(ArrayList<Integer> list) {
        int first = Integer.MIN_VALUE;
        int second = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > first) {
                second = first;
                first = list.get(i);
            } else if (list.get(i) > second && list.get(i) < first) {
                second = list.get(i);
            }
        }
        return second;
    }

    /*Recursive function. Stops when parameter number is 1. If parameter is divisible by
    2, divides the parameter by two. If not divisible by two, new parameter value is  
    3 * parameter value +1. Adds the new value to the list and calls itself recursively with
    the new  number value.*/
    public static ArrayList<Integer> calculate(ArrayList<Integer> list, int number) {
        if (number == 1) {
            return list;
        }
        if (number % 2 == 0) {
            number = number / 2;
            list.add(number);
            calculate(list, number);
        } else {
            number = (3 * number) + 1;
            list.add(number);
            calculate(list, number);
        }
        return list;
    }

    // Creates an HTML file which contains information about the input number, steps taken, and the second highest number
    public static void createHTML(int inputNumber, int secondHighest, int steps) {
        ArrayList<String> fileContent = new ArrayList<>();
        fileContent.add("<!DOCTYPE html>");
        fileContent.add("<html>");
        fileContent.add("<body>");
        fileContent.add("<p> Input number: " + inputNumber + "</p>");
        fileContent.add("<p> Steps taken: " + steps + "</p>");
        fileContent.add("<p> Second highest number: " + secondHighest + "</p>");
        fileContent.add("</body>");
        fileContent.add("</html>");
        try {
            Files.write(Paths.get(outputFile), fileContent, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(Hailstone.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
