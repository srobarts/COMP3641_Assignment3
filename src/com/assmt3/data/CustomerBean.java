package com.assmt3.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.NamingException;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

@ManagedBean // or @Named
@RequestScoped
public class CustomerBean {
	   private Connection conn;

		public void connect(String url, String username, String password, String driver)
		{
			try {
				Class.forName( driver );
				conn = DriverManager.getConnection( url, username, password );
				//stmt = con.createStatement();

			} catch(ClassNotFoundException ex) {
				ex.printStackTrace();
			} catch(SQLException ex) {
				ex.printStackTrace();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		public String create_table_query()
		{
			//Since this function is pretty specific to this address book it is not
			//a generic create table function where I pass in table names and
			//column names.  If you need to change the table then the code has
			//to change - but if you're changing the database that is pretty major
			//anyway under most circumstances.
			String query = 	"IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='a00222500_Members' and xtype='U') CREATE TABLE a00222500_Members " +
							"( memberID	INT IDENTITY(1,1), " +
							"firstName	VARCHAR(255) NOT NULL, " +
							"lastName VARCHAR(255) NOT NULL, " +
							"address VARCHAR(255) NOT NULL, " +
							"city VARCHAR(255) NOT NULL, " +
							"country VARCHAR(255) NOT NULL, " +
							"code CHAR(255) NOT NULL, " +
							"phoneNumber CHAR(255) NOT NULL, " +
							"email VARCHAR(255) NOT NULL) ";
			
			return query;
		}
	 
	   public Result getAll() throws SQLException, NamingException {
	      try {
	         Statement stmt = conn.createStatement();        
	         ResultSet result = stmt.executeQuery("SELECT * FROM Customers");
	         return ResultSupport.toResult(result);
	      } finally {
	         close();
	      }
	   }
	   
	   public void close() throws SQLException {
	      if (conn == null) return;
	      conn.close();
	      conn = null;
	   }  

}
