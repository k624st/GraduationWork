import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import twitter4j.TwitterException;

public class GraduationWork {
	
	public static void main(String args[]) throws IOException, InterruptedException, TwitterException {
		int count = 0;
		while(count++ < 800){
			ArrayList<String> route = new ArrayList<String>();
			ArrayList<Integer> routeValue = new ArrayList<Integer>();
			ArrayList<String> twapi = new ArrayList<String>();
			ArrayList<String> mcb = new ArrayList<String>();
			ArrayList<String[]> table = new ArrayList<String[]>();
			
			TwitterApi tw = new TwitterApi(1);
			TimeData tData = new TimeData();
			CheckStation check = new CheckStation();
			Mecab mecab = new Mecab();
			Xml xml = new Xml();
			Delay delay = new Delay();
			Data data = new Data();
			outPut op = new outPut();
			
			String timeData = tData.timedata();
			
//			System.out.println("駅名を入力してください。");
//			Scanner scn = new Scanner(System.in);
//			String station = scn.next();
			String station = "新宿";
			String stState = "駅に関するデータなし";
			
			tw.twapi(station, twapi, timeData);
			xml.xmlRoute(check.checker(station), route, routeValue);
			int stConf = 0;
			for(String value : twapi){
				stConf = mecab.maceb(value, mcb);
				if(stConf > 0){
					stState = "駅を通る路線に遅延や運転見合わせの可能性あり";
				}
			}
			String stationState = station + stState;
			
			for(int i = 0; i < route.size(); i++){
				ArrayList<String> time = new ArrayList<String>();
				ArrayList<String> section = new ArrayList<String>();
				ArrayList<String> cause = new ArrayList<String>();
				ArrayList<Integer> sTime = new ArrayList<Integer>();
				ArrayList<Integer> sSection = new ArrayList<Integer>();
				ArrayList<Integer> sCause = new ArrayList<Integer>();
				ArrayList<String> xmlst = new ArrayList<String>();
				int delayConf = 0;
				twapi.clear();
				
				xml.xmlStation(routeValue.get(i), xmlst);
				tw.twapi(route.get(i), twapi, timeData);
				for(String value : twapi){
					int conf = 0;
					mcb.clear();
					
					conf = mecab.maceb(value, mcb);
					if(conf > 0){
						delay.delay(mcb, time, section, cause, xmlst, sTime, sSection, sCause);
						if(delayConf == 0 || conf > 1){
							delayConf = conf;
						}
					}
				}
				if(delayConf > 0){
					data.data(route.get(i), time, section, cause, sTime, sSection, sCause, table, delayConf);
				}
			}
			op.csv(table, timeData, stationState);
			new Table(stationState, table);
			TimeUnit.MINUTES.sleep(1);
			TimeUnit.SECONDS.sleep(30);
		}
	}
}