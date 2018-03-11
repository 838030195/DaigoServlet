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
 * Servlet implementation class UpdateUserInfo
 */
@WebServlet("/UpdateUserInfo")
public class UpdateUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserInfo() {
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
		String userId = request.getParameter("userid");
		String nickName = request.getParameter("nickname");
		String headIcon = request.getParameter("headicon");
		String address = request.getParameter("address");
		
		String phoneNum = request.getParameter("phonenum");
		
		String oldPassword = request.getParameter("oldpassword");
		String newPassword = request.getParameter("newpassword");
		
		String findPhone = request.getParameter("findphone");
		String password = request.getParameter("password");
		
		response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        boolean type = false;
        
        try  
        {  
            Connection con=DBUtil.getConnection();  
            
        	
        	String sql = "update daigo.user set nickname=?,headicon=?,address=? where userid=?";
        	String sql1 = "update daigo.user set phonenum=? where userid=?";
        	String sql2 = "update daigo.user set password=? where userid=?";
        	String sql4 = "update daigo.user set password=? where phonenum=?";
        	
        	PreparedStatement ps;
        	
        	if (nickName != null) {
        		ps = con.prepareStatement(sql);
            	ps.setString(1, nickName);
            	ps.setString(2, headIcon);
            	ps.setString(3, address);
            	ps.setString(4, userId);
            	ps.executeUpdate();
            	ps.close();
            	type = true;
        	}
        	
        	if (phoneNum != null) {
        		ps = con.prepareStatement(sql1);
        		ps.setString(1, phoneNum);
        		ps.setString(2, userId);
        		ps.executeUpdate();
        		ps.close();
        		type = true;
        	}
        	
        	if (oldPassword != null) {
        		Statement stmt=con.createStatement();  
                String sql3="select * from daigo.user where userid=\'"+userId+"\'"+" and password=\'"+oldPassword+"\'";  
                ResultSet rs=stmt.executeQuery(sql3);
                if (rs.first()) {
                	ps = con.prepareStatement(sql2);
            		ps.setString(1, newPassword);
            		ps.setString(2, userId);
            		ps.executeUpdate();
            		ps.close();
            		type = true;
                }
        		
        	}
        	
        	if (password != null) {
        		ps = con.prepareStatement(sql4);
        		ps.setString(1, password);
        		ps.setString(2, findPhone);
        		ps.executeUpdate();
        		ps.close();
        	}
        	
        
        	con.close();
        	
        }  
        catch(Exception ex)  
        {  
            ex.printStackTrace();  
        }  
        finally  
        {  
            DBUtil.Close();  
            out.print(type);
            out.flush();  
            out.close();  
        }
	}

}
