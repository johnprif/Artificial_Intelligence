//Aristeidis Vrazitoulis 4034
//Ioannhs Prifth 3321


import java.util.Scanner;
import java.util.Random;



public class MiniMax
{
	static int size = 3;
	static Scanner input = new Scanner(System.in);
	static Random rand = new Random();
	char letter;

	int MAX=Integer.MAX_VALUE;
	int MIN=Integer.MIN_VALUE;

	

	public int minimax(char[][] board, boolean cpu, int depth, int alpha, int beta)
	{
		int terminalValue = checkWin(board,!cpu);
		int eval;
		int maxEval,minEval;

		//terminal condition
		if(terminalValue==1||terminalValue==0||terminalValue==-1)
		{
			return terminalValue;
		}
		
		//cpu turn
		if(cpu)
		{
			maxEval = MIN;
			for(int i=0; i<size; i++)
			{
				for(int j=0; j<size; j++)
				{
					if(board[i][j]=='-')
					{
						
						board[i][j] = 'O'; 
						eval = minimax(board,false,depth+1, alpha, beta);
						board[i][j] = '-';
						maxEval=Math.max(maxEval, eval);
						alpha=Math.max(alpha, maxEval);
						if(beta<=alpha)
						{
							break;	
						}
						
						
						
						board[i][j] = 'S';
						eval = minimax(board,false,depth+1, alpha, beta);
						board[i][j] = '-';
						maxEval=Math.max(maxEval, eval);
						alpha=Math.max(alpha, maxEval);
						if(beta<=alpha)
						{
							break;	
						}

					}
				}
			}
			return maxEval;
		//player turn
		}else
		{
			minEval = MAX;
			for(int i=0; i<size; i++)
			{
				for(int j=0; j<size; j++)
				{
					if(board[i][j]=='-')
					{

						board[i][j] = 'O';
						eval = minimax(board,true,depth+1, alpha, beta);
						board[i][j] = '-';
						minEval=Math.min(minEval, eval);
						beta=Math.min(beta, minEval);
						if(beta<=alpha)
						{
							break;
						}

						board[i][j] = 'S';
						eval = minimax(board,true,depth+1, alpha, beta);
						board[i][j] = '-';
						minEval=Math.min(minEval, eval);
						beta=Math.min(beta, minEval);
						if(beta<=alpha)
						{
							break;
						}
					}
				}
			}

			return minEval;
		}
	}

	//minimax for every child and returns best move
	public int[] bestMove(char[][] board)
	{
		int bestScore = MIN;
		int[] ret = new int[2];
		int score;
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				if(board[i][j]=='-')
				{
					board[i][j] = 'O';
					score = minimax(board,false,0, MIN, MAX);
					board[i][j] = '-';
					if(score>bestScore)
					{
						bestScore = score;
						ret[0] = i;
						ret[1] = j;
						letter = 'O';
					}
					board[i][j] = 'S';
					score = minimax(board,false,0, MIN, MAX);
					board[i][j] = '-';
					if(score>bestScore)
					{
						bestScore = score;
						ret[0] = i;
						ret[1] = j;
						letter = 'S';
					}
				}
			}
		}
		return ret;
	}


	//returns 1 for cpu 0 draw -1 user, win
	public int checkWin(char[][] board, boolean cpu)
	{

		boolean isTie = true;
		for(int i=0; i<size; i++)
		{
			if(board[1][i]=='O' && board[0][i]=='S' && board[2][i]=='S')
			{
				return (cpu) ? 1 : -1;
			}
			if(board[i][1]=='O' && board[i][0]=='S' && board[i][2]=='S')
			{
				return (cpu) ? 1 : -1;
			}
			for(int j = 0; j < size; j++)
			{
				if(board[i][j]=='-')
				{
					isTie = false;
				}
			}
		}
		if(board[0][0]=='S' && board[1][1]=='O' && board[2][2]=='S')
		{
			return (cpu) ? 1 : -1;
		}
		if(board[0][2]=='S' && board[1][1]=='O' && board[2][0]=='S')
		{
			return (cpu) ? 1 : -1;
		}
		if(isTie)
		{
			return 0;
		}
		return 10;
	}

	public void printBoard(char[][] board)
	{
		for(int i=0; i<size; i++)                   
		{
			for(int j=0; j<size; j++)
			{
				System.out.print(board[i][j]+" |");
			}
			System.out.print("\n\n");
		}
	}
	public char takeLetter()
	{
		String temp;
		char c='_';
		while(c!='S'&&c!='O')
		{
			System.out.print("S or O:");
			temp = input.next();
			c = temp.charAt(0);
			if(temp.length()!=1){
				System.out.println("Wrong input");
				continue;
			}
		}
		return c;

	}

	public int[] takeCoords(char[][] board)
	{
		int number=0;
		int[] cords = null;
		String temp;
		while(true)
		{
			System.out.print("Give the your place: ");
		
			if(input.hasNextInt())
			{
				temp = input.next();
				number=Integer.parseInt(temp);
				if(!(number>0 && number<=9) || temp.length() != 1)
				{
					System.out.println("Wrong input: ");
					continue;
				}else
				{
					cords = inputToCord(number);
					if(board[cords[0]][cords[1]] == '-')
					{

						break;
					}
					System.out.println("Wrong input!");
					continue;
				}
			}else
			{
				System.out.println("Wrong input!");
				temp = input.next();
			}

		}
		
		return cords;
		
	}
	//converts input to coordinates
	public int[] inputToCord(int number)
	{
		int[] cord = new int[2];    
		int temp = (9-number)-2*((9-number)%3-1);
		cord[0] = temp/3;
		cord[1] = temp%3;
		return cord;
	}

	//returns true if game is over
	public static boolean isOver(int winner)
	{
		if(winner == 1)
		{
			System.out.println("You lose!");
			return true;
		}else if(winner == -1 )
		{
			System.out.println("You won the game!");
			return true;
		}else if(winner == 0)
		{
			System.out.println("Tie!");
			return true;
		}
		return false;
	}

	public static void main(String[] args) 
	{
		char[][] board = new char[3][3];
		int[] move =  new int[2];
		int temp = (rand.nextInt(50)%2==0) ? 0: 2;
		MiniMax sos = new MiniMax();
		char letter;
		int winner;
		boolean cpu = true;
		

		//initialize board
		for(int i=0; i<size; i++)
		{
			for(int j=0; j<size; j++)
			{
				board[i][j]='-';
				if(i==1&&j==temp)
				{
					board[i][j] = 'O';
				}
			}
		}
		System.out.print("Use your numpad\n7 | 8 | 9\n4 | 5 | 6\n1 | 2 | 3\n\n");
		
		while(true)
		{
			//cpu turn
			move = sos.bestMove(board);
			board[move[0]][move[1]]=sos.letter;
			sos.printBoard(board);
			winner=sos.checkWin(board, true);
			if(isOver(winner))
			{
				break;
			}
			//player turn
			move = sos.takeCoords(board);
			letter = sos.takeLetter();
			board[move[0]][move[1]]=letter;
			winner=sos.checkWin(board, false);
			if(isOver(winner))
			{
				break;
			}
		}
		
	}
}

