package com.example.test;

import java.awt.Point;
import java.awt.Point.*;
import java.awt.geom.Point2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.postgresql.geometric.PGpoint;

public class Test2 {
	static TreeMap tree=new TreeMap();
	static ArrayList<Point> array;
	//function to calculate distance between two geopoints
	private static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
		
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = deg2rad(dist);
		dist = dist * 60 * 1.1515;
		if (unit == 'K') {
			dist = dist * 1.609344;
		} else if (unit == 'N') {
			dist = dist * 0.8684;
		}

		return (dist);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::  This function converts decimal degrees to radians             :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}


	public Test2() {
		// TODO Auto-generated constructor stub
	}
	public String getValues() throws SQLException
	{//String query=null;
		String result="";
		
       //System.out.println("-------- PostgreSQL "
	//			+ "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			//System.out.println("Where is your PostgreSQL JDBC Driver? "
				//	+ "Include in your library path!");
			e.printStackTrace();
			return "";

		}

		//System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;
		String url="jdbc:postgresql://localhost:5434/workshop";

		try {

			connection = DriverManager.getConnection(url,"postgres",
					"anjanisut");
			//System.out.println("Success");

		} catch (SQLException e) {

			//System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return "";

		}

		if (connection != null) 

		{
			//System.out.println("You made it, take control your database now!");
		} else 
		{
			//System.out.println("Failed to make connection!");
			}

		Statement stmt = connection.createStatement();
		
        
		String str = "SELECT * FROM cars";
		
		ResultSet rs1=null;
			rs1= stmt.executeQuery(str);   
		double min=0,dist,lat1,lon1; 	
		Point2D.Double loc=null;
		

		int i=0;
		try{
			rs1.next();
			String tempStr= rs1.getString("locations");
			String temparray[] = tempStr.split(",");
			double templat1=Double.parseDouble(temparray[0].replace("(",""));
			double templon1=Double.parseDouble(temparray[1].replace(")",""));
			
			while(rs1.next()){
				
				String location = rs1.getString("locations");
				String array[] = location.split(",");
				lat1=Double.parseDouble(array[0].replace("(",""));
				lon1=Double.parseDouble(array[1].replace(")",""));
				//calling the distance function with the hard coded geopoint and lat lon from db
				dist=distance(templat1,templon1,lat1,lon1,'K');
				//instantiate Point2D
				loc=new Point2D.Double(0, 0);
				//set location for Point2D
				loc.setLocation(new Point2D.Double(lat1,lon1));
				//store distance,locations as key value pairs in treemap
				tree.put(dist,loc);
				
			}
			//print tree map values
			Set set = tree.entrySet();
			// Get an iterator
			Iterator itr = set.iterator();

			//print nearest location
			//System.out.println("result");
			//System.out.println(tree.firstEntry());
			result=tree.firstEntry().getValue().toString();
			Point2D p;
			
//			while(itr.hasNext()) {
//				Map.Entry me = (Map.Entry)itr.next();
//				//System.out.print(me.getKey() + ": ");
//				  //System.out.println(me.getValue());
//
//			}-
			rs1.close();


		}catch(SQLException se){

			se.printStackTrace();
		}catch(Exception e){

			//e.printStackTrace();
		}finally{

			try{
				if(stmt!=null)
					connection.close();
			}catch(SQLException se){
			}// do nothing
			try{
				if(connection!=null)
					connection.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return result;
		}
	
	}
