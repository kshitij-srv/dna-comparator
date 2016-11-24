package implement;

/****Longest Common Subsequence****/
public class LCS {

	String lcs="";
	
	public int[][] edtable(String dna1, String dna2)
	{
		int l1 = dna1.length()+1;
		int l2 = dna2.length()+1;
		int edtable[][] = new int[l1][l2];
		char ch1[] = dna1.toCharArray();
		char ch2[] = dna2.toCharArray();
		int i, j, edit;
		
		for(i=0;i<l1;i++)
		{
			edtable[i][0] = i;
		}
		for(j=1;j<l2;j++)
		{
			edtable[0][j] = j;
		}
		
		for(i=1;i<l1;i++)
		{
			for(j=1;j<l2;j++)
			{
				int a = edtable[i-1][j]+1;
				int b = edtable[i][j-1]+1;
				int c = edtable[i-1][j-1];
				
				edit = a;
				
				if(c<a && c<b)
				{
					if(ch1[i-1] == ch2[j-1])
					{
						edit = c;
					}
				}
				else if(b<a)
				{
					edit = b;
				}
				
				edtable[i][j] = edit;
			}
		}
		
		return edtable;
	}
	
	public int[][] dptable(String dna1, String dna2)
	{
		int l1 = dna1.length()+1;
		int l2 = dna2.length()+1;
		int dptable[][] = new int[l1][l2];
		char ch1[] = dna1.toCharArray();
		char ch2[] = dna2.toCharArray();
		int i, j, score;
		
		for(i=0;i<l1;i++)
		{
			dptable[i][0] = 0;
		}
		for(j=1;j<l2;j++)
		{
			dptable[0][j] = 0;
		}
		
		for(i=1;i<l1;i++)
		{
			for(j=1;j<l2;j++)
			{
				int a = dptable[i-1][j];
				int b = dptable[i][j-1];
				int c = 0;
				if(ch1[i-1] == ch2[j-1])
				{
					c = dptable[i-1][j-1]+1;
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
	
	public char[][] btrack(String dna1, String dna2)
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
		
		for(i=1;i<l1;i++)
		{
			dptable[i][0] = 0;
			btrack[i][0] = ch1[i-1];
		}
		for(j=1;j<l2;j++)
		{
			dptable[0][j] = 0;
			btrack[0][j] = ch2[j-1];
		}
		
		for(i=1;i<l1;i++)
		{
			for(j=1;j<l2;j++)
			{
				int a = dptable[i-1][j];
				int b = dptable[i][j-1];
				int c = 0;
				if(ch1[i-1] == ch2[j-1])
				{
					c = dptable[i-1][j-1]+1;
				}
				
				score = a;
				ch = 'd';
				
				if(b>a && b>c)
				{
					score = b;
					ch = 'i';
				}
				else if(c>=a)
				{
					score = c;
					ch = '=';
				}
				
				dptable[i][j] = score;
				btrack[i][j] = ch;
			}
		}
		
		return btrack;
	}
	
	public String PrintLCS(char btrack[][], char ch1[], int i, int j)
	{
		if(i==0 || j==0)
		{
			return "";
		}
		
		if(btrack[i][j] == 'd')
		{
			PrintLCS(btrack, ch1, i-1, j);
		}
		else if(btrack[i][j] == 'i')
		{
			PrintLCS(btrack, ch1, i, j-1);
		}
		else if(btrack[i][j] == '=')
		{
			PrintLCS(btrack, ch1, i-1, j-1);
			lcs = lcs+ch1[i-1];
		}
		
		return lcs;
	}
}
