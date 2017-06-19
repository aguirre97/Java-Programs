/*
Alexandra Aguirre
NID: al373180
COP3503
1-31-17
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class SneakyKnights
{
    public static boolean allTheKnightsAreSafe(ArrayList<String> coordinateStrings, int boardSize)
    {
        int n = coordinateStrings.size();
        int index = 0;
        int indexY = 0;

        HashSet<String> knightSpot = new HashSet<String>();

        for(int i = 0; i < n; i++)
        {
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

            // convert the number coordinates back into a string for the HashSet
            String posXY = position(indexX, indexY);

            // check if the knight is attackable
            if(knightSpot.contains(posXY))
                return false;
        
            /*
            The next 8 if statements are checking if the knight's attackable positions are in in bounds
            if they are, mark the spot
            */

            if(inBounds(indexX-1, indexY+2, boardSize))
            {
                String up2left1 = position(indexX-1, indexY+2);
                knightSpot.add(up2left1);
            }
            
            if(inBounds(indexX+1, indexY+2, boardSize))
            {
                String up2right1 = position(indexX+1, indexY+2);
                knightSpot.add(up2right1);
            }
           
            if(inBounds(indexX-1, indexY-2, boardSize))
            {
                String down2left1 = position(indexX-1, indexY-2);
                knightSpot.add(down2left1);
            }
            
            if(inBounds(indexX+1, indexY-2, boardSize))
            {
                String down2right1 = position(indexX+1, indexY-2);
                knightSpot.add(down2right1);
            }
            
            if(inBounds(indexX-2, indexY+1, boardSize))
            {
                String up1left2 = position(indexX-2, indexY+1);
                knightSpot.add(up1left2);
            }
            
            if(inBounds(indexX+2, indexY+1, boardSize))
            {
                String up1right2 = position(indexX+2, indexY+1);
                knightSpot.add(up1right2);
            }
              
            if(inBounds(indexX-2, indexY-1, boardSize))
            {
                String down1left2 = position(indexX-2, indexY-1);
                knightSpot.add(down1left2);
            }
            
            if(inBounds(indexX+2, indexY-1, boardSize))
            {
                String down1right2 = position(indexX+2, indexY-1);
                knightSpot.add(down1right2);
            }
        }

        // program has run through all the knights and they are all safe
        return true;
    }

    // checking if the position is within the board
    public static boolean inBounds(int indexX, int indexY, int boardSize)
    {
        // coordinates cannot be negative or greater than the boardSize
        if(indexX < 1 || indexX > boardSize || indexY < 1 || indexY > boardSize)
            return false;
        
        return true;
    }

    // converting the (x,y) coordinate into a string 
    // to later place in the HashSet
    public static String position(int indexX, int indexY)
    {
        String posX = Integer.toString(indexX);
        String posY = Integer.toString(indexY);

        String posXY = posX + ',' + posY;

        return posXY;
    }


    public static double difficultyRating()
    {
        return 3.7;
    }

    public static double hoursSpent()
    {
        return 8;
    }
}