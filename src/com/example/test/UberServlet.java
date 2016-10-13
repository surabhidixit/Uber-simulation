package com.example.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import com.example.test.*;
import com.maxmind.geoip2.*;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;

/**
 * Servlet implementation class UberServlet
 */	
//@WebServlet("/UberServlet")

public class UberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static double lat,lon;
	static double dist;
	
	
	private String showFlag;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	HttpSession session;

	public UberServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		session=request.getSession();
		File database = new File("C:/Users/Surabhi/Downloads/GeoLite2-City_mmdb/GeoLite2-City.mmdb");
		DatabaseReader reader =new DatabaseReader.Builder(database).build();
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = null;

		CityResponse response1=null;
		try {	
			in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			String ip = in.readLine();

			InetAddress ipAddress = InetAddress.getByName(ip);
			response1 = null;

			response1 = reader.city(ipAddress);}
		catch (GeoIp2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Location location = response1.getLocation();

		lat=location.getLatitude();
		  // 44.9733
		DecimalFormat numberFormat = new DecimalFormat("#.000");
		DecimalFormat numberFormat1 = new DecimalFormat("#.0000");
		lon=location.getLongitude();
		response.setContentType( "text/html" );
		try {
			connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*dist=Double.parseDouble(Test2.tree.firstKey().toString());
		numberFormat.format(dist);
		System.out.println(dist);
		double time=dist/20;
		int round=(int)time;
		System.out.println(round);*/
		session.setAttribute("lat", numberFormat.format(lat));
		session.setAttribute("lon", numberFormat1.format(lon));
		//session.setAttribute("dist", numberFormat.format(dist));
		String array[] = showFlag.split(",");
		double value1=Double.parseDouble(array[0].replace("Point2D.Double[",""));
		
		//System.out.println("array: "+value1);
		
		session.setAttribute("flag1", value1);
		//session.setAttribute("flag2", "-117.8892");
		//System.out.println("Set Values in DB");
		RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @throws SQLException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void connect() throws SQLException
	{
		//System.out.println("-------- PostgreSQL "
			//	+ "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			//System.out.println("Where is your PostgreSQL JDBC Driver? "
			//		+ "Include in your library path!");
			e.printStackTrace();
			return;

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
			return;

		}

		if (connection != null) 

		{
			//System.out.println("You made it, take control your database now!");
		} else 
		{
			//System.out.println("Failed to make connection!");
			}

		Statement stmt = connection.createStatement();
		String sql0="TRUNCATE cars;";
		String sql1 = "INSERT INTO cars(name,locations) VALUES('My location',POINT("+lat+","+lon+"));";
		String sql2 = "INSERT INTO cars(name,locations) VALUES('Sedan1',POINT("+(lat+0.9)+","+lon+"));";
		String sql3 = "INSERT INTO cars(name,locations) VALUES('Hatchback1',POINT("+(lat+1)+","+(lon+0.4)+"));";
		String sql4 = "INSERT INTO cars(name,locations) VALUES('SUV1',POINT("+(lat+0.5)+","+(lon+0.4)+"));";
		String sql5 = "INSERT INTO cars(name,locations) VALUES('Sedan2',POINT("+(lat+0.6)+","+lon+"));";
		String sql6 = "INSERT INTO cars(name,locations) VALUES('Hatchback2',POINT("+(lat+0.4)+","+(lon+0.6)+"));";
		String sql7 = "INSERT INTO cars(name,locations) VALUES('SUV2',POINT("+(lat+0.2)+","+(lon+0.7)+"));";
		ResultSet rs1=null;
		stmt.execute(sql0);
		stmt.execute(sql1);
		stmt.execute(sql2);
		stmt.execute(sql3);
		stmt.execute(sql4);
		stmt.execute(sql5);
		stmt.execute(sql6);
		stmt.execute(sql7);
		
		Test2 test=new Test2();
		
		showFlag=test.getValues();

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);


	}

}
