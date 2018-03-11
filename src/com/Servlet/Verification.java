package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;

/**
 * Servlet implementation class Verification
 */
@WebServlet("/Verification")
public class Verification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Verification() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String userId = request.getParameter("userid"); //用于接收前段输入的ID值，此处参数须和input控件的name值一致  
        String idup = request.getParameter("idup");//用于接收前段输入的PW值，此处参数须和input控件的name值一致
        String iddown = request.getParameter("iddown");
        String schoolcard = request.getParameter("schoolcard");
        String idcode = request.getParameter("idcode");
        String schoolcode = request.getParameter("schoolcode");
        String realname = request.getParameter("realname");
        
        response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        PrintWriter out = response.getWriter();  
        try  
        {  
            Connection con=DBUtil.getConnection();  
            
        	
        	Statement stmt=con.createStatement();
        	
        	ResultSet preCheck = stmt.executeQuery("select * from user where idcode=" + idcode);
        	if (!preCheck.next()) {
        		ResultSet check = stmt.executeQuery("select * from verification where userId=" + userId);
                
            	if (!check.next()) {
            		PreparedStatement pstate = con.prepareStatement("INSERT INTO verification (userId, idup, iddown, schoolcard,"
            				+ "idcode, schoolcode, realname) VALUES(?,?,?,?,?,?,?)");
                    pstate.setString(1, userId);
                	pstate.setString(2, idup);
                	pstate.setString(3, iddown);
                	pstate.setString(4, schoolcard);
                	pstate.setString(5, idcode);
                	pstate.setString(6, schoolcode);
                	pstate.setString(7, realname);
                	
                    pstate.executeUpdate();
                	pstate.close();
            	} else {
            		PreparedStatement pstate = con.prepareStatement("update verification set idup=?, iddown=?,"
            				+ "schoolcard=?, idcode=?, schoolcode=?, realname=? where userId=?");
                    
                	pstate.setString(1, idup);
                	pstate.setString(2, iddown);
                	pstate.setString(3, schoolcard);
                	pstate.setString(4, idcode);
                	pstate.setString(5, schoolcode);
                	pstate.setString(6, realname);
                	pstate.setString(7, userId);
                	pstate.executeUpdate();
                	pstate.close();
            	}
        	} else {
        		out.print(false);
        	}

        	
        		
            	
               
        	
        }  
        catch(Exception ex)  
        {  
            ex.printStackTrace();  
        }  
        finally  
        {  
            DBUtil.Close();  
            
            out.flush();  
            out.close();  
        }
	}

}
