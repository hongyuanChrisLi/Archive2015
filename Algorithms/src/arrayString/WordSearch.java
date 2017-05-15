package arrayString;

/*
 *  Given a 2D board and a word, find if the word exists in the grid.

The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.

For example,
Given board =

[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]

word = "ABCCED", -> returns true,
word = "SEE", -> returns true,
word = "ABCB", -> returns false.
 */


public class WordSearch 
{
	 public boolean exist(char[][] board, String word) 
	 {
	        
		 int row_num = board.length;
		 int col_num = board[0].length;
		 
		 for ( int i = 0; i < row_num; i++ )
		 {
			 for ( int j = 0; j < col_num; j ++)
			 {
				 if (dfs (board,i,j,word,0))
					 return true;
			 }
		 }
		  
		 return false;
	 }
	 
	 
	 private boolean dfs ( char[][] board, int i, int j, String word, int k) 
	 {
		 if ( i < 0 || j < 0 || i >= board.length || j >= board[0].length)
			 return false;
		 

		 if (word.charAt(k) == board[i][j])
		 {
			 
			 if ( k == word.length() - 1 )
				 return true;
			 
			 
			 char temp = board[i][j];
			 board[i][j] = '#';
			 
			 
			 if (dfs(board, i - 1, j, word, k + 1))
				 return true;
			 if (dfs(board, i + 1, j, word, k + 1))
				 return true;
			 if (dfs(board, i, j - 1, word, k + 1))
				 return true;
			 if (dfs(board, i, j + 1, word, k + 1))
				 return true;
			 
			 board[i][j] = temp;
			 
		 }
		 
		 
		 return false;
	 }
}