import java.util.ArrayList;

public class Data {

	void data(String route, ArrayList<String> time, ArrayList<String> section, ArrayList<String> cause, ArrayList<Integer> sTime, ArrayList<Integer> sSection, ArrayList<Integer> sCause, ArrayList<String[]> table, int conf){
		ArrayList<String> tsc = new ArrayList<String>();
		int size = size(time, section, cause);
		
		int j = 0;
		do{
			if(j == 0){
				tsc.add(route);
				switch (conf) {
					case 1:
						tsc.add("遅延の可能性");
						break;
					case 2:
						tsc.add("遅延、運転見合わせの可能性");
						break;
					default:
						tsc.add("");
						break;
				}
			} else {
				tsc.add("");
				tsc.add("");
			}
			input(tsc, time, sTime, j);
			input(tsc, section, sSection, j);
			input(tsc, cause, sCause, j);
			table.add(tsc.toArray(new String[0]));
			tsc.clear();
			j++;
		}while(j < size);
	}
	
	int size(ArrayList<String> time, ArrayList<String> section, ArrayList<String> cause){
		if(time.size() > section.size()){
			if(time.size() > cause.size()){
				return time.size();
			} else {
				return cause.size();
			}
		} else {
			if(section.size() > cause.size()){
				return section.size();
			} else {
				return cause.size();
			}
		}
	}
	
	void input(ArrayList<String> in, ArrayList<String> list, ArrayList<Integer> s, int count){
		if(list.size() > count && list.get(count) != null){
			in.add(list.get(count) + " " + s.get(count) + "件");
		} else {
			in.add("");
		}
	}
}