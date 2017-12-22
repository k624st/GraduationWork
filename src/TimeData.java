import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeData {

	String timedata(){
		Date date = new Date();
		String nsdf = new SimpleDateFormat("yyyy:MM:dd:HH_mm",Locale.JAPAN).format(date);
		return nsdf;
	}
}