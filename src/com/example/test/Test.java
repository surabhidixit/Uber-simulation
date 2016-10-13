package com.example.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;

public class Test {
	static Location location;
	public Test() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[]args) throws IOException
	{
		
	
	File database = new File("C:/Users/Surabhi/Downloads/GeoLite2-City.mmdb/GeoLite2-City.mmdb");
	DatabaseReader reader =new DatabaseReader.Builder(database).build();
	URL whatismyip = new URL("http://checkip.amazonaws.com");
	BufferedReader in = new BufferedReader(new InputStreamReader(
	                whatismyip.openStream()));

	String ip = in.readLine(); //you get the IP as a String
	//System.out.println(ip);
	InetAddress ipAddress = InetAddress.getByName(ip);
	CityResponse response1 = null;
	try {
		response1 = reader.city(ipAddress);
	} catch (GeoIp2Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	 location = response1.getLocation();
	System.out.println(location.getLatitude());  // 44.9733
	System.out.println(location.getLongitude());
	}
}
