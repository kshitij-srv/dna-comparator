package implement;

/****GLobal Sequence Alignment****/
public class GSA {

	String gsa="";
	
	public int[][] edtable(String dna1, String dna2) // function to print the edit distance table
	{
		int l1 = dna1.length()+1;
		int l2 = dna2.length()+1;
		int edtable[][] = new int[l1][l2];
		char ch1[] = dna1.toCharArray();
		char ch2[] = dna2.toCharArray();
		int i, j, edit;
		
		for(i=0;i<l1;i++) // initializing first row
		{
			edtable[i][0] = i;
		}
		for(j=1;j<l2;j++) // initializing first column
		{
			edtable[0][j] = j;
		}
		
		for(i=1;i<l1;i++) // assignment of the minimum edit path 
		{
			for(j=1;j<l2;j++)
			{
				int a = edtable[i-1][j]+1;
				int b = edtable[i][j-1]+1;
				int c;
				if(ch1[i-1]==ch2[j-1])
				{
					c = edtable[i-1][j-1];
				}
				else
				{
					c = edtable[i-1][j-1]+1;
				}
				
				edit = a;
				
				if(b<a && b<c)
				{
					edit = b;
				}
				if(c<a)
				{
					edit = c;
				}
				
				edtable[i][j] = edit;
			}
		}
		
		return edtable;
	}
	
	public int[][] dptable(String dna1, String dna2) // function to print the dynamic programming score table
	{
		int l1 = dna1.length()+1;
		int l2 = dna2.length()+1;
		int dptable[][] = new int[l1][l2];
		char ch1[] = dna1.toCharArray();
		char ch2[] = dna2.toCharArray();
		int i, j, score;
		
		for(i=0;i<l1;i++) // initializing first column
		{
			dptable[i][0] = 0;
		}
		for(j=1;j<l2;j++) // initializing first row 
		{
			dptable[0][j] = 0;
		}
		
		for(i=1;i<l1;i++) // assignment of scores 
		{
			for(j=1;j<l2;j++)
			{
				int a = dptable[i-1][j]-4;
				int b = dptable[i][j-1]-4;
				int c;
				if(ch1[i-1] == ch2[j-1])
				{
					c = dptable[i-1][j-1]+5;
				}
				else
				{
					c = dptable[i-1][j-1]-3;
				}
				
				score = a;
				
				if(b>a && b>c)
				{
					score = b;
				}
				else if(c>a)
				{
					score = c;
				}
				dptable[i][j] = score;
			}
		}
		
		return dptable;
	}
	
	public char[][] btrack(String dna1, String dna2) // function to print the backtracking  matrix
	{
		int l1 = dna1.length()+1;
		int l2 = dna2.length()+1;
		char btrack[][] = new char[l1][l2];
		int dptable[][] = new int[l1][l2];
		char ch1[] = dna1.toCharArray();
		char ch2[] = dna2.toCharArray();
		int i, j, score;
		char ch;
		dptable[0][0] = 0;
		btrack[0][0] = '*';
		
		for(i=1;i<l1;i++) // initializing first column
		{
			dptable[i][0] = 0;
			btrack[i][0] = ch1[i-1];
		}
		for(j=1;j<l2;j++) // initializing first row
		{
			dptable[0][j] = 0;
			btrack[0][j] = ch2[j-1];
		}
		
		for(i=1;i<l1;i++) // assigning the backtracking symbols
		{
			for(j=1;j<l2;j++)
			{
				int a = dptable[i-1][j]-4;
				int b = dptable[i][j-1]-4;
				int c, flag=0;
				if(ch1[i-1]==ch2[j-1])
				{
					c = dptable[i-1][j-1]+5;
					flag=1;
				}
				else
				{
					c = dptable[i-1][j-1]-3;
				}
				
				score = a;
				ch = 'd';
				
				if(b>a && b>c)
				{
					score = b;
					ch = 'i';
				}
				else if(c>a)
				{
					score = c;
					if(flag==1)
					{
						ch = '=';
					}
					else
					{
						ch = 'x';
					}
				}
				dptable[i][j] = score;
				btrack[i][j] = ch;
			}
		}
		
		return btrack;
	}
	
	public String PrintGSA(char btrack[][], char ch1[], int i, int j) // function to print the best alignment
	{
		if(i==0 || j==0)
		{
			return "";
		}
		
		if(btrack[i][j] == 'd')
		{
			PrintGSA(btrack, ch1, i-1, j);
		}
		else if(btrack[i][j] == 'i')
		{
			PrintGSA(btrack, ch1, i, j-1);
		}
		else if(btrack[i][j] == '=' || btrack[i][j] == 'x')
		{
			PrintGSA(btrack, ch1, i-1, j-1);
			gsa = gsa+ch1[i-1];
		}
		
		return gsa;
	}
}
