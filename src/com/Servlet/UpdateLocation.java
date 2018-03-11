package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;

/**
 * Servlet implementation class UpdateLocation
 */
@WebServlet("/UpdateLocation")
public class UpdateLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateLocation() {
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
		response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        PrintWriter out = response.getWriter(); 
        
        double latitude = Double.parseDouble(request.getParameter("latitude"));
        double longitude = Double.parseDouble(request.getParameter("longitude"));
        String userId = request.getParameter("userid");
        
        try {
        	Connection con = DBUtil.getConnection();
        	PreparedStatement ps = con.prepareStatement("update daigo.user set latitude=?,"
        			+ "longitude=? where userid=?");
        	ps.setDouble(1, latitude);
        	ps.setDouble(2, longitude);
        	ps.setString(3, userId);
        	ps.executeUpdate();
        	
        	ps.close();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	DBUtil.Close();
        	out.flush();
        	out.close();
        }
	}

}
