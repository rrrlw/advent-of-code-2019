import java.io.*;
import java.util.*;

public class Day4
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader fin = new BufferedReader(new FileReader("day4.in"));
		
		String[] vals = fin.readLine().split("-");
		fin.close();
		int minVal = Integer.parseInt(vals[0]),
			maxVal = Integer.parseInt(vals[1]);
			
		System.out.println(minVal + " " + maxVal);
		
		String curr;
		int counter1 = 0,
			counter2 = 0;
		ArrayList<Integer> tempAdj;
		for (int i = minVal; i <= maxVal; i++)
		{
			curr = Integer.toString(i);
			
			// already meets "within range" condition
			
			// six-digit number (should already meet this too)
			if (curr.length() != 6) continue;
			
			// left-to-right non-decreasing
			if (!nonDec(curr)) continue;
			
			// adjacent same
			tempAdj = adjacentLen(curr);
			if (tempAdj.size() > 0) counter1++;
			if (tempAdj.contains(Integer.valueOf(2))) counter2++;
		}
		
		System.out.println("Part 1: " + counter1 + "\nPart 2: " + counter2);
	}
	// ensure non-decreasing
	static boolean nonDec(String s)
	{
		for (int i = 1; i < s.length(); i++)
			if (s.charAt(i) < s.charAt(i-1))
				return false;
		return true;
	}
	// find longest repeating digit length
	static ArrayList<Integer> adjacentLen(String s)
	{
		int count = 0,
			tempI;
		ArrayList<Integer>ans = new ArrayList<Integer>();
		for (int i = 1; i < s.length(); i++)
		{
			count = 1;
			tempI = i;
			while (tempI < s.length() && s.charAt(tempI) == s.charAt(tempI - 1))
			{
				count++;
				tempI++;
			}
			i = tempI;
			if (count > 1)
				ans.add(count);
		}
		return ans;
	}
}
