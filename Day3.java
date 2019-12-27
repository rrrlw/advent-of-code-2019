import java.io.*;
import java.util.*;

public class Day3
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader fin = new BufferedReader(new FileReader("day3.in"));
		
		String[] w1 = fin.readLine().split(","),
				 w2 = fin.readLine().split(",");
				 
		//print(w1); print(w2);
		
		ArrayList<Piece> wire1 = convert(w1),
						 wire2 = convert(w2);
						 
		//System.out.println(wire1); System.out.println(wire2);
						 
		ArrayList<Spot> intersections = new ArrayList<Spot>();
		Spot temp;
		for (int i = 0; i < wire2.size(); i++)
		{
			for (int j = 0; j < wire1.size(); j++)
			{
				temp = wire2.get(i).intersects(wire1.get(j));
				if (temp != null && temp.x != 0 && temp.y != 0) intersections.add(temp);
			}
		}
		
		//System.out.println(intersections);
		
		//part 1: min manhattan distance
		//part 2: least steps before
		int minManhattan = Integer.MAX_VALUE,
			minBefore = Integer.MAX_VALUE;
		for (Spot s : intersections)
		{
			if (minManhattan > Math.abs(s.x) + Math.abs(s.y))
				minManhattan = Math.abs(s.x) + Math.abs(s.y);
			if (s.stepsBefore >= 0 && s.stepsBefore < minBefore)
				minBefore = s.stepsBefore;
		}
		
		System.out.println("Part 1: " + minManhattan + "\nPart 2: " + minBefore);
	}
	static void print(String[] s)
	{
		System.out.print(s[0]);
		for (int i = 1; i < s.length; i++)
			System.out.print(" " + s[i]);
		System.out.println();
	}
	static ArrayList<Piece> convert(String[] w)
	{
		Piece curr;
		ArrayList<Piece> ans = new ArrayList<Piece>();
		for (int i = 0; i < w.length; i++)
		{
			if (i == 0)
			{
				curr = new Piece(null, w[i].charAt(0), Integer.parseInt(w[i].substring(1)));
				ans.add(curr);
			}
			else
			{
				curr = new Piece(ans.get(i - 1), w[i].charAt(0), Integer.parseInt(w[i].substring(1)));
				ans.add(curr);
			}
		}
		return ans;
	}
	static class Spot
	{
		public int x, y, stepsBefore;
		public Spot(int x, int y) {this(x, y, 0);}
		public Spot(int x, int y, int stepsBefore) {this.x = x; this.y = y; this.stepsBefore = stepsBefore;}
		public String toString()
		{
			return (x + " " + y + " " + stepsBefore + "\n");
		}
	}
	static class Piece
	{
		public int startX,
				   startY,
				   endX,
				   endY,
				   stepsBefore,
				   len;
				   
		public String toString()
		{
			return (startX + " " + startY + " " + endX + " " + endY + " " + stepsBefore + " " + len + "\n");
		}
		
		public Spot intersects(Piece other)
		{
			//assumption: no multiple consecutive overlap (can't have two horizontal overlapping pieces)
			if (this.vertical() == other.vertical())
				return null;
				
			//this piece is vertical
			if (this.vertical())
			{
				if (Math.abs(startX - other.startX) > other.len ||
					Math.abs(startX - other.endX) > other.len) return null;
				if (Math.abs(other.startY - startY) > len ||
					Math.abs(other.startY - endY) > len) return null;
					
				Spot temp = new Spot(this.startX, other.startY);
				temp.stepsBefore = totalStepsBefore(temp) + other.totalStepsBefore(temp);
				return temp;
			}
			//other piece is vertical
			else
			{
				if (Math.abs(startY - other.startY) > other.len ||
					Math.abs(startY - other.endY) > other.len) return null;
				if (Math.abs(other.startX - startX) > len ||
					Math.abs(other.startX - endX) > len) return null;
				
				Spot temp = new Spot(other.startX, this.startY);
				temp.stepsBefore = totalStepsBefore(temp) + other.totalStepsBefore(temp);
				return temp;
			}
		}
		
		public int totalStepsBefore(Spot s)
		{
			//System.out.println("TOTAL: " + this.toString() + s.toString());
		
			if (vertical() && s.x != startX) throw new IllegalArgumentException("1" + s);
			if (vertical() && Math.abs(s.y - startY) > len) throw new IllegalArgumentException("2" + s);
			if (vertical() && Math.abs(s.y - endY) > len) throw new IllegalArgumentException("3" + s);
			if (!vertical() && s.y!= startY) throw new IllegalArgumentException("4" + s);
			if (!vertical() && Math.abs(s.x - startX) > len) throw new IllegalArgumentException("5" + s);
			if (!vertical() && Math.abs(s.x - endX) > len) throw new IllegalArgumentException("6" + s);
			
			return stepsBefore + (vertical() ? Math.abs(s.y - startY) : Math.abs(s.x - startX));
		}
		
		public boolean vertical()
		{
			return (startX == endX);
		}
		
		//constructor
		public Piece(Piece previous, char direction, int steps)
		{
			//first piece in wire
			if (previous == null)
			{
				startX = 0;
				startY = 0;
				stepsBefore = 0;
				len = steps;
				
				switch (direction)
				{
					case 'R':
						endY = 0;
						endX = steps;
						break;
					case 'L':
						endY = 0;
						endX = 0 - steps;
						break;
					case 'U':
						endX = 0;
						endY = steps;
						break;
					case 'D':
						endX = 0;
						endY = 0 - steps;
						break;
					default:
						throw new IllegalArgumentException("Bad input: " + direction);
				}
			}
			else {
				startX = previous.endX;
				startY = previous.endY;
				stepsBefore = previous.stepsBefore + previous.len;
				len = steps;
				
				switch (direction)
				{
					case 'L':
						endY = startY;
						endX = startX - len;
						break;
					case 'R':
						endY = startY;
						endX = startX + len;
						break;
					case 'U':
						endX = startX;
						endY = startY + len;
						break;
					case 'D':
						endX = startX;
						endY = startY - len;
						break;
					default:
						throw new IllegalArgumentException("Invalid argument: " + direction);
				}
			}
		}
	}
}
