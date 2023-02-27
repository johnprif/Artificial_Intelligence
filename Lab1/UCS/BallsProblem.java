//Aristeidis Vrazitoulis 4034
//Ioannhs Prifth 3321

import java.util.*;


public class BallsProblem
{
	//Each state is a class
	//behaves as a struct
	class State{
		int id;
		int dash;
		char[] s;
		int cost;
		//int h;
		boolean popped;
		State(int id, int dash, char[] s, int cost){
			this.id = id;
			this.dash = dash;
			this.s = s;
			this.cost = cost;
			popped = false;
		}
	}

	State popState;


	//maps id with states
	HashMap<Integer,State> map = new HashMap<Integer,State>();
	//keeps list of visited states
	ArrayList<Integer> visited = new ArrayList<Integer>();
	//saves the progress on a tree
	ArrayList<Integer> tree = new ArrayList<Integer>();
	
	//size of array
	int size;
	//range is size/2 (N)
	int range;

	public BallsProblem(String input){

		char[] currentInput = input.toCharArray();
		int dash = input.indexOf("-");
		State s = new State(0,dash,currentInput, 0);
		map.put(0,s);
		visited.add(0);

		size = currentInput.length;
		range = size/2;
		
	}


	//UCS implementation
	public void runUCS()
	{

		//declare variables
		int k=0;
		int idCounter = 1;
		int prevId;
		int dash;
		char[] arr;
		
		tree.add(0);

		while(true)
		{
			if(map.isEmpty()){
				System.out.println("FAILED");
				return;
			}
			//Extracts the cheapest state
			popState = getCheapestState();
			
			//get index of dash
			dash = popState.dash;
			
			//gets the values from hashmap
			k = popState.cost;
			prevId = popState.id;

			//termination condition
			if(isRightPlace(popState)){
				if(popState.s[size-1]=='-'){
					arr = swap(size-1,size-2);
					popState = new State(idCounter,size-2,arr, k+1);
					map.put(idCounter, popState);
					visited.add(idCounter);
					tree.add(prevId);
					prevId = idCounter;
				}
				System.out.println("Expansions: "+idCounter);
				System.out.println("Final Result(UCS): ");
				printArray(popState.s);
				System.out.println("Cost:"+k+"\n");
				printPath(prevId);
				return;
			}
		
			//get all possible neighbours
			for(int i = 1; i <= range; i++)
			{
				//check if in bound
				if(dash-i>=0)
				{
					arr = swap(dash, dash-i);
					if(!contains(arr))
					{
						State s = new State(idCounter,dash-i,arr, k+i);
						visited.add(idCounter);
						map.put(idCounter, s);
						idCounter++;
						tree.add(prevId);
					}
				//check if in bound
				}if(dash+i<size)
				{
					arr = swap(dash, dash+i);
					if(!contains(arr))
					{
						State s = new State(idCounter,dash+i,arr, k+i);
						visited.add(idCounter);
						map.put(idCounter, s);
						idCounter++;
						tree.add(prevId);
					}
				}
			}
			popState.popped = true;
		}
	}

	//return true only if all balls are correctly placed
	public boolean isRightPlace(State s){
		char[] matrix = s.s;
		int barrier = (s.dash>=size/2) ? (size/2-1) : size/2;
		//search properly to the middle
		//if finds white ball thn stop
		for(int k = 0; k<=barrier; k++)
		{	
			if(matrix[k]=='A'){
				return false;
			}
		}
		return true;

	} 
	//Extracts from frontier smallest value
	public State getCheapestState(){
		int temp = Integer.MAX_VALUE;
		State ret = null;
		for (Map.Entry<Integer, State> entry : map.entrySet()) {
			int state = entry.getKey();
			State s = entry.getValue();
			int currentCost = s.cost;
			if(currentCost<temp&&!s.popped)
			{
				temp = currentCost;
				ret = map.get(state);
			}
		}
		if(ret==null){
			System.out.println("CHEAPEST NOT FOUND");
			return null;
		}
		return ret;
	}	


	

	//returns true if current state has been visited
	public boolean contains(char[] popState){
		int length = popState.length;
		int counter;
		for(Integer id: visited)
		{
			counter = 0;
			for(int i = 0; i < length; i++)
			{
			
				if(map.get(id).s[i]==popState[i]){
					counter++;
				}else{
					continue;
				}
				if(counter==size){
					return true;
				}
			}
		}
		return false;
	}

	//deep copies an array
	public char[] getDeepCopyMatrix(char[] matrix){
		char []temp = new char[size];
		for(int i = 0; i < size; i ++)
		{
			temp[i] = matrix[i];
		}
		return temp;
	}

	//swaps and returns a new array
	public char[] swap(int pivot, int ball){
		char[] tempArray = getDeepCopyMatrix(popState.s);
		char temp = tempArray[pivot];
		tempArray[pivot] = tempArray[ball];
		tempArray[ball] = temp;
		
		return tempArray;
	}

	//When ucs stop we print the path
	public void printPath(int id){
	
		Stack<char[]> s = new Stack<char[]>();
		while(id != 0){
			//save path to a stack and then print it backwards
			s.add(map.get(visited.get(id)).s);
			id = tree.get(id);
		}
		//unpack the stack and print
		while(!s.isEmpty()){
			printArray(s.pop());
		}

	}

	//print an array
	public void printArray(char[] matrix){
		for(int i = 0; i < matrix.length; i++)
		{
			System.out.print(matrix[i]);
		}
		System.out.println();
	}

	public static void main(String[] args)
	{
		System.out.print("Give me the input: ");
		Scanner input = new Scanner(System.in);
		String line = input.next();
		BallsProblem bp = new BallsProblem(line);
		bp.runUCS();

	}
	
		
		
}