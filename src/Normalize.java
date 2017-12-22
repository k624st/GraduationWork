
class Normalize {
	//標準化
	String normalize(String value){
		StringBuffer sb = new StringBuffer(value);
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c >= '０' && c <= '９') {
				sb.setCharAt(i, (char) (c - '０' + '0'));
			}
		}
		return sb.toString();
	}
}
