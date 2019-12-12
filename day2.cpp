#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <sstream>

using namespace std;

vector<int>opList;

vector<string> split(string str, char c);
int strToInt(string );
int intcode(int returnIndex);
void solve1();
void solve2();

int main()
{
	solve1();
	solve2();
	
	return 0;
}

void solve1()
{
	// input
	ifstream fin("day2.in");
	string line;
	getline(fin, line);
	vector<string> splits = split(line, ',');
	for (int i = 0; i < splits.size(); i++)
		opList.push_back(strToInt(splits[i]));
	fin.close();
	
	// adjust as described in problem
	opList[1] = 12;
	opList[2] = 2;
	
	// print answer
	cout << "Part 1: " << intcode(0) << endl;
}

void solve2()
{
	bool done = false;
	for (int i = 0; i <= 99 && !done; i++)
		for (int j = 0; j <= 99 && !done; j++)
		{
			opList[1] = i;
			opList[2] = j;
			
			if (intcode(0) == 19690720)
			{
				done = true;
				cout << "Part 2: " << i * 100 + j << endl;
			}
		}
}

// convert string to int
int strToInt(string s)
{
	int ans;
	stringstream(s) >> ans;
	return ans;
}

//day 2 intCode
int intcode(int returnIndex)
{
	bool done = false;
	vector<int>copyList(opList);
	for (int i = 0; i < copyList.size() && !done; )
	{
		switch (copyList[i])
		{
			case 99:
				done = true;
				break;
			case 1:
				copyList[copyList[i+3]] = copyList[copyList[i+1]] + copyList[copyList[i+2]];
				i += 4;
				break;
			case 2:
				copyList[copyList[i+3]] = copyList[copyList[i+1]] * copyList[copyList[i+2]];
				i += 4;
				break;
		}
	}
	return copyList[returnIndex];
}

// from StackOverflow by user Oktalist
// https://stackoverflow.com/questions/53849/how-do-i-tokenize-a-string-in-c
vector<string> split(string str, char c)
{
    vector<string> result;
    int counter = 0;
    do {
    	int begin = counter;
        while(str[counter] != c && counter < str.length())
            counter++;
        result.push_back(str.substr(begin, counter - begin));
    } while (counter++ < str.length());
    return result;
}
