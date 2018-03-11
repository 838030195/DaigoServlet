package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;

/**
 * Servlet implementation class OrderIDQuery
 */
@WebServlet("/OrderIDQuery")
public class OrderIDQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderIDQuery() {
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
		int campusId = Integer.parseInt(request.getParameter("campusid"));
		response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        PrintWriter out = response.getWriter();  
        String oId = "";
        try  
        {  
            Connection con=DBUtil.getConnection();  
            Statement stmt=con.createStatement();  
            String sql="select * from daigo.orderids where campusid=\'"+ campusId + "\'";  
            ResultSet rs=stmt.executeQuery(sql);  
            if (rs.next()) {
            	oId = rs.getString("orderId");
            }
        
        }  
        catch(Exception ex)  
        {  
            ex.printStackTrace();  
        }  
        finally  
        {  
            DBUtil.Close();  
            out.print(oId);
            out.flush();  
            out.close();  
        }
	}

}
