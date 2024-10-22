package com.servlet.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{

    //create the query
    private static final String INSERT_QUERY ="INSERT INTO USER(NAME,CITY,MOBILE,DOB) VALUES(?,?,?,?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
    	
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        
        //set Content type
        res.setContentType("text/html");
        
        //read the form values
        String name = req.getParameter("name");
        String city = req.getParameter("city");
        String mobile = req.getParameter("mobile");
        String dob = req.getParameter("dob");

        //load the jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        //create the connection
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/register","root","Murli@18");
                PreparedStatement ps = con.prepareStatement(INSERT_QUERY);){
            
        	//set the values
            ps.setString(1, name);
            ps.setString(2, city);
            ps.setString(3, mobile);
            ps.setString(4, dob);

            //execute the query
            int count = ps.executeUpdate();

            if(count==0) {
                pw.println("Record not stored into database");
            }else {
               // pw.println("Record Stored into Database");
            	RequestDispatcher rd = req.getRequestDispatcher("/message.html");
                rd.forward(req, res);
            }
        }catch(SQLException se) {
            pw.println(se.getMessage());
            se.printStackTrace();
        }catch(Exception e) {
            pw.println(e.getMessage());
            e.printStackTrace();
        }

        //close the stream
         pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
