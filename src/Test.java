import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		List<Integer> list = new LinkedList();
		list.add(10);
		list.add(20);
		list.add(30);
		
//		
		for(Iterator<Integer> i= list.iterator();i.hasNext();) {
			int j=i.next();
			list.remove(j);
		}
		

	}

}
