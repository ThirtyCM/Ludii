package app.boardMaker.tools;

/**
 * Class that contains a pair of values
 * */
public class Pair<E,T> {
	
	private E first;
	private T second;
	
	public Pair(E obj1, T obj2) {
		this.first = obj1;
		this.second = obj2;
	}
	
	public E getFirst() {
		return first;
	}
	
	public T getSecond() {
		return second;
	}
	
	@Override
		public String toString()
		{
			// TODO Auto-generated method stub
			return "First item " + first + " / Second item " + second;
		}
}
