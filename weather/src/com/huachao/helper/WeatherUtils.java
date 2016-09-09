package com.huachao.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.huachao.bean.Message;
import com.huachao.bean.Model;
@Component
public class WeatherUtils {
	//Weather Service URL
	public static String SERVICES_HOST = "http://ws.webxml.com.cn/";
	private static String WEATHER_SERVICES_URL = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx";
	//Weather查询URL
	public static String WEATHER_QUERY_URL = WEATHER_SERVICES_URL
			+ "/getWeather?theUserID=&theCityCode=";
	public static String CITYCODE_QUERY="http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getSupportCityString";
	/**
	 * 城市代码：   （湖北荆州）: 1571
	 * 具体省市代码ID可通过：http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx?op=getSupportCityDataset进行查询 
	 */
	/**
	 * url_quire最终查询的url 
	 * service_host Weather Service URL
	 */
	private InputStream getSoapInputStream(String url) {
		InputStream inputStream = null;
		try {
			URL urlObj = new URL(url);
			URLConnection urlConn = urlObj.openConnection();
			urlConn.setRequestProperty("Host", SERVICES_HOST); // 具体webService相关
			urlConn.connect();
			inputStream = urlConn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}


	public Model getWeather(String code) {
		List<String> weatherList = new ArrayList<String>();
		org.w3c.dom.Document document;
		DocumentBuilderFactory documentBF = DocumentBuilderFactory.newInstance();
		documentBF.setNamespaceAware(true);
		try {
			DocumentBuilder documentB = documentBF.newDocumentBuilder();
			InputStream inputStream = getSoapInputStream(WEATHER_QUERY_URL+code);
			document = documentB.parse(inputStream);
			NodeList nl = document.getElementsByTagName("string");
			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				String weather = n.getFirstChild().getNodeValue();
				weatherList.add(weather);
			}
			inputStream.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return DateUtils.getModel(weatherList);
	}
	public  String getCityCode(Message message){
		String code="";
		Document document;
		try {
			document = getDocument(message);
			List<Element> elements=document.getElementsByTag("string");
			for (Element element : elements) {
				String weather=element.text();
				String[] ao=weather.split(",");
				if(ao[0].equals(message.getCity())){
					code=ao[1];
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return code;
	}
	private Document getDocument(Message message) throws IOException{
		//第一次请求
		Connection con=Jsoup.connect("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?op=getSupportCityString");//获取连接
		con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");//配置模拟浏览器
		Response rs= con.execute();//获取响应
		Document d1=Jsoup.parse(rs.body());//转换为Dom树
		List<Element> et= d1.select("input");//可以通过查看页面源码代码得知
		//获取，cooking和表单属性，下面map存放post时的数据 
		Map<String, String> datas=new HashMap<String, String>();
		for(Element e:et.get(0).getAllElements()){
			if(e.attr("name").equals("theRegionCode")){
				e.attr("value", message.getProvince());//设置用户名
			}
			if(e.attr("name").length()>0){//排除空值表单属性
				datas.put(e.attr("name"), e.attr("value"));  
			}
		}

		/**
		 * 第二次请求，post表单数据，以及cookie信息
		 * 
		 * **/
		Connection con2=Jsoup.connect("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getSupportCityString");
		con2.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
		//设置cookie和post上面的map数据
		Response login=con2.ignoreContentType(true).method(Method.POST).data(datas).cookies(rs.cookies()).execute();
		/*
	 	   //登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
	 	   Map<String, String> map=login.cookies();
	 	   for(String s:map.keySet()){
	 		   System.out.println(s+"      "+map.get(s));
	 	   }*/
		return Jsoup.parse(login.body());

	}

}

