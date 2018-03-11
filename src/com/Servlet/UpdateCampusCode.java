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
@WebServlet("/UpdateCampusCode")
public class UpdateCampusCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCampusCode() {
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
		int campusCode = Integer.parseInt(request.getParameter("campuscode"));
		int userId = Integer.parseInt(request.getParameter("userid"));
		
		response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        boolean type = false;
        
        try  
        {  
            Connection con=DBUtil.getConnection();  
            
        	
        	String sql = "update daigo.user set campuscode=? where userid=?";
        	
        	
        	PreparedStatement ps;
        	
        	ps = con.prepareStatement(sql);
        	ps.setInt(1, campusCode);
        	ps.setInt(2, userId);
        	ps.executeUpdate();
        	
        	ps.close();
        
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
