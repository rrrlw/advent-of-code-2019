import java.io.*;
import java.util.*;

public class Day6
{
	static final String INPUT = "day6.in";
	public static void main(String[] args) throws IOException
	{
		Scanner fin = new Scanner(new File(INPUT));
		
		ArrayList<String>labels = new ArrayList<String>();
		String[] temp;
		String tempFrom, tempTo;
		int[][] adjacency;
		while (fin.hasNextLine())
		{
			temp = fin.nextLine().split("\\)");
			tempFrom = temp[0];
			tempTo = temp[1];
			
			if (!labels.contains(tempFrom))
				labels.add(tempFrom);
			if (!labels.contains(tempTo))
				labels.add(tempTo);
		}
		fin.close();
		
		fin = new Scanner(new File(INPUT));
		adjacency = new int[labels.size()][labels.size()];
		for (int i = 0; i < adjacency.length; i++)
			for (int j = 0; j < adjacency[i].length; j++)
				if (i == j)
					adjacency[i][j] = 0;
				else
					adjacency[i][j] = labels.size() * 2;
		int tempF, tempT;
		while (fin.hasNextLine())
		{
			temp = fin.nextLine().split("\\)");
			tempF = labels.indexOf(temp[0]);
			tempT = labels.indexOf(temp[1]);
			
			adjacency[tempF][tempT] = 1;
			adjacency[tempT][tempF] = 1;
		}
		fin.close();
		
		//floyd warshall
		for (int k = 0; k < labels.size(); k++)
			for (int i = 0; i < labels.size(); i++)
				for (int j = 0; j < labels.size(); j++)
					if (adjacency[i][j] > adjacency[i][k] + adjacency[k][j])
						adjacency[i][j] = adjacency[i][k] + adjacency[k][j];
		
		// part 1
		int counter = 0,
			comIndex = labels.indexOf("COM");
		for (int i = 0; i < labels.size(); i++)
			counter += adjacency[comIndex][i];
		System.out.println("Part 1: " + counter);
		
		// part 2
		int youIndex = labels.indexOf("YOU"),
			sanIndex = labels.indexOf("SAN"),
			answer = adjacency[youIndex][sanIndex] - 2;
		System.out.println("Part 2: " + answer);
	}
}
