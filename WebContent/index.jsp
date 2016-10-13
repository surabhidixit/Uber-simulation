<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.example.test.Test2"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<form action="UberServlet" method="post">
	<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>Google Maps Multiple Markers</title>
<script src="http://maps.google.com/maps/api/js?sensor=false"
	type="text/javascript"></script>
	</head>
	<body>
		<div id="map" style="width: 1000px; height: 1000px;"></div>

		<script type="text/javascript">
		
		
		var lat =
		<%=session.getAttribute("lat")%>
			;
			
			////alert('latitude'+lat);
		<%// System.out.println(session.getAttribute("lat"));%>
			
		<% //System.out.println(session.getAttribute("lon"));%>
			var lon =
		<%=session.getAttribute("lon")%>
			;
		
			var lat1= <%=session.getAttribute("flag1")%>;
			var lon1= <%=session.getAttribute("flag2")%>;
							;	
			var locations = [ [ 'You are here', lat, lon, 7 ],
					[ 'Sedan1', lat + 0.9, lon, 2 ],
					[ 'Hatchback1', lat + 1, lon + 0.4, 1 ],
					[ 'SUV1', lat + 0.5, lon + 0.4, 4 ],
					[ 'Sedan2', lat + 0.6, lon, 3 ],
					[ 'Hatchback2', lat + 0.4, lon + 0.6, 5 ],
					[ 'SUV2', lat + 0.2, lon + 0.7, 6 ],

			];
			<%%>
		<%
		%>
		<%//System.out.println("-------- PostgreSQL "
			//+ "JDBC Connection Testing ------------");

	try {

		Class.forName("org.postgresql.Driver");

	} catch (ClassNotFoundException e) {

		//System.out.println("Where is your PostgreSQL JDBC Driver? "
			//	+ "Include in your library path!");
		e.printStackTrace();
		return;

	}

	//System.out.println("PostgreSQL JDBC Driver Registered!");

	Connection connection = null;
	String url="jdbc:postgresql://localhost:5434/postgres";

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

	/*  for(int i=0;i<6;i++)
     {  
		double lat=  locations[0][1];
		String str="Insert into cars(locations) values(POINT(marker[i][1],marker[i][2]))";
	
	ResultSet rs1=stmt.executeQuery(str);   
     }
	
 */

    %>
			var map = new google.maps.Map(document.getElementById('map'), {
				zoom : 10,
				center : new google.maps.LatLng(33.7838279, -118.1162791),
				mapTypeId : google.maps.MapTypeId.ROADMAP
			});

			var infowindow = new google.maps.InfoWindow();

			var marker;

			for (var i = 0; i < locations.length; i++) {
				locations[i][1]=parseFloat(locations[i][1]).toFixed(3);
				//alert('locations[i][1]'+locations[i][1]);
				
				if(locations[i][1] == lat){
					
					marker = new google.maps.Marker({
					position : new google.maps.LatLng(locations[i][1],
							locations[i][2]),
					map : map,
					
					icon : "http://twimgs.com/ddj/v2/images/bizcard/home_icon.gif"
						
				});}
					else if(locations[i][1] == lat1){
					
				
					marker = new google.maps.Marker({
					position : new google.maps.LatLng(locations[i][1],
							locations[i][2]),
					map : map,
					//icon: "C:\Users\Surabhi\Desktop\cars\sedan.jpg"
					icon : "https://maps.google.com/mapfiles/kml/shapes/schools_maps.png"
				});
				}
					else{
					
					marker = new google.maps.Marker({
						position : new google.maps.LatLng(locations[i][1],
								locations[i][2]),
						map : map,
						//icon: "C:\Users\Surabhi\Desktop\cars\sedan.jpg"
						//icon : "https://maps.google.com/mapfiles/kml/shapes/schools_maps.png"
					});
					
				}
				google.maps.event.addListener(marker, 'click', (function(
						marker, i) {
					return function() {
						infowindow.setContent(locations[i][0]);
						infowindow.open(map, marker);
					}

				})(marker, i));
			
			
				}
			
/*			google.maps.event.addListener(marker, 'click', (function(
					marker, i) {
				return function() {
					infowindow.setContent(locations[i][0]);
					infowindow.open(map, marker);
				}

			})(marker, i));
*/
		</script>
	</body>
</html>