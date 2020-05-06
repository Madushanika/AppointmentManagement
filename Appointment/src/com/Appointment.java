package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appointment {//DB connection----------------------------
	private Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthcaredb1?serverTimezone=UTC", "root", "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return con;
	}
		
		//view------------------------------------------------------------------------------------------------------------------------
		public String readAppointment(){
			
			String output = "";
				try{
					Connection con = connect();
				
				if (con == null){
					return "Error while connecting to the database for reading Appointments."; 
				}
				// Prepare the html table to be displayed
				output = "<table border='1'><tr>"
						+ "<th>Doctor_ID</th>"
						+ "<th>Hospital_ID</th>"
						+ "<th>Chargers</th>"
						+ "<th>Appointment Date</th>"
						+ "<th>Start_Time</th>"
						+ "<th>End_Time</th>"
						+ "<th>Update</th>"
						+ "<th>Remove</th>"
						+ "</tr>";
				
				String query = "select * from schedule";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				// iterate through the rows in the result set
				while (rs.next()){
					
					String Shedule_ID 		= Integer.toString(rs.getInt("Shedule_ID"));
					String Doctor_ID 	= rs.getString("Doctor_ID");
					String Hospital_ID 	= rs.getString("Hospital_ID");
					String Chargers 		= rs.getString("Chargers");
					String Date 	= rs.getString("Date");
					String Start_Time 		= rs.getString("Start_Time");
					String End_Time = rs.getString("End_Time");
					
					//Add into the html table
					
					output += "<tr><td><input id='hidAppointmentIDUpdate' name='hidAppointmentIDUpdate' type='hidden' value='" + Shedule_ID + "'>" + Doctor_ID + "</td>";
					output += "<td>" + Hospital_ID + "</td>";
					output += "<td>" + Chargers + "</td>";
					output += "<td>" + Date + "</td>";
					output += "<td>" + Start_Time + "</td>";
					output += "<td>" + End_Time + "</td>";
					
					// buttons
					output += "<td><input name='btnUpdate'type='button' "
							+ "value='Update'class='btnUpdate btn btn-secondary'></td>"
							+ "<td><input name='btnRemove'type='button' "
							+ "value='Remove'class='btnRemove btn btn-danger' data-scheduleid='"+ Shedule_ID + "'>" + "</td></tr>";
				}
				con.close();
				// Complete the html table
				output += "</table>";
				}
				catch (Exception e){
					output = "Error while reading the Appointments info.";
					System.err.println(e.getMessage());
				}
				
		return output;
		
		}
		
		
		//insert-------------------------------------------------------------------------------------------------------------------------
		public String insertAppointment(String Doctor_ID, String Hospital_ID, String Chargers, String Date,String Start_Time,String End_Time)
		{
			String output = "";
			try
			{
				Connection con = connect();
				
				if (con == null)
				{
					return "Error while connecting to the database for inserting.";
				}
				
				// create a prepared statement
				String query = " insert into `schedule`(`Shedule_ID`, `Doctor_ID`, `Hospital_ID`, `Chargers`, `Date`,`Start_Time`, `End_Time`)  values (?, ?, ?, ?, ?, ?, ?)";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, Doctor_ID);
				preparedStmt.setString(3, Hospital_ID);
				preparedStmt.setDouble(4, Double.parseDouble(Chargers));
				preparedStmt.setString(5, Date);
				preparedStmt.setString(6, Start_Time);
				preparedStmt.setString(7, End_Time);
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				
				String newAppointment = readAppointment();
				output = "{\"status\":\"success\", \"data\": \"" +newAppointment + "\"}";
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\": \"Error while inserting the Appointment.\"}";
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
	    
		
		
		//update----------------------------------------------------------------------------------------------------------------
		public String updateAppointment(String Shedule_ID, String Doctor_ID, String Hospital_ID, String Chargers, String Date,String Start_Time,String End_Time)
		{
			String output = "";
			
			try
			{
				Connection con = connect();
				
				if (con == null)
				{
					return "Error while connecting to the database for updating.";
				}
				
				// create a prepared statement
				String query = "UPDATE schedule SET Doctor_ID=?,Hospital_ID=?,Chargers=?,Date=?,Start_Time=?,End_Time=? WHERE Shedule_ID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setString(1, Doctor_ID);
				preparedStmt.setString(2, Hospital_ID);
				preparedStmt.setDouble(3, Double.parseDouble(Chargers));
				preparedStmt.setString(4, Date);
				preparedStmt.setString(5, Start_Time);
				preparedStmt.setString(6, End_Time);
				preparedStmt.setInt(7, Integer.parseInt(Shedule_ID));
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				
				String newAppointment = readAppointment();
				output = "{\"status\":\"success\", \"data\": \"" + newAppointment + "\"}";
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		
		//delete-------------------------------------------------------------------------------------------------------------------
		public String deleteAppointment(String Shedule_ID)
		{
			String output = "";
			
			try
			{
				Connection con = connect();
				
				if (con == null)
				{
					return "Error while connecting to the database for deleting.";
				}
				
				// create a prepared statement
				String query = "delete from schedule where Shedule_ID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(Shedule_ID));
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				
				String newAppointment = readAppointment();
				output = "{\"status\":\"success\", \"data\": \"" + newAppointment + "\"}";
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
				System.err.println(e.getMessage());
			}
			
			return output;
		}
}