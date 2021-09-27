/*
 * Copyright (C) 2021 Jiri Skoda <jiri.skoda@student.upce.cz>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.upce.fei.skodaj.bzapr.tut02;

import java.util.Scanner;

/**
 * Entry class of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Main {
    
    /**
     * Main function of program
     * @param args Arguments of program
     */
    public static void main(String[] args) {
        /*
        String firstString = "fs";
        int lengthOfString = firstString.length();
        System.out.println("First string: " + firstString);
        System.out.println("Length of first string: " + lengthOfString);        
        */
        Scanner scanner = new Scanner(System.in);
        
        /*
        // Input will be number
        System.out.println("Enter number:");
        int userInput = scanner.nextInt();
        System.out.println("You've entered: " + userInput);
        */
        // Input will be string
        /*
        System.out.println("Enter input: ");
        String userInput = scanner.next();
        System.out.println("You've entered: " + userInput);
        */
        
        // Input will be number
        /*
        System.out.println("Enter number:");
        int userInput = scanner.nextInt();
        if (userInput > 5)
        {
            System.out.println("You've entered number greater than 5.");
        }
        else
        {
            System.out.println("You haven't entered number greater than 5.");
        }
        
        switch (userInput)
        {
            case 1:
                System.out.println("You've entered number one.");
                break;
            case 2:
                System.out.println("You've entered number two.");
                break;
            default:
                System.out.println("You have entered neither one nor two.");
        }
        */
        
        // Simple calculator
        double operand1, operand2;
        System.out.println("Enter first operand:");
        operand1 = scanner.nextDouble();
        System.out.println("Enter second operand:");
        operand2 = scanner.nextDouble();
        String operation;
        System.out.println("Enter operation [+, -, *, /]:");
        operation = scanner.next();
        while (!"+".equals(operation) && !"-".equals(operation) && !"*".equals(operation) && !"/".equals(operation))
        {
            System.out.println("Enter operation [+, -, *, /]:");
            operation = scanner.next();
        }
        double result = 0;
        switch (operation)
        {
            case "+": result = operand1 + operand2; break;
            case "-": result = operand1 - operand2; break;
            case "*": result = operand1 * operand2; break;
            case "/": result = operand1 / operand2; break;
        }
        System.out.println("Result is: " + result);
        
        double sum = operand1 + operand2;
        System.out.println("Second power: " +  Math.pow(sum, 2));
        if (sum >= 0)
        {
            System.out.println("Square root: " + Math.sqrt(sum));
        }
        
    } 
}
