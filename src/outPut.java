import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class outPut {

	void csv(ArrayList<String[]> table, String date, String stationState) throws IOException{
		String[][] arrayTable = table.toArray(new String[table.size()][]);
		
		String filename = "/Users/apple/Desktop/卒研/" + date + "/table.csv";
		File file = new File(filename);
		file.delete();
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
		FileOutputStream os = new FileOutputStream(file);
        os.write(0xef);
        os.write(0xbb);
        os.write(0xbf);
        pw = new PrintWriter(new OutputStreamWriter(os));
        pw.println("指定駅に関する情報");
        pw.println(stationState);
        pw.println("路線名,運行状態,遅延時間,遅延区間,遅延原因");
		for (int i = 0; i < arrayTable.length; i++) {
			for (int j = 0; j < 5; j++) {
				pw.print(arrayTable[i][j] + ",");
			}
			pw.println("");
		}
		pw.close();
	}
}
