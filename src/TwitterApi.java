import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

 class TwitterApi {
	private static final String consumerKey = "3960SKv15x53Vi2UgsQdTcL6F";
	private static final String consumerSecret = "3fs64Oi3XO0R9MCHRgzzHcdjTWTlhNKg5wwamG21Xva0xH3I74";
	private static final String accessToken = "2558511349-IjpMu12tP8WhOfPdCHO3R8NpHIAuBhjKyY4Arqq";
	private static final String accessTokenSecret = "uz2TfzQ8XuyPGz596DA1Zp2InOVEKVCs9iqZNDxOO9NWR";

	private static int choose;
	
	TwitterApi() {
		this (0);
	}
	
	TwitterApi(int newChoose) {
		choose = newChoose;
	}
	
	void twapi(String key, ArrayList<String> twapi, String date) throws InterruptedException, IOException{
		System.setProperty("http.proxyHost", "proxy.kokushikan.ac.jp");
		System.setProperty("http.proxyPort", "8080");
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey(consumerKey)
			.setOAuthConsumerSecret(consumerSecret)
			.setOAuthAccessToken(accessToken)
			.setOAuthAccessTokenSecret(accessTokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		Twitter tw = TwitterFactory.getSingleton();
		
		String word = " -RT -まとめ -エステ";
		String user = "-from:i_transportlab -from:yamadaikenbot -from:chiensokuho2 -from:HAIR_SOYUZ"
				+ " -from:tomatodaisukism -from:chuo_access -from:E993_bot -from:SeinnoFGG_bot -from:repairstudio -from:JorudanLive";
		String search = key + word + user;
		
		String timedata[] = new String[2];
		time(timedata);
		
		try{
			Query query = new Query();
			query.setQuery(search);
			query.setLang("ja");
//			query.since(timedata[1]);
//			query.until(timedata[0]);
			query.since("2017-12-22_17:00:00_JST");
			query.until("2017-12-22_18:00:00_JST");
			query.setCount(100);
			QueryResult result = twitter.search(query);
			Paging paging = new Paging(2,100);
			
			new File("/Users/apple/Desktop/卒研/" + date).mkdirs();
			
			switch (choose) {
				case 0:
					for (Status status : result.getTweets()) {
						twapi.add(status.getText());
					}
					break;
				default:
					String filename = "/Users/apple/Desktop/卒研/" + date + "/" + key + ".txt";
					File file = new File(filename);
					file.mkdir();
					file.delete();
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
					for (Status status : result.getTweets()) {
						twapi.add(status.getText());
						pw.println(status.getText());
						pw.println("@" + status.getUser().getScreenName() + " | ツイート日時：" + status.getCreatedAt());
						pw.println("-----------------------------------------------------------------------");
					}
					pw.close();
					break;
			}
		} catch(TwitterException e){
			System.err.println(e);
		}	
	}
	
	static String[] time(String timedata[]){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		
		//今
		String nsdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss",Locale.JAPAN).format(date);
		timedata[0] = nsdf + "_JST";

		//30分前
		cal.setTime(date);
		cal.add(Calendar.MINUTE, -15);
		String psdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss",Locale.JAPAN).format(cal.getTime());
		timedata[1] = psdf + "_JST";
		
		return timedata;
	}
}