import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class Xml {
	
	void xmlStation(int value, ArrayList<String> stName){
		try{
			System.setProperty("http.proxyHost", "proxy.kokushikan.ac.jp");
			System.setProperty("http.proxyPort", "8080");
			
			URL url = new URL("http://www.ekidata.jp/api/l/" + value + ".xml");
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestMethod("GET");
			http.connect();
			
			Document document = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse(http.getInputStream());
			Element element = document.getDocumentElement();
			
			//駅名
			NodeList station = element.getElementsByTagName("station");
			for(int i = 0; i < station.getLength(); i++){
				Element estation = (Element) station.item(i);
				NodeList stationname = estation.getElementsByTagName("station_name");
				stName.add(stationname.item(0).getFirstChild().getNodeValue());
			}
		}catch(IOException e){
			System.out.println(e);
		}catch (SAXException e) {
			System.out.println(e);
		}catch(ParserConfigurationException e){
			System.out.println(e);
		}
	}
	
	void xmlRoute(int value, ArrayList<String> rtName, ArrayList<Integer> routeValue){
		try{
			System.setProperty("http.proxyHost", "proxy.kokushikan.ac.jp");
			System.setProperty("http.proxyPort", "8080");
			
			URL url = new URL("http://www.ekidata.jp/api/g/" + value + ".xml");
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestMethod("GET");
			http.connect();
			
			Document document = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse(http.getInputStream());
			Element element = document.getDocumentElement();
			
			//駅名
			NodeList stGroup = element.getElementsByTagName("station_g");
			for(int i = 0; i < stGroup.getLength(); i++){
				Element estation = (Element) stGroup.item(i);
				NodeList lineName = estation.getElementsByTagName("line_name");
				String liname = lineName.item(0).getFirstChild().getNodeValue();
				liname = liname.replaceAll("[(].*[)]", "");
				
				liname = liname.replaceAll("JR", "");
				liname = liname.replaceAll(".*・", "");
				liname = liname.replaceAll("東京メトロ", "");
				if(liname.contains("新宿") != true){
					liname = liname.replaceAll("都営", "");
					liname = liname.replaceAll("東武", "");
					liname = liname.replaceAll("西武", "");
				}
				
				rtName.add(liname);
				
				NodeList lineCd = estation.getElementsByTagName("line_cd");
				routeValue.add(Integer.parseInt(lineCd.item(0).getFirstChild().getNodeValue()));
			}
		}catch(IOException e){
			System.out.println(e);
		}catch (SAXException e) {
			System.out.println(e);
		}catch(ParserConfigurationException e){
			System.out.println(e);
		}
	}
}