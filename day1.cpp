// Note: for the code to work correctly, you need to replace the file name on
//       line 27 with the appropriate input file downloaded from the Advent of
//       Code website.

#include <iostream>
#include <fstream>

using namespace std;

void solve(int part);
int fuelValue(int );
int recFuelValue(int );

int main()
{
	// solve both parts of problem
	solve(1);
	solve(2);
	
	//exit
	return 0;
}

void solve(int part)
{
	// setup file input
	ifstream fin("day1.in");
	
	// read file and calculate fuel needs
	int counter = 0,
		temp;
	fin >> temp;
	while (!fin.eof())
	{
		counter += (part == 1) ? fuelValue(temp) : recFuelValue(temp);
		fin >> temp;
	}
	
	// output answer & close file input
	cout << "Part " << part << ": " << counter << endl;
	fin.close();
}

// based on problem description
int recFuelValue(int f)
{
	int counter = 0;
	f = fuelValue(f);
	while (f > 0)
	{
		counter += f;
		f = fuelValue(f);
	}
	
	return counter;
}

// based on problem description
int fuelValue(int f)
{
	return (f / 3 - 2);
}
