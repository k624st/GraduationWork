import java.util.ArrayList;

public class Weight {

	void count(String value, ArrayList<String> list, ArrayList<Integer> s){
		
		if(list.contains(value) == true){
			int index1 = list.indexOf(value);
			int index2 = 0;
			String temp1 = value;
			int temp2 = s.get(index1);

			for(int i = 0; i < s.size(); i++){
				if(s.get(i) == temp2){
					index2 = i;
					break;
				}
			}
			
			list.remove(index1);
			s.remove(index1);
			list.add(index2, temp1);
			s.add(index2, temp2 + 1);
		}else if(list.contains(value) != true){
			list.add(value);
			s.add(1);
		}
	}
	
	void size(){
		
	}
}
