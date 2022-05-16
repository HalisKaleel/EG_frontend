package com;

import java.sql.*;


public class User {
	
private Connection connect() {
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");     

			// Provide the correct details: DBServer/DBName, user_name, password
			
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogrid?autoReconnect=true&useSSL=false", "root", "admin"); 
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
		public String insertUser(String username, String address, String phonenum, String email, String password) 
		 { 
		    String output = ""; 
		 try
		 { 
		      Connection con = connect(); 
		      
		      if (con == null) 
		      {
		    	  return "Error while connecting to the database for inserting the user."; 
		      
		      } 
		      
		      // create a prepared statement
		      String query = " insert into user (`account_no`,`name`,`inquiry_type`,`date`,`location`, `inquiry_status`)"
		       + " values (?, ?, ?, ?, ?, ?)"; 
		      
	      	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	      	 
		     // binding values
	      	 
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, account_no); 
			 preparedStmt.setString(3, name); 
			 preparedStmt.setString(4, phonenum); 
			 preparedStmt.setString(5, email); 
			 preparedStmt.setString(6, password); 
			 
		 // execute the statement

			 preparedStmt.execute(); 
			 con.close(); 
			 String users = readUser();
			 output = "{\"status\":\"success\", \"data\": \"" + users + "\"}";
			 
		 } 
		   catch (Exception e) 
		   { 
			   output = "{\"status\":\"error\", \"data\": \"Error while inserting the user.\"}";
			 System.err.println(e.getMessage()); 
		   } 
		   return output; 
	   } 
		
		public String readUser() {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for reading the user.";
				}
				
				
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>account_no</th><th>name</th>\" +\r\n" + 
						"		 \"<th>inquiry_type</th><th>date</th>\" +\r\n" + 
						"		 \"<th>date</th>\" + \r\n" + 
						"		 \"<th>inquiry_status</th>\" +
						 "<th>Update</th><th>Remove</th></tr>";

				String query = "select * from user";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				
				// iterate through the rows in the result set
				while (rs.next()) {
					Stringid = Integer.toString(rs.getInt("id"));
					String account_no = rs.getString("account_no");
					String name = rs.getString("name");
					String inquiry_type = rs.getString("inquiry_type");
					String date = rs.getString("date");
					String location = rs.getString("location");
					String inquiry_status =rs.getString("inquiry_status");
					
					// Add into the html table
					output += "<tr><td>" + id + "</td>";
					output += "<td>" + account_no + "</td>";
					output += "<td>" + name + "</td>";
					output += "<td>" + inquiry_type + "</td>";
					output += "<td>" + date + "</td>";
					output += "<td>" + location + "</td>";
					output += "<td>" + inquiry_status + "</td>";

					//buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-success' data-userid='" + id + "'></td>"
							 + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-userid='" + id + "'></td></tr>";
				}
				con.close();
				// Complete the html table
				output += "</table>";
			} catch (Exception e) {
				output = "Error while reading the users.";
				System.err.println(e.getMessage());
			}
			return output;	
		}
		
		public String updateUser(int id, String account_no, String name, String location ,String inquiry_status)

		{
			String output = "";
			
			
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for updating the user.";
				}
				// create a prepared statement
				String query = "UPDATE user SET account_no=?,name=?,inquiry_type=?, date=?, location=?, inquiry_status=? WHERE userID=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setString(1, account_no);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, inquiry_type);
				preparedStmt.setString(4, date);
				preparedStmt.setString(5, location);
				preparedStmt.setInt(6, inquiry_status);
				// execute the statement
				 preparedStmt.execute();
				con.close();
				
				 String users = readUser();
				 output = "{\"status\":\"success\", \"data\": \"" + users + "\"}";
				
				
			} catch (Exception e) {
				
				output = "{\"status\":\"error\", \"data\": \"Error while updating the user.\"}"; 
				System.err.println(e.getMessage());
				
			}
			return output;
		}
		
		public String deleteUser(String userID) 
		 { 
		        String output = ""; 
		 try
		 { 
			       Connection con = connect(); 
			       
			       if (con == null) 
			      {
			    	   return "Error while connecting to the database for deleting the user."; 
			      } 
			       
			     // create a prepared statement
			     String query = "delete from user where userID=?"; 
			     
			     PreparedStatement preparedStmt = con.prepareStatement(query); 
			     
			     // binding values
			     preparedStmt.setInt(1, Integer.parseInt(userID)); 
			     
			    // execute the statement
			    preparedStmt.execute(); 
			    con.close(); 
			    
			    String users = readUser();
			     output = "{\"status\":\"success\", \"data\": \"" + users + "\"}"; 
		  } 
		   catch (Exception e) 
		  { 
			   output = "{\"status\":\"error\", \"data\": \"Error while deleting the user.\"}"; 
		       System.err.println(e.getMessage()); 
		  } 
		 
	    	 return output; 
		 }
		
		public String readAUser()
		 {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {
			 return "Error while connecting to the database for reading.";
		 }
		 
		 // Prepare the html table to be displayed
		 output = "<table border='1'><tr><th>account_no</th><th>name</th>" +
		 "<th>inquiry_type</th><th>date</th>" +
		 "<th>date</th>" + 
		 "<th>inquiry_status</th>" +
		 "<th>Update</th><th>Remove</th></tr>";

		 String query = "select * from user where id=?";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
		 String userID = Integer.toString(rs.getInt("id"));
		 String username = rs.getString("account_no");
		 String address = rs.getString("name");
		 String phonenum = rs.getString("inquiry_type");
		 String email = rs.getString("date");
		 String password = rs.getString("location");
		 String password = rs.getString("inquiry_status");
		
		 // Add into the html table
		 output += "<tr><td>" + userID + "</td>";
		 output += "<td>" + username + "</td>";
		 output += "<td>" + address + "</td>";
		 output += "<td>" + inquiry_type + "</td>";
		 output += "<td>" + date + "</td>";
		 output += "<td>" + location + "</td>";
		 output += "<td>" + inquiry_status + "</td>";
		 
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
		 + "<td><form method='post' action='payment.jsp'>"
		 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
		 + "<input name='itemID' type='hidden' value='" + userID
		 + "'>" + "</form></td></tr>";
		 }
		 con.close();
		 // Complete the html table
		 output += "</table>";
		 }
		 catch (Exception e)
		 {
		 output = "Error while reading the data.";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 } 
		
	
		
				
}
