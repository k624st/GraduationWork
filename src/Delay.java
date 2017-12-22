import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Delay {
	
	private static String[] result = new String[3];
	private static int timeConf = 0;
	private static int sectionConf = 0;

	public void delay(ArrayList<String> xmlcabo, ArrayList<String> time, ArrayList<String> section, ArrayList<String> cause, ArrayList<String> stName, ArrayList<Integer> sTime, ArrayList<Integer> sSection, ArrayList<Integer> sCause) {
		try{
			Weight weight = new Weight();
			Normalize normalize = new Normalize();
			for(int i = 0; i < xmlcabo.size() ;i++){
				delaytime(xmlcabo.get(i), xmlcabo, i - 1);
				delaySection(xmlcabo.get(i),stName);
				delaycause(xmlcabo.get(i), xmlcabo, i);
			}
			
			if(result[0] != null) {
				String nrizeTime = normalize.normalize(result[0]);
				weight.count(nrizeTime, time, sTime);
			}
			if(result[1] != null) {
				weight.count(result[1], section, sSection);
			}
			if(result[2] != null) {
				weight.count(result[2], cause, sCause);
			}
			
			reset();
		} catch(NullPointerException e) {
			System.err.println(e);
		}
	}
	
	static void delaytime(String text, ArrayList<String> list, int i){
		String regex1 = ".*時";
		String regex2 = ".*分";
		String regex3 = "[0-9]";
		Pattern p1 = Pattern.compile(regex1,Pattern.CASE_INSENSITIVE);
		Pattern p2 = Pattern.compile(regex2,Pattern.CASE_INSENSITIVE);
		Pattern p3 = Pattern.compile(regex3,Pattern.CASE_INSENSITIVE);
		Matcher m1 = p1.matcher(text);
		Matcher m2 = p2.matcher(text);
		
		if(m1.find()){
			timeConf = 1;
		}
		if(m2.find() && timeConf == 0){
			Matcher m3 = p3.matcher(list.get(i));
			if(m3.find()){
				result[0] = (list.get(i) + "分");
			}
		}
	}

	static void delaySection(String text, ArrayList<String> stName){
		try{
			if(stName.contains(text)){
				if(sectionConf == 0){
					result[1] = text + "駅";
					sectionConf++;
				}else if(sectionConf == 1){
					text += "駅";
					if(result[1].contains(text) != true){
						result[1] = result[1] + "から" + text;
						sectionConf++;
					}
				}
			}
			if(sectionConf > 1 && text.contains("ライン") && result[1].contains("上野駅から東京駅") == true){
				result[1] = null;
			}
		}catch (NullPointerException e){
			System.out.println(e);
		}
	}

	static void delaycause(String text, ArrayList<String> list, int i){
		try{
			List<String> cause = new ArrayList<>(Arrays.asList("人身", "支障", "トラブル", "信号", "安全確認", "火災", "接触"));
			for(int j = 0; j < cause.size(); j++){
				String regex = cause.get(j);
				Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(text);
				if(m.find()){
					switch (j) {
						case 0:
							result[2] = ("人身事故");
							break;
						case 1:
							result[2] = ("影響");
							break;
						case 2:
							result[2] = ("トラブル");
							break;
						case 3:
							result[2] = ("信号点検");
							break;
						case 4:
							result[2] = ("安全確認");
							break;
						case 5:
							result[2] = ("火災");
							break;
						case 6:
							result[2] = ("接触");
							break;
						default:
							result[2] = cause.get(j);
							break;
					}
				}
			}
		}catch (NullPointerException e){
			System.out.println(e);
		}
	}
	
	void reset(){
		timeConf = 0;
		sectionConf = 0;
		result = delete(result);
	}
	
	static String[] delete(String[] value){
		List<String> list = new ArrayList<String>(Arrays.asList(value));
		list.clear();
		return (String[]) list.toArray(new String[value.length]);
	}
}