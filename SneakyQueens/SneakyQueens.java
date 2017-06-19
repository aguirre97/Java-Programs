// Alexandra Aguirre
// COP3503
// 1-15-17

/*
Assignment for Dr. Szumlanski, UCF - Computer Science 2.
Will be given a list of coordinate strings for queens on an arbitrarily large square
chess board, and must determine whether any of the queens can attack one another
in the given configuration.
 */

import java.io.*;
import java.lang.*;
import java.util.*;

public class SneakyQueens
{
    public static boolean allTheQueensAreSafe(ArrayList<String> coordinateStrings, int boardSize)
    {
        // initialize variables
        int n = coordinateStrings.size();
        int index = 0;
        int indexY = 0;
        int posB = 0;
        int negB = 0;

        // create an array for each direction (horizontal, vertical, 2 diagonals) and an array for a negative B intercept
        int[] arrX = new int[2*boardSize];
        int[] arrY = new int[2*boardSize];
        int[] arrB = new int[2*boardSize];
        int[] arrnegB = new int[2*boardSize];
        int[] arrnegY = new int[2*boardSize];

        // loop through each coordinateStrings
        for(int i = 0; i < n; i++)
        {
            // breakdown the strings into an array of chars
             char[] arrChar = coordinateStrings.get(i).toCharArray();
             int l = arrChar.length;

             // loop through the char array
             for(int j = 0; j < l; j++)
             {
                 
                 // if we can parse the int, the x-coordinate (letters of the array) have finished
                 try
                 {
                     // convert the char into a string for the next step
                     String b = Character.toString(arrChar[j]);

                     // parse the string into an integer
                     int testY = Integer.parseInt(b);

                     // index is where the y-coordinate (numbers of the array) start
                     index = j;

                     // we have finished the x-coordinate and must terminate the loop
                     j=l;

                 }
                 catch(Exception e)
                 {
                   // no code needed inside
                   // exception means we are still in the x-coordinate

                 }
             }

            // store the char array back into a string
            String arr = String.valueOf(arrChar);

            // initialize the x-coordinate here so it starts as 0 for every new String
            int indexX = 0;

            // convert the chars into position numbers for x-coordinate
            // total them to be the correct position number
            for(int j = 0; j < index; j++)
            {
                int pos = arrChar[j] - 'a' + 1;
                int sum = pos * (int)Math.pow(26,index-j-1);
                indexX += sum;
            } 

            // get the correct y-coordinate
            indexY = Integer.parseInt(arr.substring(index));

            // to check diagonals, can use the formula y = m*x + b
            // if m = 1 then 
            posB = indexY - indexX;

            // cannot have a negative index in an array, but can have a negative B intercept
            // if intercept is negative, put it in the negY array for safe keeping
           if(posB < 0)
            {
                posB = (-1) * posB;

                if(arrnegY[posB] == 1)
                {
                    return false;
                }
                arrnegY[posB] = 1;
            }
            // B intercept is not negative
            else{ 
                if(arrB[posB] == 1)
                {
                    return false;
                }
                arrB[posB] = 1;
            }

            // if m = -1 then
            negB = indexY + indexX;
            
            // checking to make sure no queen can attack each other
            // if the element at that index is 1 then a queen is already there
            if(arrX[indexX] == 1)
            {
                return false;
            }
            if(arrY[indexY] == 1)
            {
                return false;
            }
            if(arrnegB[negB] == 1)
            {
                return false;
            }
            // if none are a match, put the queen in that index spot
            else
            {
                arrX[indexX] = 1;
                arrY[indexY] = 1;
                arrnegB[negB] = 1;
            }
        }
        // program has run through all the queens and they are all safe
        return true;
    }

    public static double difficultyRating()
    {
        return 4.3;
    }

    public static double hoursSpent()
    {
        return 7;
    }
}