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
package cz.upce.fei.skodaj.bzapr.tut03;

/**
 * Main class of third tutorial
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Main
{
    /**
     * Main function of the program
     * @param args Arguments of program
     */
    public static void main(String[] args)
    {
        // For cycle
        for (int i = 0; i < 10; i++)
        {
            // Nested for cycle
            for (int j = 0; j < 10; j++)
            {
                System.out.println(j);
            }
        }
        
        // While cycle
        int a = 0;
        while(a < 10)
        {
            System.out.println("a = " + a);
            a++;
        }
        
        // Do-while cycle
        a = 10;
        do 
        {
            System.out.println("a = " + a);
            a++;
        }
        while (a < 10);
        
        // While for checking user input
        java.util.Scanner sc = new java.util.Scanner(System.in);        
        String answer = "yes";
        while (answer.equals("yes"))
        {
            System.out.println("Enter number:");
            a = sc.nextInt();
            System.out.println("You've entered number: " + a);
            System.out.println("Do you want continue?");
            answer = sc.next();
        }
        
        // Break & continue
        for (int i = 0; i < 10; i++)
        {
            if (i == 5)
            {
                continue;
            }
            else if (i == 8)
            {
                break;
            }
            System.out.println("i = " + i);
        }
        
        // Factorial calculator
        int number = 4;
        int factorial = 1;
        for (int i = number; i > 0; i--)
        {
            factorial *= i;
        }
        System.out.println("Factorial of " + number + " is: " + factorial);
        
        // Sum of natural numbers
        int min, max;
        do 
        {
            System.out.println("Enter NATURAL number (min):");
            min = sc.nextInt();
            if (min < 0)
            System.out.println("Entered number is not natural!");
        }
        while (min < 0);
        do 
        {
            System.out.println("Enter NATURAL number (max):");
            max = sc.nextInt();
            if (max < 0)
            System.out.println("Entered number is not natural!");
        }
        while (max < 0);
        int sum = 0;
        for (int i = min; i <= max; i++)
        {
            sum += i;
        }
        System.out.println("Sum of numbers from " + min + " to " + max + " is: " + sum);
        
        //Average calculactor
        int avgCount = 0;
        int avgSum = 0;
        int input;
        do 
        {
            System.out.println("Enter number:");
            input = sc.nextInt();
            if (input == 0)
            {
                break;
            }
            avgCount++;
            avgSum += input;
        }
        while (input != 0);
        System.out.println("Average value of entered inputs is: " + (double)((double)avgSum / (double)avgCount));
        
        // Small multiplicators
        for (int i = 0; i <= 10; i++)
        {
            for (int j = 0; j <= 10; j++)
            {
                System.out.print(i * j + " ");
            }
            System.out.println("");
        }
    }
}
