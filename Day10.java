import java.io.*;
import java.util.*;

public class Day10
{
	public static void main(String[] args) throws IOException
	{
		// input
		Scanner fin = new Scanner(new File("day10.in"));
		
		ArrayList<String> grid = new ArrayList<String>();
		while (fin.hasNextLine())
			grid.add(fin.nextLine());
			
		fin.close();
		
		// setup grid
		boolean[][] asteroid = new boolean[grid.size()][grid.get(0).length()];
		for (int i = 0; i < grid.size(); i++)
			for (int j = 0; j < grid.get(i).length(); j++)
				asteroid[i][j] = (grid.get(i).charAt(j) == '#');
		
		// find number of asteroids each spot can see
		int[][] counter = new int[asteroid.length][asteroid[0].length];
		for (int i = 0; i < counter.length; i++)
			for (int j = 0; j < counter[i].length; j++)
				for (int k = 0; k < asteroid.length; k++)
					for (int l = 0; l < asteroid[k].length; l++)
						if (i == k && j == l) continue;
						else if (asteroid[i][j] && asteroid[k][l] && sight(k, l, i, j, asteroid))
							counter[i][j]++;
		
		// find spot that can see max number of asteroids; save coordinates for part 2
		int maxVal = 0,
			maxI = -1, maxJ = -1;
		for (int i = 0; i < counter.length; i++)
			for (int j = 0; j < counter[i].length; j++)
				if (maxVal < counter[i][j])
				{
					maxVal = counter[i][j];
					maxI = i;
					maxJ = j;
				}
					
		// part 1 answer
		System.out.println("Part 1: " + maxVal + " " + maxI + "," + maxJ);
		
		// get angles for all asteroids, sort
		ArrayList<Double>angles = new ArrayList<Double>();
		double angle;
		double[][] hit = new double[asteroid.length][asteroid[0].length];
		for (int i = 0; i < hit.length; i++)
			for (int j = 0; j < hit[i].length; j++)
				if (asteroid[i][j] && sight(i, j, maxI, maxJ, asteroid) &&
					(i != maxI || j != maxJ))
				{
					hit[i][j] = getAngle(maxI, maxJ, i, j);
					angles.add(hit[i][j]);
				}
		
		Collections.sort(angles);
		
		// find asteroid equal to the 200th angle & print answer as described in problem statement
		for (int i = 0; i < hit.length; i++)
			for (int j = 0; j < hit[i].length; j++)
				if (asteroid[i][j] && sight(i, j, maxI, maxJ, asteroid) &&
					(i != maxI || j != maxJ))
					if (equals(getAngle(maxI, maxJ, i, j), angles.get(199), 1e-6))
					{
						System.out.println(j * 100 + i);
						return ;
					}
	}
	// equality check for doubles (within eps of each other)
	static boolean equals(double a, double b, double eps)
	{
		return (Math.abs(a - b) < eps);
	}
	// calculate angle (bearing: north = 0, south = pi) between two coordinates relative to vertical
	static double getAngle(int sourceI, int sourceJ, int targetI, int targetJ)
	{
		double yDiff = targetI - sourceI,
			   xDiff = targetJ - sourceJ;
		double angle = Math.atan(yDiff / xDiff);
		if (targetJ < sourceJ)
			angle += Math.PI;
		
		
		
		return angle + Math.PI / 2.0;
	}
	// find out if two asteroids can see each other
	// x and y are switched up :/
	static boolean sight(int sX, int sY, int eX, int eY,
						 boolean[][] grid)
	{
		// really shouldn't have this statement here
		if (sX == eX && sY == eY) return true;
		
		int xDiff = eX - sX,
			yDiff = eY - sY,
			gcdXY = gcd(Math.abs(xDiff), Math.abs(yDiff));
		int slopeX,
			slopeY;
		if (gcdXY == 0)
		{
			slopeX = 0;
			slopeY = 0;
		}
		// no asteroid can be exactly in the way if gcd == 1
		else if (gcdXY == 1)
			return true;
		else
		{
			slopeX = xDiff / gcdXY;
			slopeY = yDiff / gcdXY;
		}
		
		// check if there's any asteroids in the way
		int currX = sX + slopeX,
			currY = sY + slopeY;
		while (currX != eX || currY != eY)
		{
			if (grid[currX][currY])
			{
				return false;
			}
			
			currX += slopeX;
			currY += slopeY;
		}
		return true;
	}
	static int gcd(int a, int b)
	{
		return (b == 0) ? a : gcd(b, a % b);
	}
}
