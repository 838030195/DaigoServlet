package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;
import com.Util.Order;
import com.Util.User;
import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class OrderList
 */
@WebServlet("/OrderIDList")
public class OrderIDList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderIDList() {
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
		String campusId = request.getParameter("campusid");
		
		response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        PrintWriter out = response.getWriter();  
        
        List<String> orderList = new ArrayList<>();
        
        try  
        {  
            Connection con=DBUtil.getConnection();  
            Statement stmt=con.createStatement();  
            String sql="select * from daigo.order where campusid=\'"+campusId+"\'";  
            ResultSet rs=stmt.executeQuery(sql);  
        	
            while(rs.next())  
            {
            	if (rs.getInt("state") == Order.NORMAL) {
            		orderList.add(rs.getString("orderid"));
            	}
            	
            }
            /**
             * output
             */
            if (orderList.size() > 0) {
            	
                for (int i = 0; i < orderList.size(); i++) {
                	out.println(orderList.get(i));
                	
                }
                
                
            } else {
            	out.print("null");
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
