package com.Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;

/**
 * Servlet implementation class Report
 */
@WebServlet("/Report")
public class Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Report() {
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
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String model = request.getParameter("model");
		String device = request.getParameter("device");
		String sdk = request.getParameter("sdk");
		
		response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");
        try  
        {  
            Connection con=DBUtil.getConnection();  
            
        	PreparedStatement ps = con.prepareStatement("insert into `report` (title, content, model, device, sdk)"
        			+ " values(?,?,?,?,?)");
        	ps.setString(1, title);
        	ps.setString(2, content);
        	ps.setString(3, model);
        	ps.setString(4, device);
        	ps.setString(5, sdk);
        	ps.executeUpdate();
        	ps.close();
        	con.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

}
