package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Item {
	
	public Connection connect() {
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test","root"," ");
			
			System.out.println("Successfully Connected");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertItem(String code, String name,String price, String desc)
	{
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "Error while connecting to the database";
			}
			
			String query = "insert intp item ('itemID','itemCode','itemName','itemPrice','itemDesc')" + " values(?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, desc);
			preparedStmt.execute();
			con.close();
			
			output = "Inserted succesfully";
		}catch(Exception e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}
		
		return output;
	}

	public String readItems()

	{
		String output = "";
		
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for reading";
			}
			
			output = "<table border='1'<tr><th>Item Code</th>"
					 + "<th>Item Name</th><th>Item Price</th>"
					 +"<th> Item Description </th>"
					 +"<th> Update</th><th>Remove</th></tr>";
			
			String query = "select * from item";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				String itemID = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");
						
				output += "<tr><td>" + itemCode + "</td>";
				output += "<td>" + itemName + "</td>";
				output += "<td>" + itemPrice + "</td>";
				output += "<td>" + itemDesc + "</td>";
				
				output += "<td><input name='btnUpdate' type='button' value='Update' class = 'btn btn-secondary'></td>"
					 + "<td><form method='post' action='items.jsp'>" 
					 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
					 + "<input name='itemID' type='hidden' value='" + itemID + "'>" + "</form></td></tr>";
			
			}
			
			con.close();
			
			output += "</table>";
			
		}catch(Exception e) {
			output = " Error while reading the items";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deleteItem(String itemID) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if (con == null) {
				
				return "Error while connecting to the database while deleting";
				
			}
			
			String query = "delete from item where itemID = ?";
			
			PreparedStatement preparedstmt = con.prepareStatement(query);
			
			preparedstmt.setInt(1, Integer.parseInt(itemID));
			
			preparedstmt.execute();
			con.close();
			
			output = "Deleted Succefully";
		}
		catch(Exception e){
			output = "Error while deleting the item";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
}
