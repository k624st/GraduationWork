import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.chasen.mecab.Node;
import org.chasen.mecab.Tagger;

public class Mecab {
	static {
		try {
			File f = new File("/Users/apple/Desktop/cabocha/mecab-java-0.996/libMeCab.so");
			System.load(f.toString());
		} catch (UnsatisfiedLinkError e) {
			System.exit(1);
		}
	}
	
	int conf = 0;
	
	int maceb(String value, ArrayList<String> meca) {
		conf = 0;
		Tagger tagger = new Tagger();
		tagger.parse(value);
		Node node = tagger.parseToNode(value);
		for (; node != null; node = node.getNext()) {
			meca.add(node.getSurface());
			regexp(node.getSurface());
		}
		return conf;
	}
	
	void regexp(String text){
		String regex1 = ".*遅れ|.*遅延|.*止ま";
		String regex2 = ".*見合わせ";
		String regex3 = ".*通常|.*平常|.*再開";
		Pattern p1 = Pattern.compile(regex1);
		Pattern p2 = Pattern.compile(regex2);
		Pattern p3 = Pattern.compile(regex3);
		Matcher m1 = p1.matcher(text);
		Matcher m2 = p2.matcher(text);
		Matcher m3 = p3.matcher(text);
		
		if (m1.find()){
			conf = 1;
		}else if(m2.find()){
			conf = 2;
		}else if(m3.find()){
			conf = 0;
		}
	}
}