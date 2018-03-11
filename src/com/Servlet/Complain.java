package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;

/**
 * Servlet implementation class Complain
 */
@WebServlet("/Complain")
public class Complain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Complain() {
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
        
        String orderId = request.getParameter("orderid");
        String content = request.getParameter("content");
        
        try {
        	
        	Connection con = DBUtil.getConnection();
        	
        	ResultSet rs = con.createStatement().executeQuery("select * from daigo.complain "
        			+ "where orderid=" + orderId);
        	if (rs.next()) {
        		PreparedStatement ps = con.prepareStatement("update daigo.complain set "
        				+ "content=? where orderid=?");
        		ps.setString(1, content);
        		ps.setString(2, orderId);
        		ps.executeUpdate();
        		ps.close();
        	} else {
        		PreparedStatement ps = con.prepareStatement("insert into daigo.complain "
        				+ "(orderid, content, addressed) values(?,?,?)");
        		ps.setString(1, orderId);
        		ps.setString(2, content);
        		ps.setInt(3, 0);
        		ps.executeUpdate();
        		ps.close();
        	}
        	
        } catch (Exception e) {
        	
        } finally {
        	DBUtil.Close();
        	
        	out.flush();
        	out.close();
        }
	}

}
