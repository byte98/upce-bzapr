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
package cz.upce.skodaji.tut01;

/**
 * This file contains first tutorial of subject 'Basics of programming'
 * This is basically 'Hello world' kind of thing
 * Note: I don't wanna do this anymore...
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Main {
    /**
     * Main function of program
     * @param args Arguments of program
     */
    public static void main(String[] args) {
        System.out.println("Hello world");
        System.out.print("Inline output");
        System.out.println("New line output");
        
        // Inline comment
        /*
        Multi
        line
        comment
        */
        
        int a;
        int b = 7;
        a = 5;
        int c = a + b;
        int d, e;
        float f;
        d = c - a;
        e = a * c;
        f = (float)b / (float)a;
        
        
        System.out.println(a);
        System.out.println("Sum of a and b is " + c);
        System.out.println("d (c - a): " + d);
        System.out.println("e (a * c): " + e);
        System.out.println("f (b / a): " + f);
        
        int sideA = 10;
        int sideB = 5;
        
        sideA = sideA + 5;
        sideA += 5;
        
        
        int area = sideA * sideB;
        System.out.println("Area of rectangle is " + area);
        
        System.out.println("Side B: " + sideB);
        System.out.println("Side B: " + sideB++);
        System.out.println("Side B: " + (sideB + 1));

        
    }
}
