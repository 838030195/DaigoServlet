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
import com.Util.Order;

/**
 * Servlet implementation class OrderStateModify
 * 接单、退单、取消订单相关服务器操作
 */
@WebServlet("/OrderStateModify")
public class OrderStateModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderStateModify() {
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
		
		String orderId = request.getParameter("orderid");
		/**
		 * type
		 * 0：发布
		 * 1：接单
		 * 2：取消
		 * 3：完成
		 */
		int type = Integer.parseInt(request.getParameter("type"));
		String receiverId = request.getParameter("receiverid");
		String acceptTime = request.getParameter("accepttime");
		String completeTime = request.getParameter("completetime");
		
		response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        PrintWriter out = response.getWriter();
        
        boolean res = false;
        
        try {
        	Connection con=DBUtil.getConnection(); 
        	if (type == 0) {
        		//null
        	} else if (type == 1) {
        		//接单部分
        		ResultSet check = con.createStatement().executeQuery("select * from daigo.order "
        				+ "where orderid=" + orderId + " and state=0");
        		if (check.next()) {
        			
        			String senderId = check.getString("senderid");
        			
        			PreparedStatement ps = con.prepareStatement("update daigo.order set state=?, receiverid=?, accepttime=?, addressed=1 where orderid=?");
                	ps.setInt(1, Order.RECEIVED);
                	ps.setInt(2, Integer.parseInt(receiverId));
                	ps.setString(3, acceptTime);
                	ps.setString(4, orderId);
                	
                	ps.executeUpdate();
                	
                	ps.close();

            		res = true;
                	
        		}
        	} else if (type == 2) {
        		//取消订单
        		PreparedStatement ps = con.prepareStatement("update daigo.order set state=?, completetime=? where orderid=?");
            	ps.setInt(1, Order.INVALIDATE);
            	ps.setString(2, completeTime);
            	ps.setString(3, orderId);
            	
            	ps.executeUpdate();
       
            	ps.close();

        		res = true;
        	} else if (type == 3) {
        		//完成订单
        		PreparedStatement ps = con.prepareStatement("update daigo.order set state=?, completetime=?"
        				+ " where orderid=?");
            	ps.setInt(1, Order.COMPLETE);
            	ps.setString(2, completeTime);
            	ps.setString(3, orderId);
            	
            	ps.executeUpdate();
       
            	ps.close();

        		res = true;
        	}
        	
        	/**
        	 * 更新最新订单库
        	 */
        	Statement updateStmt = con.createStatement();
        	ResultSet rs = updateStmt.executeQuery("select * from daigo.order where campusid=" + orderId.substring(14, 15)
        			+ " and state=0");
        	String latestOrderId = "";
        	while(rs.next()) {
        		
        		latestOrderId = rs.getString("orderid");
        		
        	}
        	
        	PreparedStatement updatePState = con.prepareStatement("update daigo.orderids set orderid=? where campusid=?");
        	updatePState.setString(1, latestOrderId);
        	updatePState.setInt(2, Integer.parseInt(orderId.substring(14, 15)));
        	updatePState.executeUpdate();
        	updatePState.close();
        	
        	con.close();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	DBUtil.Close();  
            out.print(res);
            out.flush();  
            out.close();  
        }
	}

}
