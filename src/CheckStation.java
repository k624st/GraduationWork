
public class CheckStation {

	int checker(String station){
		int value = 0;
		switch (station) {
			case "新宿":
				value = 1130208;
				break;
			case "与野本町":
				value = 1132117;
				break;
			case "上野":
				value = 1130220;
				break;
			case "大阪":
				value = 1160214;
				break;
			default:
				System.out.println(station + "は実装していません。");
				System.exit(1);
				break;
		}
		return value;
	}
}