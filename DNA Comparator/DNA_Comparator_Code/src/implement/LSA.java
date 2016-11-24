package implement;

/****Local Sequence Alignment****/
public class LSA {

	public String lsa="";
	
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
				
				if(score<0)
				{
					score = 0;
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
				
				if(score<0)
				{
					score = 0;
					ch = '*';
				}
				
				dptable[i][j] = score;
				btrack[i][j] = ch;
			}
		}
		
		return btrack;
	}
	
	public int[][] maxtable(int dptable[][], int l1, int l2)
	{
		int i, j, q=0, max = dptable[0][0], count=0;
		
		//finding the maximum score in the matrix
		for(i=0;i<l1;i++)
		{
			for(j=0;j<l2;j++)
			{
				if(dptable[i][j]>max)
				{
					max = dptable[i][j]; 
				}
			}
		}
		
		for(i=0;i<l1;i++)
		{
			for(j=0;j<l2;j++)
			{
				if(dptable[i][j]==max)
				{
					count++;
				}
			}
		}
		
		int maxtable[][] = new int[2][count];
		
		//finding the scores equivalent to max
		for(i=0;i<l1;i++)
		{
			for(j=0;j<l2;j++)
			{
				if(dptable[i][j]==max)
				{
					maxtable[0][q] = i; 
					maxtable[1][q++] = j;
				}
			}
		}
		
		return maxtable;
	}
	
	public String PrintLSA(int dptrack[][], char ch1[], int i, int j) // function to print the best alignment
	{
		if(i==0 || j==0)
		{
			return "";
		}
		
		if(dptrack[i][j]==0)
		{
			return ""+ch1[i-1];
		}
		
		if(dptrack[i][j] == dptrack[i-1][j]-4)
		{
			PrintLSA(dptrack, ch1, i-1, j);
			lsa = lsa+ch1[i-1];
		}
		else if(dptrack[i][j] == dptrack[i][j-1]-4)
		{
			PrintLSA(dptrack, ch1, i, j-1);
			lsa = lsa+"_";
		}
		else if(dptrack[i][j] == dptrack[i-1][j-1]+5 || dptrack[i][j] == dptrack[i-1][j-1]-3)
		{
			PrintLSA(dptrack, ch1, i-1, j-1);
			lsa = lsa+ch1[i-1];
		}
		
		return lsa;
	}
}

